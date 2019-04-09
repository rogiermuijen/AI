package com.microsoft.bot.builder.solutions.virtualassistant;

import android.app.Application;

import com.microsoft.bot.builder.solutions.directlinespeech.SpeechSdk;

public class MainApplication extends Application {

    // State
//    private SpeechSdk speechSdk;
    private static MainApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
//        speechSdk = new SpeechSdk();
        //speechSdk.initialize(null); do NOT initialize here. get permissions first for audio
    }

//    public SpeechSdk getSpeechSdk(){
//        return speechSdk;
//    }

    public static MainApplication getInstance(){
        return instance;
    }
}
