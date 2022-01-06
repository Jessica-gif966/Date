package com.example.nicedate;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

public class Consultorio extends Fragment {

    View consultorio;
    ImageView view;
    Button btnnuevoconsultorio;

    public Consultorio() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        consultorio = inflater.inflate(R.layout.fragment_consultorio, container, false);

        view = (ImageView) consultorio.findViewById(R.id.consultorioGraficas);
        btnnuevoconsultorio = (Button) consultorio.findViewById(R.id.btnnuevoconsultorio);

        btnnuevoconsultorio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent agregarconstulroio = new Intent(getActivity(),NuevoConsultorio.class);
                startActivity(agregarconstulroio);
            }
        });


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GraficasConsultorio();
            }
        });


        return consultorio;
    }



    public void GraficasConsultorio(){
        Intent graficas = new Intent(getActivity(),Graficas.class);
        startActivity(graficas);
    }





}