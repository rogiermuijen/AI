package com.microsoft.bot.builder.solutions.directlinespeech;

import android.util.Log;

import com.microsoft.cognitiveservices.speech.ResultReason;
import com.microsoft.cognitiveservices.speech.audio.AudioConfig;
import com.microsoft.cognitiveservices.speech.dialog.BotConnectorActivity;
import com.microsoft.cognitiveservices.speech.dialog.BotConnectorConfig;
import com.microsoft.cognitiveservices.speech.dialog.SpeechBotConnector;

import java.io.IOException;
import java.io.PipedOutputStream;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SpeechSdk {

    // Constants
    private final String KEY_ENDPOINT_SPEECH = "SPEECH-Endpoint";
    private final String URI_ENDPOINT_SPEECH = "wss://websockets.platform.bing.com/ppe/convai/api/v1";

    // State
    private MicrophoneStream microphoneStream;
    private SpeechBotConnector botConnector;
    private Synthesizer synthesizer;
    private HashMap<String, PipedOutputStream> audioMap = new HashMap<>();

    //TODO pass the configuration by the 3rd party dev
    public void initialize(SpeechConfiguration configuration){
        synthesizer = new Synthesizer();
        initializeSpeech(configuration);
    }

    private void initializeSpeech(SpeechConfiguration configuration){
        //final AudioConfig audioInput = AudioConfig.fromDefaultMicrophoneInput();
        final AudioConfig audioInput = AudioConfig.fromStreamInput(createMicrophoneStream());
        BotConnectorConfig botConfig = BotConnectorConfig.fromBotConnectionId(
                SpeechConfiguration.BotId,
                SpeechConfiguration.CognitiveServicesSubscriptionKey,
                SpeechConfiguration.SpeechRegion);
        botConfig.setProperty(KEY_ENDPOINT_SPEECH, URI_ENDPOINT_SPEECH);
        botConnector = new SpeechBotConnector(botConfig, audioInput);

        botConnector.recognizing.addEventListener((o, speechRecognitionResultEventArgs) -> {
            final String s = speechRecognitionResultEventArgs.getResult().getText();
            Log.i("test", "Intermediate result received: " + s);
            //TODO trigger callback to expose result in 3rd party app
        });

        botConnector.recognized.addEventListener((o, speechRecognitionResultEventArgs) -> {
            final String s = speechRecognitionResultEventArgs.getResult().getText();
            Log.i("test", "Final result received: " + s);
            //TODO trigger callback to expose result in 3rd party app
        });

        botConnector.sessionStarted.addEventListener((o, sessionEventArgs) -> {
            Log.i("test", "got a session (" + sessionEventArgs.getSessionId() + ") event: sessionStarted");
            //TODO trigger callback to expose result in 3rd party app
        });

        botConnector.sessionStopped.addEventListener((o, sessionEventArgs) -> {
            Log.i("test", "got a session (" + sessionEventArgs.getSessionId() + ") event: sessionStopped");
            //TODO trigger callback to expose result in 3rd party app
        });

        botConnector.canceled.addEventListener((o, canceledEventArgs) -> {
            Log.i("test", "canceled (" + canceledEventArgs.getErrorDetails());
            final Future<Void> task = botConnector.disconnectAsync();
//            setOnTaskCompletedListener(task, result -> {
//                this.connected = false;
//            });
        });

        botConnector.activityReceived.addEventListener((o, activityEventArgs) -> {
            final String act = activityEventArgs.getActivity().serialize();
            Log.i("test", "got activity: " + act);
        });

        //TODO Note that this doesn't really handle the case of multiple requests supplying audio; to handle that, the callback to the playSound method should look to see if there are other completed streams and start playing one of those.
        botConnector.synthesizing.addEventListener((o, synthesisEventArgs) -> {
            Log.i("test", "got synthesis!");
            String request = synthesisEventArgs.getResult().getRequestId();
            byte[] audio = synthesisEventArgs.getResult().getAudio();
            ResultReason reason = synthesisEventArgs.getResult().getReason();
            if (reason == ResultReason.SynthesizingAudioCompleted) {
                PipedOutputStream v = audioMap.get(request);
                if (v != null) {
                    // write last chunk
                    try {
                        Log.v("test", String.format("trailing chunk (%d bytes)", audio.length));
                        if (synthesisEventArgs.getResult().getAudio().length > 0) {
                            v.write(audio);
                        }
                        // mark stream end
                        synthesizer.StopPlaying();
                        audioMap.get(request).close();
                    }
                    catch (IOException e) {
                        Log.e("test", "IOexception " + e.getMessage());
                    }
                }
                else {
                    Log.e("test", "No pending request?");
                }
            } else if (reason == ResultReason.SynthesizingAudio) {
                Log.v("test", String.format("writing chunk (%d bytes)", audio.length));
                if (!audioMap.containsKey(request)) {
                    PipedOutputStream bos = new PipedOutputStream();
                    audioMap.put(request, bos);
                }
                try {
                    if (!synthesizer.IsPlaying()) {
                        synthesizer.playStream(audioMap.get(request), (() -> {
                            // runs when stream completes
                            Log.i("synthesis", "finished playing");
                            // Your code here
                        }));
                    }
                    audioMap.get(request).write(audio);

                } catch (IOException e) {
                    Log.e("test", "IOException " + e.getMessage());
                }
            }
        });
    }

    private MicrophoneStream createMicrophoneStream() {
        if (microphoneStream != null) {
            microphoneStream.close();
            microphoneStream = null;
        }

        microphoneStream = new MicrophoneStream();
        return microphoneStream;
    }

    public void setRecognizingEventListener(){
        if (botConnector == null) throw new IllegalStateException("SDK is not initialized with initialize()");
    }

    public void setRecognizedEventListener(){
        if (botConnector == null) throw new IllegalStateException("SDK is not initialized with initialize()");
    }

    public void setSessionStartedEventListener(){
        if (botConnector == null) throw new IllegalStateException("SDK is not initialized with initialize()");
    }

    public void setSessionStoppedEventListener(){
        if (botConnector == null) throw new IllegalStateException("SDK is not initialized with initialize()");
    }

    public void setCanceledEventListener(){
        if (botConnector == null) throw new IllegalStateException("SDK is not initialized with initialize()");
    }

    public void setActivityReceivedEventListener(){
        if (botConnector == null) throw new IllegalStateException("SDK is not initialized with initialize()");
    }

    public void connect(){
        Future<Void> task = botConnector.connectAsync();
        setOnTaskCompletedListener(task, result -> {
            // your code here
        });
    }

    public void listenOnce(){
        final Future<Void> task = botConnector.listenOnceAsync();
        setOnTaskCompletedListener(task, result -> {
            // your code here
        });
    }

    public void disconnect(){
        final Future<Void> task = botConnector.disconnectAsync();
        setOnTaskCompletedListener(task, result -> {
            // your code here
        });
    }

    public void sendActivity(CharSequence chars) {
        final String activityTemplate = "{\"type\": \"button\", \"text\" : \"\", \"entities\": [{ \"color\": \"%2s\" }] }";

        if (botConnector != null) {
            UUID u = UUID.randomUUID();
            String randomUUIDString = u.toString();
            String s = String.format(activityTemplate, randomUUIDString, chars);
            BotConnectorActivity activity = BotConnectorActivity.fromSerializedActivity(s);
            final Future<Void> task = botConnector.sendActivityAsync(activity);
            setOnTaskCompletedListener(task, result -> {
                // your code here
            });
        }
    }

    private <T> void setOnTaskCompletedListener(Future<T> task, OnTaskCompletedListener<T> listener) {
        s_executorService.submit(() -> {
            T result = task.get();
            listener.onCompleted(result);
            return null;
        });
    }

    private interface OnTaskCompletedListener<T> {
        void onCompleted(T taskResult);
    }

    private static ExecutorService s_executorService;
    static {
        s_executorService = Executors.newCachedThreadPool();
    }
}
