package com.midominio.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.midominio.myapplication.UTILIDADES.MyStepWord;

public class MyRececive extends BroadcastReceiver {
    Intent i;
    public static final String SCREEN_TOGGLE_TAG = "SCREEN_TOGGLE_TAG";
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_SCREEN_OFF.equals (intent.getAction())) {
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
            int spI = sp.getInt(MyStepWord.spUSeThis, 0);
            switch (spI){
                case 0: {
                    i = new Intent(context.getApplicationContext(), Main2Activity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);
                    break;
                }
                case 1: {
                    i = new Intent(context.getApplicationContext(), MySetpWordActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);
                    break;
                }
                case 2:{

                }
            }

         }
    }
}
