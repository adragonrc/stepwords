package com.midominio.myapplication;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.widget.Toast;

public class MyService extends Service {
    private MyRececive myRececive = null;

    @Override
    public void onCreate() {
        super.onCreate();
        IntentFilter intentFilter = new IntentFilter ();
        intentFilter.addAction ("android.intent.action.SCREEN_ON");
        intentFilter.addAction ("android.intent.action.SCREEN_OFF");

        // Establecer la prioridad del receptor de difusión.
        intentFilter.setPriority (100);

        // Crear un receptor de transmisión de cambio de red.
        myRececive = new MyRececive ();

        // Registrar el receptor de difusión con el objeto de filtro de intento.
        registerReceiver (myRececive, intentFilter);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Servicio destruído!", Toast.LENGTH_SHORT).show();
         // Anular el registro de screenOnOffReceiver cuando se destruye.
        if (myRececive != null)
        {
            unregisterReceiver (myRececive);
     //       Log.d (myRececive.SCREEN_TOGGLE_TAG, "onDestroy: screenOnOffReceiver no está registrado.");
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
