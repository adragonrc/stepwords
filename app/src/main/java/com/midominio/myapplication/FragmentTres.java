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
import android.widget.RadioGroup;

import com.midominio.myapplication.UTILIDADES.MyStepWord;

public class FragmentTres extends Fragment {
    private View view;
    private Context context;
    private SharedPreferences sp;
    private RadioGroup radioGroup;

    public FragmentTres(){
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tres,container,false);
        sp = PreferenceManager.getDefaultSharedPreferences(view.getContext());
        context = view.getContext();
        radioGroup = view.findViewById(R.id.radioGrup);
        radioGroup.setOnCheckedChangeListener(onCheckedChangeListener);
        int i = sp.getInt(MyStepWord.spUSeThis,0);
        switch (i) {
            case 0:
                radioGroup.check(R.id.rbPredefinido);
                break;
            case 1:
                radioGroup.check(R.id.rbMyWord);
                break;
            case 2:
                radioGroup.check(R.id.rbInToast);
                break;
        }
        return view;
    }

    private RadioGroup.OnCheckedChangeListener onCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId){
                case R.id.rbPredefinido:{
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putInt(MyStepWord.spUSeThis,0);
                    editor.commit();
                    if (sp.getBoolean(ToastService.TOAST_EXIST,false)){
                        Intent i = new Intent(context,ToastService.class);
                       context.stopService(i);
                    }
                    break;
                }
                case R.id.rbMyWord:{
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putInt(MyStepWord.spUSeThis,1);
                    if (sp.getBoolean(ToastService.TOAST_EXIST,false)){
                        Intent i = new Intent(context,ToastService.class);
                        context.stopService(i);
                    }
                    editor.commit();
                    break;
                }
                case R.id.rbInToast:{
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putInt(MyStepWord.spUSeThis,2);
                    editor.commit();
                    if (!sp.getBoolean(ToastService.TOAST_EXIST,false)) {
                        Intent backgroundService = new Intent(context, ToastService.class);
                        context.startService(backgroundService);
                    }
                    break;
                }
            }
        }
    };

}
