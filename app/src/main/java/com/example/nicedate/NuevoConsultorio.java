package com.example.nicedate;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;

public class NuevoConsultorio extends AppCompatActivity {

    Button hora11;
    Button hora12;
    Button btnguardarfechas;

    EditText hora11E,hora12E;

    private int hora11t,min11t,hora12t,min12t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_consultorio);

        hora11 = (Button) findViewById(R.id.hora11);
        hora12 = (Button) findViewById(R.id.hora12);

        hora11E = (EditText) findViewById(R.id.hora11E);
        hora12E = (EditText) findViewById(R.id.hora12E);

        btnguardarfechas = (Button)findViewById(R.id.btnguardarfechas);

        hora11.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
               final Calendar time1 = Calendar.getInstance();
               hora11t = time1.get(Calendar.HOUR_OF_DAY);
               min11t = time1.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(NuevoConsultorio.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                       hora11E.setText(hourOfDay+":"+minute);
                    }

                }, hora11t, min11t,false);
                timePickerDialog.show();
            }
        });


        hora12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar time1 = Calendar.getInstance();
                hora12t = time1.get(Calendar.HOUR_OF_DAY);
                min12t = time1.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(NuevoConsultorio.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        hora12E.setText(hourOfDay+":"+minute);
                    }

                }, hora12t, min12t,false);
                timePickerDialog.show();

            }
        });


        btnguardarfechas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent guardarfechas = new Intent(getApplicationContext(),InicioConsultorio.class);
                startActivity(guardarfechas);
            }
        });


    }
}