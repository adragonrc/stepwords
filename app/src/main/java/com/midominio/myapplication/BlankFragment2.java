package com.midominio.myapplication;

import android.content.Context;
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
import com.midominio.myapplication.UTILIDADES.AdminTableAdjetivos;
import com.midominio.myapplication.UTILIDADES.AdminTableComonVerbs;
import com.midominio.myapplication.UTILIDADES.AdminTableVerbos;

public class BlankFragment2 extends Fragment {
    private View view;
    private Button btIrrVerb;
    private Button btComVerb;
    private Button btAdjetiv;
    private boolean flags[];
    private Context context;
    private InterstitialAd interstitialAd;
    private SharedPreferences sp;
    private String posInitName = "posInit";
    private int cont[];

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_dos,container,false);
        context = view.getContext();
        iniciarViews();
        iniciarPreferencias();
        iniciarVariables();
        cont = new int[3];
        for (int i = 0; i<cont.length; i++){
            cont[i] =0;
        }
        return view;
    }

    private void addTokens(){
        SharedPreferences.Editor editor= sp.edit();
        if (flags[0]){
            AdminTableVerbos.posPalabraMax +=20;
            editor.putInt(AdminTableVerbos.namePosPalabraMax,AdminTableVerbos.posPalabraMax);
            flags[0] = false;
        }else{
            if (flags[1]){
                AdminTableComonVerbs.posPalabraMax += 20;
                editor.putInt(AdminTableComonVerbs.namePosPalabraMax,AdminTableComonVerbs.posPalabraMax);
                flags[1] = false;
            }else{
                AdminTableAdjetivos.posPalabraMax += 20;
                editor.putInt(AdminTableAdjetivos.namePosPalabraMax,AdminTableAdjetivos.posPalabraMax);
                flags[2] = false;
            }
        }

        editor.commit();
        escribirBtAddTokens();
    }

    private void escribirBtAddTokens(){
        ((TextView) view.findViewById(R.id.tvAdj)).setText(String.valueOf(AdminTableAdjetivos.posPalabraMax));
        ((TextView) view.findViewById(R.id.tvComVerb)).setText(String.valueOf(AdminTableComonVerbs.posPalabraMax));
        ((TextView) view.findViewById(R.id.tvIrrVerb)).setText(String.valueOf(AdminTableVerbos.posPalabraMax));
    }
    private void iniciarPreferencias(){
        sp = PreferenceManager.getDefaultSharedPreferences(context);
        if (sp.getBoolean(posInitName, true)) {
            SharedPreferences.Editor editor = sp.edit();
            editor.putInt(AdminTableVerbos.namePosPalabraMax,35);
            editor.putInt(AdminTableComonVerbs.namePosPalabraMax,35);
            editor.putInt(AdminTableAdjetivos.namePosPalabraMax,35);
            editor.putBoolean(posInitName,false);
            editor.commit();
        }else{
            AdminTableAdjetivos.posPalabraMax = sp.getInt(AdminTableAdjetivos.namePosPalabraMax,35);
            AdminTableComonVerbs.posPalabraMax = sp.getInt(AdminTableComonVerbs.namePosPalabraMax,35);
            AdminTableVerbos.posPalabraMax = sp.getInt(AdminTableVerbos.namePosPalabraMax,35);
            escribirBtAddTokens();
        }

    }

    public void cargarNuevoAnuncio(){
        if (interstitialAd.isLoaded()){
            interstitialAd.show();
        }else{
            if (interstitialAd.isLoading()){
                Toast.makeText(view.getContext(), R.string.mensajeDeCargaDeAnuncio, Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(view.getContext(), R.string.mensajeAnuncioNoDisponible, Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void iniciarViews(){
        btAdjetiv= view.findViewById(R.id.btAdtjetive);
        btComVerb = view.findViewById(R.id.btComVerb);
        btIrrVerb = view.findViewById(R.id.btIrregularVerb);
        //Modificamos listeners
        btAdjetiv.setOnClickListener(onClickListenerBtAdj);
        btIrrVerb.setOnClickListener(onClickListenerBtIrV);
        btComVerb.setOnClickListener(onClickListenerBtCV);

        interstitialAd = new InterstitialAd(context);
        interstitialAd.setAdUnitId(getString(R.string.intesrticial2ID));
        interstitialAd.loadAd(new AdRequest.Builder().build());
        interstitialAd.setAdListener(new AdListener(){
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                addTokens();
                interstitialAd.loadAd(new AdRequest.Builder().build());

            }
        });
    }
    private void iniciarVariables(){
        flags = new boolean[]{false, false,false};

    }

    private Button.OnClickListener onClickListenerBtAdj = new Button.OnClickListener(){
        @Override
        public void onClick(View v) {
            if(cont[0] < 3) { cont[0] ++;
            }else{
                flags[0] = false;
                flags[1] = false;
                flags[2] = true;
                cargarNuevoAnuncio();
                cont[0] = 0;
            }
        }
    };

    private Button.OnClickListener onClickListenerBtCV = new Button.OnClickListener(){
        @Override
        public void onClick(View v) {
            if(cont[1] < 3) { cont[1] ++;
            }else {
                flags[0] = false;
                flags[1] = true;
                flags[2] = false;
                cargarNuevoAnuncio();
                cont[1] = 0;
            }
        }
    };
    private Button.OnClickListener onClickListenerBtIrV = new Button.OnClickListener(){
        @Override
        public void onClick(View v) {
            if(cont[2] < 3) { cont[2] ++;
            }else {
                flags[0] = true;
                flags[1] = false;
                flags[2] = false;
                cargarNuevoAnuncio();
                cont [2] = 0;
            }
        }
    };

}
