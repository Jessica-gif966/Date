package com.example.nicedate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TipoCuenta extends AppCompatActivity {
    Button btnUsuario, btnConsultorio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipo_cuenta);

        btnUsuario = (Button) findViewById(R.id.Usuario);
        btnConsultorio = (Button) findViewById(R.id.Consultorio);

        btnUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),LoginUsuario.class);
                startActivity(intent);
            }
        });

        btnConsultorio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),LoginConsultorio.class);
                startActivity(intent);
            }
        });
    }
}