package com.microsoft.bot.builder.solutions.virtualassistant;

import android.app.Application;

import com.microsoft.bot.builder.solutions.directlinespeech.SpeechSdk;

public class MainApplication extends Application {

    // State
    private SpeechSdk speechSdk;

    @Override
    public void onCreate() {
        super.onCreate();
        speechSdk = new SpeechSdk();
        speechSdk.initialize(null);
    }

    public SpeechSdk getSpeechSdk(){
        return speechSdk;
    }
}
