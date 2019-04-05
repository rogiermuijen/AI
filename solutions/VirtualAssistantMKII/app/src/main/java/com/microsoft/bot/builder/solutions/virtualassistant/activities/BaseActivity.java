package com.microsoft.bot.builder.solutions.virtualassistant.activities;

import android.annotation.SuppressLint;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

@SuppressLint("Registered")
public abstract class BaseActivity extends AppCompatActivity {

    protected void showSnackbar(View view, String message){
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
                .setAction("Action", null)
                .show();
    }

}
