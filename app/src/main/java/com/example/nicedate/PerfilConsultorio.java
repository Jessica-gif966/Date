package com.example.nicedate;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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

public class PerfilConsultorio extends Fragment {

    View perfilConsultorio;
    EditText etA_Paterno,etA_Materno,etNombre,etTelefono;
    Button btnActualizarConsultorio,btnSeleccionarImagenConsultorio;
    ImageView imagen2;
    public PerfilConsultorio() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        perfilConsultorio = inflater.inflate(R.layout.fragment_perfil_consultorio, container,
                false);

        etA_Paterno= (EditText)perfilConsultorio.findViewById(R.id.etPaternoConsultorio);
        etA_Materno = (EditText)perfilConsultorio.findViewById(R.id.etMaternoConsultorio);
        etNombre = (EditText)perfilConsultorio.findViewById(R.id.etNombreConsultorio);
        etTelefono = (EditText)perfilConsultorio.findViewById(R.id.etTelefonoConsultorio);
        imagen2 = (ImageView) perfilConsultorio.findViewById(R.id.imagenperfilconsultorio);

        btnSeleccionarImagenConsultorio = (Button) perfilConsultorio.findViewById(R.id.btnSeleccionarImagenConsultorio);
        btnActualizarConsultorio = (Button) perfilConsultorio.findViewById (R.id.btnActualizarConsultorio);

        btnSeleccionarImagenConsultorio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarImagen();
            }
        });

        SharedPreferences prefs = getActivity().getSharedPreferences("datosConsultorio",   Context.MODE_PRIVATE);
        String correoDato = prefs.getString("correoConsultorio", "");


        PerfilConsultorio("https://primordial-recordin.000webhostapp.com/buscarconsultorio.php?correo="+correoDato+"");

        btnActualizarConsultorio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               Actualizar("https://primordial-recordin.000webhostapp.com/actualizarconsultorio.php");
            }
        });

        return perfilConsultorio;


    }

    public void cargarImagen(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(intent.createChooser(intent,"Seleccione la Aplicación"),10);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==resultCode){
            Uri path = data.getData();
            imagen2.setImageURI(path);
        }
    }

    public  void PerfilConsultorio(String URL){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        etNombre.setText(jsonObject.getString("nombre"));
                        etA_Paterno.setText(jsonObject.getString("ape_p"));
                        etA_Materno.setText(jsonObject.getString("ape_m"));
                        etTelefono.setText(jsonObject.getString("telefono"));


                    } catch (JSONException e) {
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Error en la conexión", Toast.LENGTH_LONG).show();
            }
        }
        );
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(jsonArrayRequest);
    }

    public void Actualizar(String URL){

        String apellidopaterno = etA_Paterno.getText().toString();
        String apellidomaterno = etA_Materno.getText().toString();
        String nombre = etNombre.getText().toString();
        String telefono = etTelefono.getText().toString();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override

            public void onResponse(String response) {
                Toast.makeText(getActivity(), "Actualización correcta", Toast.LENGTH_LONG).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Error al registrar", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> parametros = new HashMap<String, String>();

                parametros.put("nombre",nombre);
                parametros.put("ape_p",apellidopaterno);
                parametros.put("ape_m",apellidomaterno);
                parametros.put("telefono",telefono);


                SharedPreferences prefs = getActivity().getSharedPreferences("datosConsultorio",   Context.MODE_PRIVATE);
                String correoDato = prefs.getString("correoConsultorio", "");

                parametros.put("correo",correoDato);

                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

    }
}