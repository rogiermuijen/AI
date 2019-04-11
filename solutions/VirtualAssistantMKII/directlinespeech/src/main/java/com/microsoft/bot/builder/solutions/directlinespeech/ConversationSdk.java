package com.microsoft.bot.builder.solutions.directlinespeech;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.util.TimeZone;

import client.ApiClient;
import client.ApiException;
import client.WebSocketServerConnection;
import client.api.ConversationsApi;
import client.model.Activity;
import client.model.ActivityTypes;
import client.model.ChannelAccount;
import client.model.Conversation;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ConversationSdk implements WebSocketServerConnection.ServerListener {

    // Constants
    private static final String LOGTAG = "ConversationSdk";
    private static final String DIRECT_LINE_CONSTANT = "directline";

    // State
    private CompositeDisposable subscriptions;
    private ConversationsApi conversationsApi;
    private ApiClient client;
    private Conversation conversation;
    private ChannelAccount user;
    private Context context;
    private WebSocketServerConnection mServerConnection;
    private ConversationListener conversationListener;

    // Constructor
    public ConversationSdk(Context context) {
        if (context == null) throw new NullPointerException("Context is null");
        this.context = context;
        this.subscriptions = new CompositeDisposable();
        this.conversationsApi = new ConversationsApi();
        this.client = conversationsApi.getApiClient();
        this.user = new ChannelAccount();
        this.conversation = null;
    }


    // Concrete implementation of WebSocketServerConnection.ServerListener

    @Override
    public void onNewMessage(String message) {
        Log.v(LOGTAG,"onNewMessage: "+message);
    }

    @Override
    public void onStatusChange(WebSocketServerConnection.ConnectionStatus status) {
        Log.v(LOGTAG,"onStatusChange: "+status.toString());
    }


    // rxJava

    private void addDisposable(Disposable disposable) {
        subscriptions.add(disposable);
    }

    private void clearDisposables() {
        subscriptions.clear();
    }

    public Single<Boolean> isNetworkAvailable() {
        return Single.just(isNetworkAvailable(context));
    }

    private boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    /*
     * Calls Direct Line API to start a conversation and saves the conversation object
     */

    public void createConversationConnection(ConversationListener conversationListener){
        this.conversationListener = conversationListener;
        addDisposable(startConversation()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(conversation -> {
                            this.conversation = conversation;//keep a reference
                            mServerConnection = new WebSocketServerConnection(conversation.getStreamUrl());
                            mServerConnection.Connect(this);
                            Log.v(LOGTAG, "successfully connected");
                            this.conversationListener.serverConnected();
                        },
                        error -> {
                            Log.e(LOGTAG, "could not connect");
                            this.conversationListener.serverConnectionFailed(error.getMessage());
                        }
                ));
    }

    private Single<Conversation> startConversation(){
        return Single.create(subscriber -> isNetworkAvailable()
                        .doOnSuccess(isNetworkAvailable -> {
                            if (!isNetworkAvailable) {
                                Log.e(LOGTAG,"no internet");
                                this.conversationListener.noConnectivity();
                            }
                        })
                        .filter(isNetworkAvailable -> true)
                        .flatMapSingle(isNetworkAvailable -> {
                            client.addDefaultHeader("Authorization", "Bearer " + ConversationConfiguration.DirectLineSecret);
                            user.setName(ConversationConfiguration.UserName);
                            user.setId(ConversationConfiguration.UserId);
                            return conversationsApi.conversationsStartConversation();
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(conversation -> {
                            if(conversation != null) {
                                if (!subscriber.isDisposed()) subscriber.onSuccess(conversation);
                            } else {
                                if (!subscriber.isDisposed()) subscriber.onError(new Throwable("conversation = null"));
                            }
                        }, throwable -> {
                            Log.e(LOGTAG,"error");
                            if(!subscriber.isDisposed()) subscriber.onError(throwable);
                        })
        );
    }

    /*
    Send startConversation event, with locale, to the bot
     */
    public void sendStartConversationEvent() throws ApiException {
        Activity eventActivity = createEventActivity(ConversationConfiguration.StartConversationEvent, null, null);
        sendActivity(eventActivity);
    }

    /*
     * Send the IPA.TimeZone event to the bot
     */
    public void sendVirtualAssistantTimeZoneEvent() throws ApiException {
        TimeZone tz = TimeZone.getDefault();
        Activity eventActivity = createEventActivity(ConversationConfiguration.IPATimezoneEvent, null, tz.getDisplayName());
        sendActivity(eventActivity);
    }

    /*
     * Send the IPA.Location event to the bot
     */
    public void sendVirtualAssistantLocationEvent(String latitude, String longitude) throws ApiException {
        final String coordinates = latitude + "," + longitude;
        Activity eventActivity = createEventActivity(ConversationConfiguration.IPALocationEvent, null, coordinates);
        sendActivity(eventActivity);
    }

    /*
     * Create Event Activity with inputs: name, channel data, and value
     */
    private Activity createEventActivity(String name, Object channelData, Object value) throws ApiException {
        Activity activity = new Activity();
        activity.setType(ActivityTypes.EVENT);
        activity.setLocale(ConversationConfiguration.Locale);
        activity.setFrom(user);
        activity.setChannelId(DIRECT_LINE_CONSTANT);
        activity.setChannelData(channelData);
        activity.setName(name);
        activity.setValue(value);

        return activity;
    }

    /*
    Post Activity to Direct Line API (do not do on Main Thread)
     */
    private void sendActivity(Activity activity) throws ApiException {
        addDisposable(isNetworkAvailable()
                .doOnSuccess(isNetworkAvailable -> {
                    if (!isNetworkAvailable) {
                        Log.e(LOGTAG,"no internet");
                        this.conversationListener.noConnectivity();
                    }
                })
                .filter(isNetworkAvailable -> true)
                .flatMapSingle(isNetworkAvailable -> conversationsApi.conversationsPostActivity(conversation.getConversationId(), activity))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(resourceResponse -> {
                    Log.v(LOGTAG, "sendActivity result: "+resourceResponse.toString());
                }, throwable -> {
                    Log.e(LOGTAG,"sendActivity error: "+throwable.getMessage());
                })
        );
    }
}
