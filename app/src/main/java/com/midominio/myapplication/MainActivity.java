package com.midominio.myapplication;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.ads.MobileAds;
import com.midominio.myapplication.UTILIDADES.MyStepWord;

public class MainActivity extends AppCompatActivity {

    public static String nameCantPos= "cantPosKey";

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    SharedPreferences sp ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        MobileAds.initialize(this,"ca-app-pub-7112272416908253~2673073333");

        Intent backgroundService = new Intent(getApplicationContext(), MyService.class);
        startService(backgroundService);
        AdminDataBase adminDataBase = new AdminDataBase(this,AdminDataBase.DATABASE_NAME, null,1);
        adminDataBase.getWritableDatabase();

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        viewPager = findViewById(R.id.viewpage);
        setupViewPager(viewPager);

        tabLayout = findViewById(R.id.tab);
        tabLayout.setupWithViewPager(viewPager);
        sp = PreferenceManager.getDefaultSharedPreferences(this);

        if (sp.getInt(MyStepWord.spUSeThis,0)==2 && sp.getBoolean(ToastService.TOAST_EXIST,false)){
            Intent backgroundService1 = new Intent(this, ToastService.class);
            this.startService(backgroundService1);
        }

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new BlankFragment(), "CREA");
        adapter.addFragment(new BlankFragment2(), "AGREGA");
        adapter.addFragment(new FragmentTres(), "PREFERENCIA");
        adapter.addFragment(new FragmentComentarios(),"CONTACTO");
        viewPager.setAdapter(adapter);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        int config = R.id.config;
        if (id == config) {
            startActivity(new Intent(this, Preferencias.class));
        }else{
            if (id == R.id.about){
                alertDialog().show();
            }
        }
        return super.onOptionsItemSelected(item);

    }
    public AlertDialog alertDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Steep Word").
                setMessage("Desarrollador:\n\tAlexander RC \n Contacto: \n \t AlexRodriguez734m0@gmail.com").
                setPositiveButton("Listo", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        return builder.create();
    }

    protected void onResume() {
        super.onResume();
    }
}