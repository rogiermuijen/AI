package com.microsoft.bot.builder.solutions.directlinespeech;

public interface ConversationListener {
    void noConnectivity();
    void serverConnected();
    void serverConnectionFailed(String message);
}
