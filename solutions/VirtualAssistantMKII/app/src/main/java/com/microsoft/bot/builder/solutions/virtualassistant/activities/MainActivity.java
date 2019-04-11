package com.microsoft.bot.builder.solutions.virtualassistant.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;

import com.microsoft.bot.builder.solutions.directlinespeech.ConversationConfiguration;
import com.microsoft.bot.builder.solutions.directlinespeech.ConversationListener;
import com.microsoft.bot.builder.solutions.directlinespeech.ConversationSdk;
import com.microsoft.bot.builder.solutions.directlinespeech.SpeechSdk;
import com.microsoft.bot.builder.solutions.virtualassistant.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnEditorAction;
import client.ApiException;

public class MainActivity extends BaseActivity implements ConversationListener {

    // VIEWS
    @BindView(R.id.recyclerview) RecyclerView recyclerview;
    @BindView(R.id.fab_mic) FloatingActionButton fabMic;
    @BindView(R.id.textinput) TextInputEditText textInput;

    // CONSTANTS
    private final int CONTENT_VIEW = R.layout.activity_main;
    private static final String LOGTAG = "MainActivity";

    // STATE
    private boolean isListening;
    private SpeechSdk speechSdk;
    private ConversationSdk conversationSdk;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(CONTENT_VIEW);
        ButterKnife.bind(this);

        fabMic.setOnClickListener(v -> {
            int fabColor;
            if (!isListening) {
                showSnackbar(fabMic, getString(R.string.msg_listening));
                fabColor = ContextCompat.getColor(this, R.color.color_fab_listening);
            } else {
                showSnackbar(fabMic, getString(R.string.msg_listening_not));
                fabColor = ContextCompat.getColor(this, R.color.color_fab_listening_not);
            }
            isListening = !isListening; //toggle listening state
            setFabColor(fabMic, fabColor);
        });

        // handle dangerous permisisons
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            requestRecordAudioPermissions();
        } else {
            initializeSpeechSdk(true);
        }

        // doesn't require dangerous permissions
        initializeConversationSdk();
    }

    @Override
    protected void permissionDenied(String manifestPermission) {
        if (manifestPermission.equals(Manifest.permission.RECORD_AUDIO)){
            initializeSpeechSdk(false);
            fabMic.hide();
        }
    }

    @Override
    protected void permissionGranted(String manifestPermission) {
        if (manifestPermission.equals(Manifest.permission.RECORD_AUDIO)){
            initializeSpeechSdk(true);
        }
    }

    private void initializeSpeechSdk(boolean haveRecordAudioPermission){
        if (speechSdk == null) {
            speechSdk = new SpeechSdk();
            speechSdk.initialize(null, haveRecordAudioPermission);
        }
    }

    private void initializeConversationSdk(){
        if (conversationSdk == null) {
            conversationSdk = new ConversationSdk(this);
            conversationSdk.createConversationConnection(this);
        }
    }

    private void setFabColor(FloatingActionButton fab, int color){
        fab.setSupportBackgroundTintList(ColorStateList.valueOf(color));
    }

    @OnEditorAction(R.id.textinput)
    boolean onEditorAction(int actionId, KeyEvent key){
        boolean handled = false;
        if (actionId == EditorInfo.IME_ACTION_SEND) {
            sendTextMessage(textInput.getEditableText().toString());
            handled = true;
        }
        return handled;
    }

    private void sendTextMessage(String msg){
        speechSdk.sendActivity(msg);
    }

    // Concrete ConversationListener implementation

    @Override
    public void serverConnected() {
        try{
            conversationSdk.sendStartConversationEvent();
            conversationSdk.sendVirtualAssistantTimeZoneEvent();
            conversationSdk.sendVirtualAssistantLocationEvent(ConversationConfiguration.Latitude, ConversationConfiguration.Longitude);
        } catch (ApiException e){
            Log.e(LOGTAG, e.toString());
        }

    }

    @Override
    public void serverConnectionFailed(String message) {
        Log.e(LOGTAG, message);
    }

    @Override
    public void noConnectivity() {
        Log.e(LOGTAG, "no connectivity");
    }
}
