package com.example.nicedate;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CitasUsuario extends Fragment {

    View CitasUsuario;
    FloatingActionButton citasnueva;
    public CitasUsuario() { }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         CitasUsuario= inflater.inflate(R.layout.fragment_citas_usuario, container, false);
         citasnueva = (FloatingActionButton) CitasUsuario.findViewById(R.id.nueva_cita_usuario);


         citasnueva.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent nueva_cita = new Intent(getActivity(), NuevaCita.class);
                 startActivity(nueva_cita);
             }
         });
        return  CitasUsuario;
    }
}