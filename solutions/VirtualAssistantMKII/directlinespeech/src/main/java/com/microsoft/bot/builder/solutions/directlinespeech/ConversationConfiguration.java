package com.microsoft.bot.builder.solutions.directlinespeech;

import client.model.InputHints;

//TODO make this configurable for the 3rd party dev - This configuration is only for smoke testing
public class ConversationConfiguration {

    //Bot & Conversation
    public static String UserName = "User";
    public static String UserId = "";
    public static String Locale = "";
    public static String DirectLineSecret = "Q6nJMjE3aiU.cwA.Prs.FbfZ2ZH0pDVXop14wnHUgtIkcAiHM6PbwLHwiOldqEs";
    public static InputHints DefaultInputHint;

    public static String StartConversationEvent = "startConversation";
    public static String IPATimezoneEvent = "IPA.Timezone";
    public static String IPALocationEvent = "IPA.Location";
    public static String Latitude = "47.631936";
    public static String Longitude = "-122.136301";

}
