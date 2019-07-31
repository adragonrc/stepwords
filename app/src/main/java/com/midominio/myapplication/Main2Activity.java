package com.midominio.myapplication;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.midominio.myapplication.UTILIDADES.AdminTableAdjetivos;
import com.midominio.myapplication.UTILIDADES.AdminTableComonVerbs;
import com.midominio.myapplication.UTILIDADES.AdminTableVerbos;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Main2Activity extends AppCompatActivity {
    //SpeechRecognizer sr;
    private Boolean permisoParaAvanzar;
    private Boolean permisoParaRetroseder;
    private boolean vermas = false;

    private int posPalabra;
    private int color;
    private int posRespuesta;
    private final int a = 100;

    private String preferenceNameHora = "hora";
    private String ultimaHora;
    private String[] palabra;
    private String[] subtitulos;

    private AdView mAdView;

    private EditText respuesta;

    private LinearLayout llBase;
    private LinearLayout ll1;

    private TextView[] descripciones;
    private TextView pregunta;
    private TextView tvInstrucciones;

    private SharedPreferences sp;

    private DateFormat dateFormat ;

    private TextToSpeech mTtsEnglish ;
    private TextToSpeech mTtsSpanish ;

    private TextToSpeech.OnInitListener onInitListener = new TextToSpeech.OnInitListener() {
        @Override
        public void onInit(int status) {
            if (status == TextToSpeech.SUCCESS) {
                int result = mTtsEnglish.setLanguage(Locale.US);

                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    //  Log.e("error", "Este Lenguaje no esta permitido");
                }
            } else { }
        }
    };

    private TextToSpeech.OnInitListener onInitListenerSpanish = new TextToSpeech.OnInitListener() {
        @Override
        public void onInit(int status) {
            Locale spanish = new Locale("es", "ES");
            if (status == TextToSpeech.SUCCESS) {
                int result = mTtsSpanish.setLanguage(spanish);

//                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
  //              }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        iniciarViews();
        iniciarVariables();

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        sp = PreferenceManager.getDefaultSharedPreferences(this);

        String vel = sp.getString("config_audio","100");
        mTtsEnglish = new TextToSpeech(this, onInitListener);


        Float velFloat  = Integer.valueOf(vel)/100.0f;
        mTtsEnglish.setSpeechRate(velFloat);

        mTtsSpanish = new TextToSpeech(this, onInitListenerSpanish);
        paintBackground();
        nuevaPalabra();
        descripciones = new TextView[palabra.length];
        pregunta.setText(palabra[0]);
        double r = Math.random();
        posRespuesta = (int)( r*10%3  +1);
        String instruccion = subtitulos[0]+subtitulos[posRespuesta];
        tvInstrucciones.setText(instruccion);
        descripciones [0] = pregunta;
        agregar();


    }

    public void initQueueEnglish(String text) {
            mTtsEnglish.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    public void initQueueSpanish(String text) {
            mTtsSpanish.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }


    private void iniciarVariables() {
        permisoParaAvanzar = false;
        permisoParaRetroseder =false;
        dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    }

    private void calcularHoraDeCambio(){
        try {
            Date hora= new Date();
            String changeType = sp.getString("pref_time_change","2");
            switch (changeType){
                case "1":{
                    hora.setTime(hora.getTime() +3600000); break;
                }
                case "2":{
                    hora.setTime(hora.getTime() + 18000000); break;
                }
                case "3":{
                    hora.setTime(hora.getTime() + 43200000); break;
                }
                default: {
                    hora.setTime(hora.getTime() + 3600000);
                }
            }
            String horaDeCambio = dateFormat.format(hora);

            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor edit = sp.edit();

            edit.putString(preferenceNameHora,horaDeCambio);
            edit.commit();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void compararHoras(String posToken, int max){
        ultimaHora = sp.getString(preferenceNameHora,"2019/06/03 00:00:00");
        posPalabra = sp.getInt(posToken,0);
        try {
            if (permisoParaAvanzar){
                SharedPreferences.Editor editor = sp.edit();
                if(posPalabra<max-1){
                    posPalabra++;
                }else{
                    posPalabra = 0;
                }
                editor.putInt(posToken,posPalabra);
                editor.commit();
                permisoParaAvanzar = false;
            }else{
                if (permisoParaRetroseder){
                    SharedPreferences.Editor editor = sp.edit();
                    if(posPalabra>0){
                        posPalabra--;
                    }else{
                        posPalabra = max-1;
                    }
                    editor.putInt(posToken,posPalabra);
                    editor.commit();
                    permisoParaRetroseder = false;
                }else {
                    Date dateAnt = dateFormat.parse(ultimaHora);
                    Date dateNow = new Date();
                    if (dateNow.after(dateAnt)) {
                        calcularHoraDeCambio();
                        SharedPreferences.Editor editor = sp.edit();
                        if (posPalabra < max - 1) {
                            posPalabra++;
                        } else {
                            posPalabra = 0;
                        }
                        editor.putInt(posToken, posPalabra);
                        editor.commit();
                    }
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    private void paintBackground(){
        int b, g, r;
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        r = sp.getInt("key_background_seekbar_rojo",0);
        g = sp.getInt("key_background_seekbar_verde",0);
        b = sp.getInt("key_background_seekbar_azul",0);
        Float f = r*2.55f; r = f.intValue();
        f = g*2.55f;       g = f.intValue();
        f = b*2.55f;       b = f.intValue();
        color = Color.rgb(r,g,b);
        llBase.setBackgroundColor(color);
        r = sp.getInt("key_text_seekbar_rojo",100);
        g = sp.getInt("key_text_seekbar_verde",100);
        b = sp.getInt("key_text_seekbar_azul",100);
        f = r*2.55f; r = f.intValue();
        f = g*2.55f; g = f.intValue();
        f = b*2.55f; b = f.intValue();
        color = Color.rgb(r,g,b);
        cambiarColores();
    }

    private void cambiarColores(){
        pregunta.setTextColor(color);
        respuesta.setTextColor(color);
        tvInstrucciones.setTextColor(color);
    }
    private String modP(String s){
        String aux = "";
        boolean flag =true;
        for(int i =0; i<s.length()&&flag;i++)
            if (s.charAt(i) == '(') {
                flag = false;
            } else {
                aux += s.charAt(i);
            }
        return aux;
    }
    private void agregar(){
        ll1 = findViewById(R.id.llDescripcion1);

        for (int i = 1; i<palabra.length; i++){
            final int I = i;
            LinearLayout linearLayout1 = (LinearLayout) getLayoutInflater().inflate(R.layout.linearl,ll1,false );
            TextView tvTitle = (TextView) getLayoutInflater().inflate(R.layout.tvtitle,ll1,false);
            final TextView tvDescripcion = (TextView) getLayoutInflater().inflate(R.layout.tvdescripcion,ll1,false);

            tvTitle.setText(subtitulos[i]);
            tvTitle.setTextColor(color);
            tvDescripcion.setText(palabra[i]);
            tvDescripcion.setTextColor(color);
            View.OnClickListener listener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String s = tvDescripcion.getText().toString();
                    if (I == palabra.length-1) {
                        initQueueSpanish(s);
                    }else {initQueueEnglish(modP(s));}
                }
            };
            tvDescripcion.setOnClickListener(listener);
            descripciones[i] = tvDescripcion;
            ImageButton button = (ImageButton) getLayoutInflater().inflate(R.layout.img_button,null);
            button.setOnClickListener(listener);
            linearLayout1.addView(tvTitle);
            linearLayout1.addView(tvDescripcion);
            linearLayout1.addView(button);
            ll1.addView(linearLayout1);
        }
    }
    public void onClickTexToSpeach(View view){
            mTtsEnglish.speak(modP(palabra[0]), TextToSpeech.QUEUE_FLUSH, null);
    }

    private void nuevaPalabra(){
        AdminDataBase adminDataBase = new AdminDataBase(this,AdminDataBase.DATABASE_NAME,null,1);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        String s = sp.getString("config_token","0");
        assert s != null;
        switch (s){
            case "0":{
                compararHoras("posToken1", AdminTableVerbos.posPalabraMax);
                subtitulos = AdminTableVerbos.descripciones;
                palabra = adminDataBase.consultarTablaVerbos(posPalabra);;
                break;
            }
            case "1":{
                compararHoras("posToken2", AdminTableComonVerbs.posPalabraMax);
                subtitulos = AdminTableComonVerbs.descripciones;
                palabra = adminDataBase.consultarTableComonVerbs(posPalabra);
                break;

            }
            case "2":{
                compararHoras("posToken3", AdminTableAdjetivos.posPalabraMax);
                subtitulos = AdminTableAdjetivos.descripciones;
                palabra = adminDataBase.consultarTablaAdjetivos(posPalabra);
                break;
            }
            default:{
                subtitulos = new String[]{"s1","s2","s3","s4"};
                palabra = new String[]{"Error","En","El","Codigo"};
            }
        }

    }
    public void cerrar(View view) {
        onBackPressed();
    }

    public void send(View view){
        if (respuesta.getText().toString().equals(modP(palabra[posRespuesta]))){
            Toast.makeText(this, "Corecto", Toast.LENGTH_SHORT).show();
            onBackPressed();
        }else{
            Toast.makeText(this, "Intenta de nuevo :)", Toast.LENGTH_SHORT).show();
        }
    }

    public void onClickCambiar(View view){
        permisoParaAvanzar = true;
        nuevaPalabra();
        for (int i = 0; i<descripciones.length; i++){
            descripciones[i].setText(palabra[i]);
        }
    }
    public void onClickAnt(View view){
        permisoParaRetroseder = true;
        nuevaPalabra();
        for (int i = 0; i<descripciones.length; i++){
            descripciones[i].setText(palabra[i]);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case a:{
                if (resultCode == RESULT_OK && null != data){
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    respuesta.setText(result.get(0));
                }
                break;
            }

        }
    }
    public void onClickMostrarRespuestas(View view){
        if (vermas){
            ((Button)view).setText("VER MAS");
            ll1.setVisibility(View.INVISIBLE);
        }else{((Button)view).setText("OCULTAR");
            ll1.setVisibility(View.VISIBLE);}
        vermas = !vermas;
    }

    private void iniciarViews(){
        respuesta = findViewById(R.id.tvRespuesta);
        pregunta = findViewById(R.id.tvPregunta);
        tvInstrucciones = findViewById(R.id.tvInstrucciones);
        llBase = findViewById(R.id.llBase);
    }
    protected void onDestroy() {
        super.onDestroy();
//        ttsManager.shutDown();
    }
    public void iniciarEntrada(View view){
        Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ENGLISH);

        i.putExtra(RecognizerIntent.EXTRA_PROMPT,"Respuesta");
        Toast.makeText(this, R.string.aviso_microfono, Toast.LENGTH_SHORT).show();
        try{
            startActivityForResult(i,a);
        }catch (ActivityNotFoundException e){

        }

    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if ( hasFocus ) {
            hideSystemUI ();
        }
    }

    private void hideSystemUI () {
        // Habilita el modo inmersivo regular.
        // Para el modo "inclinado", elimina SYSTEM_UI_FLAG_IMMERSIVE.
        // O para "inmersivo pegajoso", reemplazarlo con SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        View decorView = getWindow ().getDecorView ();
        decorView.setSystemUiVisibility ( View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                // Establecer el contenido de aparecer debajo de las barras del sistema de modo que la
                // contenido no cambia de tamaño cuando las barras del sistema ocultar y mostrar.
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                // Ocultar la barra de navegación y barra de estado
                |View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN );
    }
    // Muestra las barras del sistema eliminando todas las banderas
    // excepto las que hacen que el contenido aparezca debajo de las barras del sistema.
    private void showSystemUI () {
        View decorView = getWindow ().getDecorView ();
        decorView.setSystemUiVisibility ( View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN );
    }


}
