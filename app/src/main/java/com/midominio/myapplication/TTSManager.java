package com.midominio.myapplication;

import android.content.Context;
import android.speech.tts.TextToSpeech;

import java.util.Locale;

public class TTSManager {
    private TextToSpeech mTtsEnglish = null;
    private TextToSpeech mTtsSpanish = null;
    private boolean isLoaded = false;
    public void init(Context context, String vel) {
            mTtsEnglish = new TextToSpeech(context, onInitListener);


            Float velFloat  = Integer.valueOf(vel)/100.0f;
            mTtsEnglish.setSpeechRate(velFloat);

            mTtsSpanish = new TextToSpeech(context, onInitListenerSpanish);
    }

    private TextToSpeech.OnInitListener onInitListener = new TextToSpeech.OnInitListener() {
        @Override
        public void onInit(int status) {
            if (status == TextToSpeech.SUCCESS) {
                int result = mTtsEnglish.setLanguage(Locale.US);
                isLoaded = true;

                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                  //  Log.e("error", "Este Lenguaje no esta permitido");
                }
            } else {
              //  Log.e("error", "Fallo al Inicilizar!");
            }
        }
    };

    private TextToSpeech.OnInitListener onInitListenerSpanish = new TextToSpeech.OnInitListener() {
        @Override
        public void onInit(int status) {
            Locale spanish = new Locale("es", "ES");
            if (status == TextToSpeech.SUCCESS) {
                int result = mTtsSpanish.setLanguage(spanish);
                isLoaded = true;

                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                 }
            } else {
                //  Log.e("error", "Fallo al Inicilizar!");
            }
        }
    };

    public void shutDown() {
        mTtsEnglish.shutdown();
    }

    public void addQueue(String text) {
        if (isLoaded)
            mTtsEnglish.speak(text, TextToSpeech.QUEUE_ADD, null);
        else ;
      //      Log.e("error", "TTS Not Initialized");
    }

    public void initQueueEnglish(String text) {
        if (isLoaded)
            mTtsEnglish.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    public void initQueueSpanish(String text) {
        if (isLoaded)
            mTtsSpanish.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }
}
