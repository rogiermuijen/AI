package com.microsoft.bot.builder.solutions.virtualassistant.activities;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;

import com.microsoft.bot.builder.solutions.virtualassistant.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    // VIEWS
    @BindView(R.id.recyclerview) RecyclerView recyclerview;
    @BindView(R.id.fab_mic) FloatingActionButton fabMic;
    @BindView(R.id.textinput) TextInputEditText textInput;

    // CONSTANTS
    private final int CONTENT_VIEW = R.layout.activity_main;

    // STATE
    private boolean isListening;

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
    }

    private void setFabColor(FloatingActionButton fab, int color){
        fab.setSupportBackgroundTintList(ColorStateList.valueOf(color));
    }
}
