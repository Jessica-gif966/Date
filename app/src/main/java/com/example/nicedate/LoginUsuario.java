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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginUsuario extends AppCompatActivity {

    EditText etCorreoUsuario, etContraseñaUsuario;
    Button btnIngresarUsuario;
    String correo, contraseña;
    RequestQueue requestQueue;
    String URL = "https://primordial-recordin.000webhostapp.com/loginusuario.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_usuario);
        requestQueue = Volley.newRequestQueue(LoginUsuario.this);

        etCorreoUsuario = (EditText) findViewById(R.id.etCorreoUsuario);
        etContraseñaUsuario = (EditText) findViewById(R.id.etContraseñaUsuario);
        btnIngresarUsuario = (Button) findViewById(R.id.btnIngresarUsuario);


        btnIngresarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginUsuario();
            }
        });
    }

    public void RegistroUsuario(View view) {
        Intent registrousuario = new Intent(this, RegistroUsuario.class);
        startActivity(registrousuario);

    }

    public void LoginConsultorio(View view) {
        Intent loginconsultorio = new Intent(this, LoginConsultorio.class);
        startActivity(loginconsultorio);

    }

    public void LoginUsuario() {
        correo = etCorreoUsuario.getText().toString();
        contraseña = etContraseñaUsuario.getText().toString();

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

                                    String correoUsuario = etCorreoUsuario.getText().toString();
                                    SharedPreferences prefs = getSharedPreferences("datosUsuario",   Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = prefs.edit();
                                    editor.putString("correoUsuario", correoUsuario);
                                    editor.commit();

                                    Intent inicioUsuario = new Intent(getApplicationContext(),
                                            InicioUsuario.class);
                                    startActivity(inicioUsuario);
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
