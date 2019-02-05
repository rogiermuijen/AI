﻿// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT license.
using System;
using System.Collections.Generic;
using System.Globalization;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using Luis;
using Microsoft.Bot.Builder;
using Microsoft.Bot.Builder.Dialogs;
using Microsoft.Bot.Builder.Dialogs.Choices;
using Microsoft.Bot.Schema;
using Microsoft.Bot.Solutions.Responses;
using Microsoft.Bot.Solutions.Skills;
using PointOfInterestSkill.Dialogs.Route;
using PointOfInterestSkill.Dialogs.Shared.Resources;
using PointOfInterestSkill.Models;
using PointOfInterestSkill.ServiceClients;

namespace PointOfInterestSkill.Dialogs.Shared
{
    public class PointOfInterestSkillDialog : ComponentDialog
    {
        // Constants
        public const string SkillModeAuth = "SkillAuth";
        public const string LocalModeAuth = "LocalAuth";

        public PointOfInterestSkillDialog(
            string dialogId,
            SkillConfigurationBase services,
            ResponseManager responseManager,
            IStatePropertyAccessor<PointOfInterestSkillState> accessor,
            IServiceManager serviceManager,
            IBotTelemetryClient telemetryClient)
            : base(dialogId)
        {
            Services = services;
            ResponseManager = responseManager;
            Accessor = accessor;
            ServiceManager = serviceManager;
            TelemetryClient = telemetryClient;

            AddDialog(new TextPrompt(Action.Prompt, CustomPromptValidatorAsync));
            AddDialog(new ConfirmPrompt(Action.ConfirmPrompt) { Style = ListStyle.Auto, });
        }

        protected SkillConfigurationBase Services { get; set; }

        protected IStatePropertyAccessor<PointOfInterestSkillState> Accessor { get; set; }

        protected IServiceManager ServiceManager { get; set; }

        protected ResponseManager ResponseManager { get; set; }

        protected override async Task<DialogTurnResult> OnBeginDialogAsync(DialogContext dc, object options, CancellationToken cancellationToken = default(CancellationToken))
        {
            var state = await Accessor.GetAsync(dc.Context);
            await DigestPointOfInterestLuisResult(dc, state.LuisResult);
            return await base.OnBeginDialogAsync(dc, options, cancellationToken);
        }

        protected override async Task<DialogTurnResult> OnContinueDialogAsync(DialogContext dc, CancellationToken cancellationToken = default(CancellationToken))
        {
            var state = await Accessor.GetAsync(dc.Context);
            await DigestPointOfInterestLuisResult(dc, state.LuisResult);
            return await base.OnContinueDialogAsync(dc, cancellationToken);
        }

        protected async Task<DialogTurnResult> GetPointOfInterestLocations(WaterfallStepContext sc, CancellationToken cancellationToken = default(CancellationToken))
        {
            try
            {
                var state = await Accessor.GetAsync(sc.Context);

                var service = ServiceManager.InitMapsService(Services, sc.Context.Activity.Locale ?? "en-us");
                var pointOfInterestList = new List<PointOfInterestModel>();

                state.CheckForValidCurrentCoordinates();

                if (string.IsNullOrEmpty(state.SearchText) && string.IsNullOrEmpty(state.SearchAddress))
                {
                    // No entities identified, find nearby locations
                    pointOfInterestList = await service.GetNearbyPointsOfInterestAsync(state.CurrentCoordinates.Latitude, state.CurrentCoordinates.Longitude);
                    await GetPointOfInterestLocationViewCards(sc, pointOfInterestList);
                }
                else if (!string.IsNullOrEmpty(state.SearchText))
                {
                    // Fuzzy query search with keyword
                    pointOfInterestList = await service.GetPointOfInterestByQueryAsync(state.CurrentCoordinates.Latitude, state.CurrentCoordinates.Longitude, state.SearchText);
                    await GetPointOfInterestLocationViewCards(sc, pointOfInterestList);
                }
                else if (!string.IsNullOrEmpty(state.SearchAddress))
                {
                    // Fuzzy query search with address
                    pointOfInterestList = await service.GetPointOfInterestByQueryAsync(state.CurrentCoordinates.Latitude, state.CurrentCoordinates.Longitude, state.SearchAddress);
                    await GetPointOfInterestLocationViewCards(sc, pointOfInterestList);
                }

                if (pointOfInterestList?.ToList().Count == 1)
                {
                    return await sc.PromptAsync(Action.ConfirmPrompt, new PromptOptions { Prompt = ResponseManager.GetResponse(POISharedResponses.PromptToGetRoute) });
                }

                state.ClearLuisResults();

                return await sc.EndDialogAsync(true);
            }
            catch
            {
                await HandleDialogException(sc);
                throw;
            }
        }

        protected async Task<DialogTurnResult> ResponseToGetRoutePrompt(WaterfallStepContext sc, CancellationToken cancellationToken)
        {
            try
            {
                var state = await Accessor.GetAsync(sc.Context);

                if ((bool)sc.Result)
                {
                    if (state.ActiveLocation != null)
                    {
                        state.ActiveLocation = state.LastFoundPointOfInterests.SingleOrDefault();
                        state.LastFoundPointOfInterests = null;
                    }

                    await sc.EndDialogAsync();
                    return await sc.BeginDialogAsync(nameof(RouteDialog));
                }
                else
                {
                    var replyMessage = ResponseManager.GetResponse(POISharedResponses.GetRouteToActiveLocationLater);
                    await sc.Context.SendActivityAsync(replyMessage);
                }

                return await sc.EndDialogAsync();
            }
            catch
            {
                await HandleDialogException(sc);
                throw;
            }
        }

        // Vaildators
        protected Task<bool> CustomPromptValidatorAsync(PromptValidatorContext<string> promptContext, CancellationToken cancellationToken)
        {
            var result = promptContext.Recognized.Value;
            return Task.FromResult(true);
        }

        // Helpers
        protected async Task GetPointOfInterestLocationViewCards(DialogContext sc, List<PointOfInterestModel> pointOfInterestList)
        {
            var state = await Accessor.GetAsync(sc.Context);
            var service = ServiceManager.InitMapsService(Services);

            if (pointOfInterestList != null && pointOfInterestList.Count > 0)
            {
                state.LastFoundPointOfInterests = pointOfInterestList;

                for (int i = 0; i < pointOfInterestList.Count; i++)
                {
                    pointOfInterestList[i] = await service.GetPointOfInterestDetailsAsync(pointOfInterestList[i]);
                    pointOfInterestList[i].Index = i;
                }

                if (pointOfInterestList.Count() > 1)
                {
                    var templateId = string.Empty;
                    var cards = new List<Card>();

                    if (sc.ActiveDialog.Id.Equals(Action.FindAlongRoute) && state.ActiveRoute != null)
                    {
                        templateId = POISharedResponses.MultipleLocationsFoundAlongActiveRoute;
                    }
                    else
                    {
                        templateId = POISharedResponses.MultipleLocationsFound;
                    }

                    foreach (var pointOfInterest in pointOfInterestList)
                    {
                        cards.Add(new Card("PointOfInterestViewCard", pointOfInterest));
                    }

                    var replyMessage = ResponseManager.GetCardResponse(templateId, cards);
                    await sc.Context.SendActivityAsync(replyMessage);
                }
                else
                {
                    state.ActiveLocation = state.LastFoundPointOfInterests.Single();
                    var templateId = string.Empty;

                    if (sc.ActiveDialog.Id.Equals(Action.FindAlongRoute) && state.ActiveRoute != null)
                    {
                        templateId = POISharedResponses.SingleLocationFoundAlongActiveRoute;
                    }
                    else
                    {
                        templateId = POISharedResponses.SingleLocationFound;
                    }

                    var card = new Card("PointOfInterestViewCard", state.ActiveLocation);
                    var replyMessage = ResponseManager.GetCardResponse(templateId, card);
                    await sc.Context.SendActivityAsync(replyMessage);
                }
            }
            else
            {
                var replyMessage = ResponseManager.GetResponse(POISharedResponses.NoLocationsFound);
                await sc.Context.SendActivityAsync(replyMessage);
            }
        }

        protected string GetFormattedTravelTimeSpanString(TimeSpan timeSpan)
        {
            var travelTimeSpanString = new StringBuilder();
            if (timeSpan.Hours == 1)
            {
                travelTimeSpanString.Append(timeSpan.Hours + " hour");
            }
            else if (timeSpan.Hours > 1)
            {
                travelTimeSpanString.Append(timeSpan.Hours + " hours");
            }

            if (travelTimeSpanString.Length != 0)
            {
                travelTimeSpanString.Append(" and ");
            }

            if (timeSpan.Minutes == 1)
            {
                travelTimeSpanString.Append(timeSpan.Minutes + " minute");
            }
            else if (timeSpan.Minutes > 1)
            {
                travelTimeSpanString.Append(timeSpan.Minutes + " minutes");
            }

            return travelTimeSpanString.ToString();
        }

        protected string GetFormattedTrafficDelayString(TimeSpan timeSpan)
        {
            var trafficDelayTimeSpanString = new StringBuilder();
            if (timeSpan.Hours == 1)
            {
                trafficDelayTimeSpanString.Append(timeSpan.Hours + " hour");
            }
            else if (timeSpan.Hours > 1)
            {
                trafficDelayTimeSpanString.Append(timeSpan.Hours + " hours");
            }

            if (trafficDelayTimeSpanString.Length != 0)
            {
                trafficDelayTimeSpanString.Append(" and ");
            }

            if (timeSpan.Minutes == 1)
            {
                trafficDelayTimeSpanString.Append(timeSpan.Minutes + " minute.");
            }
            else if (timeSpan.Minutes > 1)
            {
                trafficDelayTimeSpanString.Append(timeSpan.Minutes + " minutes.");
            }

            if (trafficDelayTimeSpanString.Length != 0)
            {
                trafficDelayTimeSpanString.Insert(0, "There is a traffic delay of ");
            }
            else
            {
                trafficDelayTimeSpanString.Append("There is no delay due to traffic.");
            }

            return trafficDelayTimeSpanString.ToString();
        }

        protected async Task GetRouteDirectionsViewCards(DialogContext sc, RouteDirections routeDirections)
        {
            var routes = routeDirections.Routes;
            var state = await Accessor.GetAsync(sc.Context);
            var cardData = new List<RouteDirectionsModelCardData>();
            var routeId = 0;

            if (routes != null)
            {
                state.FoundRoutes = routes.ToList();

                foreach (var route in routes)
                {
                    var travelTimeSpan = TimeSpan.FromSeconds(route.Summary.TravelTimeInSeconds);
                    var trafficTimeSpan = TimeSpan.FromSeconds(route.Summary.TrafficDelayInSeconds);

                    var routeDirectionsModel = new RouteDirectionsModelCardData()
                    {
                        Location = state.ActiveLocation.Name,
                        TravelTime = GetFormattedTravelTimeSpanString(travelTimeSpan),
                        TrafficDelay = GetFormattedTrafficDelayString(trafficTimeSpan),
                        RouteId = routeId,
                    };

                    cardData.Add(routeDirectionsModel);
                    routeId++;
                }

                if (cardData.Count() > 1)
                {
                    var cards = new List<Card>();
                    foreach (var data in cardData)
                    {
                        cards.Add(new Card("RouteDirectionsViewCard", data));
                    }

                    var replyMessage = ResponseManager.GetCardResponse(POISharedResponses.MultipleRoutesFound, cards);
                    await sc.Context.SendActivityAsync(replyMessage);
                }
                else
                {
                    var card = new Card("RouteDirectionsViewCardNoGetStartedButton", cardData.SingleOrDefault());
                    var replyMessage = ResponseManager.GetCardResponse(POISharedResponses.SingleRouteFound, card);
                    await sc.Context.SendActivityAsync(replyMessage);
                }
            }
            else
            {
                var replyMessage = ResponseManager.GetResponse(POISharedResponses.NoLocationsFound);
                await sc.Context.SendActivityAsync(replyMessage);
            }
        }

        protected async Task DigestPointOfInterestLuisResult(DialogContext dc, PointOfInterestLU luisResult)
        {
            try
            {
                var state = await Accessor.GetAsync(dc.Context, () => new PointOfInterestSkillState());

                if (luisResult != null)
                {
                    var entities = luisResult.Entities;

                    if (entities.KEYWORD != null && entities.KEYWORD.Length != 0)
                    {
                        state.SearchText = string.Join(" ", entities.KEYWORD);
                    }

                    if (entities.ADDRESS != null && entities.ADDRESS.Length != 0)
                    {
                        state.SearchAddress = string.Join(" ", entities.ADDRESS);
                    }

                    if (entities.DESCRIPTOR != null && entities.DESCRIPTOR.Length != 0)
                    {
                        state.SearchDescriptor = entities.DESCRIPTOR[0];
                    }

                    if (entities.number != null)
                    {
                        try
                        {
                            var value = entities.number[0];
                            if (Math.Abs(value - (int)value) < double.Epsilon)
                            {
                                state.UserSelectIndex = (int)value - 1;
                            }
                        }
                        catch
                        {
                            // ignored
                        }
                    }
                }
            }
            catch
            {
                // put log here
            }
        }

        protected async Task HandleDialogException(WaterfallStepContext sc)
        {
            var state = await Accessor.GetAsync(sc.Context);
            state.Clear();
            await Accessor.SetAsync(sc.Context, state);
            await sc.CancelAllDialogsAsync();
        }
    }
}