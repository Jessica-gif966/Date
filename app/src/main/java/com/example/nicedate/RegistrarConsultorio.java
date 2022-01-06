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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class RegistrarConsultorio extends AppCompatActivity {
    Button btnRegistrarConsultorio;
    EditText etA_Paterno, etA_Materno, etNombre, etTelefono, etCorreo, etContrasena1, etContrasena2, etCodigoSeguridad;
    String contraseñaFinal;
    RequestQueue requestQueue;
    String URL = "https://primordial-recordin.000webhostapp.com/insertarconsultorio.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_consultorio);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        requestQueue = Volley.newRequestQueue(RegistrarConsultorio.this);

        etA_Paterno = (EditText) findViewById(R.id.etPaternoConsultorio);
        etA_Materno = (EditText) findViewById(R.id.etMaternoConsultorio);
        etNombre = (EditText) findViewById(R.id.etNombreConsultorio);
        etTelefono = (EditText) findViewById(R.id.etTelefonoConsultorio);
        etCorreo = (EditText) findViewById(R.id.etCorreoConsultorio);
        etContrasena1 = (EditText) findViewById(R.id.etContraseña1Consultorio);
        etContrasena2 = (EditText) findViewById(R.id.etContraseña2Consultorio);
        etCodigoSeguridad = (EditText) findViewById(R.id.etCodigoSeguirdad);


        btnRegistrarConsultorio = (Button) findViewById(R.id.btnRegistrarConsultorio);

        btnRegistrarConsultorio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String contraseña1 = etContrasena1.getText().toString();
                String contraseña2 = etContrasena2.getText().toString();

                contraseñaFinal = "";

                if (contraseña1.equals(contraseña2) ){
                    contraseñaFinal = contraseña1;

                    String c = etCodigoSeguridad.getText().toString();
                    int codigo = Integer.parseInt(c);

                    if (codigo == 2021) {
                        MetodoRegistrarConsultorio();
                    } else {
                        Toast.makeText(getApplicationContext(), "Código invalido", Toast.LENGTH_LONG).show();
                    }

                }else {
                    Toast.makeText(getApplicationContext(), "Las contraseñas no coinciden", Toast.LENGTH_LONG).show();

                }
            }
        });
    }

    public void MetodoRegistrarConsultorio(){

        String correo = etCorreo.getText().toString();
        String apellidopaterno = etA_Paterno.getText().toString();
        String apellidomaterno = etA_Materno.getText().toString();
        String nombre = etNombre.getText().toString();
        String telefono = etTelefono.getText().toString();

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

                                String correoConsultorio = etCorreo.getText().toString();
                                SharedPreferences prefs = getSharedPreferences("datosConsultorio",   Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = prefs.edit();
                                editor.putString("correoConsultorio", correoConsultorio);
                                editor.commit();

                                Toast.makeText(getApplicationContext(), mensaje,
                                        Toast.LENGTH_LONG).show();

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
                parametros.put("correo",correo);
                parametros.put("nombre",nombre);
                parametros.put("ape_p",apellidopaterno);
                parametros.put("ape_m",apellidomaterno);
                parametros.put("telefono",telefono);
                parametros.put("contrasena", contraseñaFinal);
                return parametros;
            }
        };

        requestQueue.add(stringRequest);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
