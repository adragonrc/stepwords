package com.midominio.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.midominio.myapplication.UTILIDADES.MyStepWord;

public class NewStepWordActivity extends AppCompatActivity {
    private EditText eTPrimary;
    private EditText eTSecond;
    private EditText eTKey;
    private CheckBox checkBox;
    private SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_step_word);
        iniciarViews();
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        eTKey.setVisibility(View.GONE);
        checkBox.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    eTKey.setVisibility(View.VISIBLE);
                }else{
                    eTKey.setVisibility(View.GONE);
                }
            }
        });
    }
    public void onClickAceptar(View view){
        int cantPos = sp.getInt(MainActivity.nameCantPos,5);
        guardarDatos();
        cantPos--;
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(MainActivity.nameCantPos,cantPos);
        editor.commit();
        if (sp.getInt(MyStepWord.spUSeThis,0)==2 && sp.getBoolean(ToastService.TOAST_EXIST,false)){
            Intent backgroundService = new Intent(this, ToastService.class);
            this.stopService(backgroundService);
            Intent backgroundService1 = new Intent(this, ToastService.class);
            this.startService(backgroundService1);
            Toast.makeText(this, "Mensaje Modificado", Toast.LENGTH_SHORT).show();
        }
    }
    public void guardarDatos(){
        SharedPreferences.Editor editor= sp.edit();
        editor.putString(MyStepWord.spPrincip,eTPrimary.getText().toString());
        editor.putString(MyStepWord.spSecond,eTSecond.getText().toString());
        editor.putBoolean(MyStepWord.spShowPass,checkBox.isChecked());
        if (checkBox.isSelected()){
            editor.putString(MyStepWord.spPass,eTKey.getText().toString());
        }else{
            editor.putString(MyStepWord.spPass,MyStepWord.spKeyDefault);
        }
        editor.commit();
        eTPrimary.setText("");
        eTSecond.setText("");
        eTKey.setText("");

        Toast.makeText(this, "GUARDADO", Toast.LENGTH_SHORT).show();
        onBackPressed();
    }
    public void onClickCancel(View view){
        onBackPressed();
    }
    private void iniciarViews(){
        eTPrimary = findViewById(R.id.eTPrimary);
        eTSecond = findViewById(R.id.eTSecondary);
        eTKey = findViewById(R.id.eTContra);
        checkBox = findViewById(R.id.checkBox);
    }
}
