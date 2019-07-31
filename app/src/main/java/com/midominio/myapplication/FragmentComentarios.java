package com.midominio.myapplication;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FragmentComentarios extends Fragment {
    private View view;
    private Context context;
    private EditText etAsunto;
    private EditText etMensaje;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.comentarios,container,false);
        context = view.getContext();
        etAsunto = view.findViewById(R.id.etAsunto);
        etMensaje = view.findViewById(R.id.etMensaje);
        ((Button)view.findViewById(R.id.btEnviarDatos)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarCorreo();
            }
        });
        return view;
    }
    private void enviarCorreo(){
        String[] TO = {"alexrodriguez734m0@gmail.com"};
        String[] CC ={""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL,TO);
        emailIntent.putExtra(Intent.EXTRA_CC,CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT,etAsunto.getText().toString());
        emailIntent.putExtra(Intent.EXTRA_TEXT,etMensaje.getText().toString());
        try{
            startActivity(Intent.createChooser(emailIntent,"GMAIL"));
            //finish();
        }catch (android.content.ActivityNotFoundException ex){
           Toast.makeText(context, "No hay clientes instalados :c", Toast.LENGTH_SHORT).show();
        }
    }
}
