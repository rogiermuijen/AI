package com.microsoft.bot.builder.solutions.directlinespeech;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import client.ApiClient;
import client.WebSocketServerConnection;
import client.api.ConversationsApi;
import client.model.ChannelAccount;
import client.model.Conversation;
import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ConversationSdk implements WebSocketServerConnection.ServerListener {

    // Constants
    private final static String logTag = "ConversationSdk";

    // State
    private CompositeDisposable subscriptions;
    private ConversationsApi conversationsApi;
    private ApiClient client;
    private Conversation conversation;
    private ChannelAccount user;
    private Context context;
    private WebSocketServerConnection mServerConnection;

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

    public void initialize(){
        mServerConnection.Connect(this);
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


    // Concrete implementation of WebSocketServerConnection.ServerListener

    @Override
    public void onNewMessage(String message) {
        Log.v(logTag,"onNewMessage: "+message);
    }

    @Override
    public void onStatusChange(WebSocketServerConnection.ConnectionStatus status) {
        Log.v(logTag,"onStatusChange: "+status.toString());
    }




    /*
     * Calls Direct Line API to start a conversation and saves the conversation object
     */
    public void startConversation(){
        addDisposable(
                isNetworkAvailable()
                        .doOnSuccess(isNetworkAvailable -> {
                            if (!isNetworkAvailable) {
                                // showErrorMessage();
                                Log.e(logTag,"no internet");
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
                            this.conversation = conversation;
                        }, error -> {
                            Log.e(logTag,"error");
                        })
        );
    }
}
