package com.example.nicedate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginConsultorio extends AppCompatActivity {

    Button btnIngresarConsultorio;
    EditText etCorreoConsultorio, etContraseñaConsultorio;
    String correo, contraseña;
    RequestQueue requestQueue;
    String URL = "https://primordial-recordin.000webhostapp.com/loginconsultorio.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_consultorio);

        requestQueue = Volley.newRequestQueue(LoginConsultorio.this);

        etCorreoConsultorio = (EditText) findViewById(R.id.etCorreoConsultorio);
        etContraseñaConsultorio = (EditText) findViewById(R.id.etContraseñaConsultorio);
        btnIngresarConsultorio = (Button) findViewById(R.id.btnIngresarConsultorio);

        btnIngresarConsultorio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginConsultorio();
            }
        });


    }

    public void RegistroConsultorio(View view) {
        Intent registroconsultorio = new Intent(this, RegistrarConsultorio.class);
        startActivity(registroconsultorio);
    }

    public void LoginConsultorio(){
        correo = etCorreoConsultorio.getText().toString();
        contraseña = etContraseñaConsultorio.getText().toString();

        if (correo.isEmpty() && contraseña.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Los campos se encuentran vacios",
                    Toast.LENGTH_LONG).show();
        } else if (correo.isEmpty()) {
            Toast.makeText(getApplicationContext(), "El correo esta vacío",
                    Toast.LENGTH_LONG).show();
        } else if (contraseña.isEmpty()) {
            Toast.makeText(getApplicationContext(), "La contraseña esta vacío",
                    Toast.LENGTH_LONG).show();
        } else {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                    new Response.Listener<String>() {
                        public void onResponse(String serverResponse) {
                            try {
                                JSONObject obj = new JSONObject(serverResponse);
                                Boolean error = obj.getBoolean("error");
                                String mensaje = obj.getString("mensaje");

                                if (error == true) {
                                    Toast.makeText(getApplicationContext(), mensaje,
                                            Toast.LENGTH_LONG).show();
                                } else {

                                    Toast.makeText(getApplicationContext(), mensaje,
                                            Toast.LENGTH_LONG).show();

                                    String correoConsultorio = etCorreoConsultorio.getText().toString();
                                    SharedPreferences prefs = getSharedPreferences("datosConsultorio",   Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = prefs.edit();
                                    editor.putString("correoConsultorio", correoConsultorio);
                                    editor.commit();

                                    Intent inicioConsultorio = new Intent(getApplicationContext(),
                                            InicioConsultorio.class);
                                    startActivity(inicioConsultorio);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), error.toString(),
                                    Toast.LENGTH_LONG).show();

                        }
                    }) {
                protected Map<String, String> getParams() {
                    Map<String, String> parametros = new HashMap<>();
                    parametros.put("correo", correo);
                    parametros.put("contrasena", contraseña);
                    return parametros;
                }
            };

            requestQueue.add(stringRequest);
        }
    }
}