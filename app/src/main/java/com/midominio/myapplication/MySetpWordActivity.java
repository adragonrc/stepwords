package com.midominio.myapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.midominio.myapplication.UTILIDADES.MyStepWord;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class MySetpWordActivity extends AppCompatActivity {

    private boolean showHelp;

    private String sKey;

    private AdView mAdView;
    private EditText etKey;
    private Button btEnviar;
    private LinearLayout llKey;
    private LinearLayout llAyuda;
    private TextView tvPrimary;
    private TextView tvSecond;
    private TextView tvRecordar;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_setp_word);
        iniciarViews();
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        showHelp = true;

        recuperarDatos();
    }
    private void recuperarDatos(){
        String primary = sp.getString(MyStepWord.spPrincip,"Crea tu nota");
        String second = sp.getString(MyStepWord.spSecond,"crea tu mensaje");
        boolean usekey = sp.getBoolean(MyStepWord.spShowPass,false);
        tvPrimary.setText(primary);
        tvSecond.setText(second);
        if (second.equals("")){
            ((Button)findViewById(R.id.btSecond)).setVisibility(View.GONE);
        }
        if (usekey){
            sKey = sp.getString(MyStepWord.spPass,MyStepWord.spKeyDefault);
            llKey.setVisibility(View.VISIBLE);
            btEnviar.setVisibility(View.VISIBLE);
        }else{
            llKey.setVisibility(View.GONE);
            btEnviar.setVisibility(View.GONE);
        }
    }
    public void onClickAceptar(View view){
        if (sKey.equals(etKey.getText().toString())){
            Toast.makeText(this, "CORRECTO", Toast.LENGTH_SHORT).show();
            onBackPressed();
        }else{
            Toast.makeText(this, "CONTRASEÑA INCORRECTA", Toast.LENGTH_SHORT).show();
        }
    }
    public void mostrarAyuda(View view){
        if (showHelp){
           llAyuda.setVisibility(View.VISIBLE);
            ((Button) view).setText("Menos");
        }else{llAyuda.setVisibility(View.GONE);
            ((Button) view).setText("Mas");
        }
        showHelp = !showHelp;
    }
    public void salir(View view){
        onBackPressed();
    }
    private void iniciarViews(){
        tvPrimary = findViewById(R.id.tvPrimary);
        tvSecond = findViewById(R.id.tvSecond);
        tvRecordar = findViewById(R.id.tvRecordar);
        etKey = findViewById(R.id.etKey);
        btEnviar = findViewById(R.id.btEnviar);
        llKey = findViewById(R.id.llKey);
        llAyuda = findViewById(R.id.llAyuda);

    }
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
}
