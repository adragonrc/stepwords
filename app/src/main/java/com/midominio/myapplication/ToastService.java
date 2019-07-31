package com.midominio.myapplication;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.midominio.myapplication.UTILIDADES.MyStepWord;

public class ToastService extends Service {
    private ToastService.MyTask myTask;
    public static final String TOAST_EXIST = "toast_service_exist";
    SharedPreferences sp;

    @Override
    public void onCreate() {
        super.onCreate();
        sp = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        final String primary = sp.getString(MyStepWord.spPrincip,"Primary default");

        String changeType = sp.getString("pref_time_change","2");
        final long time ;
        switch (changeType){
            case "1":{
                time = 15000; break;
            }
            case "2":{
                time = 30000; break;
            }
            case "3":{
                time = 60000; break;
            }
            case "4":{
                time = 120000; break;
            }
            default: {
                time = 30000; break;
            }
        }
        myTask = new ToastService.MyTask(primary,time);
        myTask.execute();


        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(ToastService.TOAST_EXIST,true);
        editor.commit();
     }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        myTask.cancel(true);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(ToastService.TOAST_EXIST, false);
        editor.commit();
        // Anular el registro de screenOnOffReceiver cuando se destruye.
        Toast.makeText(this, "servicio eliminado", Toast.LENGTH_SHORT).show();
    }

    private class MyTask extends AsyncTask<String, String, String> {

        private String mensaje;
        private long time;
        private boolean cent;

        public MyTask(String mensaje, long time){
            this.mensaje = mensaje;
            this.time = time;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            cent = true;
        }

        @Override
        protected String doInBackground(String... params) {
            while (!isCancelled()){
                try {
                    publishProgress(mensaje);
                    // Stop 5s
                    Thread.sleep(time);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //          Toast.makeText(ToastService.this, "ask finalizado", Toast.LENGTH_SHORT).show();
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            Toast.makeText(getApplicationContext(), values[0], Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            cent = false;
        }
    }
}


