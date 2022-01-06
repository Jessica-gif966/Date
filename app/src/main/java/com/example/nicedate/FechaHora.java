package com.example.nicedate;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class FechaHora extends AppCompatActivity {
 Button btnAgendar,bFecha;
 EditText eFecha;
 Spinner SpinnerHora;
 public int dia,mes,año;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fecha_hora);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        btnAgendar = (Button) findViewById(R.id.btnAgendar);
        bFecha = (Button) findViewById(R.id.bFecha);
        eFecha = (EditText) findViewById(R.id.eFecha);
        SpinnerHora = (Spinner) findViewById(R.id.SpinnerHoras);

        btnAgendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Agendado correctamente",Toast.LENGTH_LONG).show();
                Intent angendado = new Intent(getApplicationContext(),InicioUsuario.class);
                startActivity(angendado);
            }
        });

        String[] categorias = {"10:00-11:00", "11:00-12:00","12:00-13:00","13:00-14:00","14:00-15:00"};
        SpinnerHora.setAdapter(new ArrayAdapter<String>(FechaHora.this, android.R.layout.simple_spinner_dropdown_item, categorias));

        bFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cal = Calendar.getInstance();
                dia = cal.get(Calendar.DAY_OF_MONTH);
                mes = cal.get(Calendar.MONTH);
                año = cal.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(FechaHora.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        eFecha.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                        dia = dayOfMonth;
                        mes = month;
                        año = year;
                    }
                }, año, mes, dia);
                datePickerDialog.show();

            }
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}