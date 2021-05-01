package com.midominio.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;


public class BlankFragment extends Fragment {

    private View view;
    private Context context;
    private Button btGo;
    private Button btMostrarPub;

    private SharedPreferences sp;
    private InterstitialAd interstitialAd;
    private int cont = 0;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_blank,container,false);
        context = view.getContext();
        iniciarViews();
        sp = PreferenceManager.getDefaultSharedPreferences(context);
        btGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sp.getInt(MainActivity.nameCantPos,5)!=0) {
                    startActivity(new Intent(view.getContext(), NewStepWordActivity.class));
                }else{
                    Toast.makeText(context, "Nesecitas mas oportunidades", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btMostrarPub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cont < 3){
                    cont++;
                }else {
                    showPub(); cont = 0;
                }
            }
        });
        return view;
    }

    private void showPub(){
        if (interstitialAd.isLoaded()) {
            interstitialAd.show();
        } else {
            if (interstitialAd.isLoading()) {
                Toast.makeText(context, R.string.mensajeDeCargaDeAnuncio, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, R.string.mensajeAnuncioNoDisponible, Toast.LENGTH_SHORT).show();
                interstitialAd.loadAd(new AdRequest.Builder().build());
            }
        }
    }
    private void aumentarOP(){
        int c = sp.getInt(MainActivity.nameCantPos,5)+5;
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(MainActivity.nameCantPos,c);
        editor.commit();
        ((TextView) view.findViewById(R.id.tvOpRest)).setText(String.valueOf(c));
    }
    private void iniciarViews(){
        btGo = view.findViewById(R.id.btGo);
        btMostrarPub = view.findViewById(R.id.btMostrarPub);
        interstitialAd = new InterstitialAd(context);

        interstitialAd.setAdUnitId(getString(R.string.intesrticialID));
        interstitialAd.loadAd(new AdRequest.Builder().build());

        interstitialAd.setAdListener(new AdListener(){

            @Override
            public void onAdClosed() {
                super.onAdClosed();
                aumentarOP();
                interstitialAd.loadAd(new AdRequest.Builder().build());
            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();
        ((TextView) view.findViewById(R.id.tvOpRest)).setText(String.valueOf(PreferenceManager.getDefaultSharedPreferences(context).getInt(MainActivity.nameCantPos,5)));
    }

}
