package com.example.nicedate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Base64;
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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PerfilUsuario extends Fragment {

    View PerfilUsuario;
    EditText etA_Paterno,etA_Materno,etNombre,etTelefono;
    Button btnActualizarUsuario;
    ImageView imagen;
    Bitmap bitmap;

    String dato = null;
    Bitmap imagen1;

    public PerfilUsuario() {}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       PerfilUsuario= inflater.inflate(R.layout.fragment_perfil_usuario, container, false);

        etA_Paterno= (EditText)PerfilUsuario.findViewById(R.id.etPaternoUsuario);
        etA_Materno = (EditText)PerfilUsuario.findViewById(R.id.etMaternoUsuario);
        etNombre = (EditText)PerfilUsuario.findViewById(R.id.etNombreUsuario);
        etTelefono = (EditText)PerfilUsuario.findViewById(R.id.etTelefonoUsuario);
        btnActualizarUsuario = (Button) PerfilUsuario.findViewById (R.id.btnActualizarUsuario);

        imagen = (ImageView) PerfilUsuario.findViewById(R.id.imagenperfilusuario);


        imagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarImagne();
            }
        });

        SharedPreferences prefs = getActivity().getSharedPreferences("datosUsuario",   Context.MODE_PRIVATE);
        String correoDato = prefs.getString("correoUsuario", "");

        perfilUsuario("https://primordial-recordin.000webhostapp.com/buscarusuario.php?correo="+correoDato+"");

        btnActualizarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                actualizar("https://primordial-recordin.000webhostapp.com/actualizarusuario.php");
            }
        });

       return  PerfilUsuario;
    }

    public void cargarImagne(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(intent.createChooser(intent,"Seleccione la Aplicación"),10);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==resultCode){
            Uri path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(),path);
                imagen.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private void perfilUsuario (String URL){

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


                       // byte[] bytes = Base64.decode(dato,Base64.DEFAULT);
                      //  imagen1= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                       // imagen.setImageBitmap(imagen1.getImagen());

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




    public void actualizar (String URL){

        String apellidopaterno = etA_Paterno.getText().toString();
        String apellidomaterno = etA_Materno.getText().toString();
        String nombre = etNombre.getText().toString();
        String telefono = etTelefono.getText().toString();
        String imagenperfil = convertirimagen(bitmap);

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
                parametros.put("imagen",imagenperfil);

                SharedPreferences prefs = getActivity().getSharedPreferences("datosUsuario",   Context.MODE_PRIVATE);
                String correoDato = prefs.getString("correoUsuario", "");

                parametros.put("correo",correoDato);

                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    private String convertirimagen(Bitmap bitmap) {
        ByteArrayOutputStream array = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,array);
        byte[] imagenByte = array.toByteArray();
        String imangenString = Base64.encodeToString(imagenByte,Base64.DEFAULT);

        return  imangenString;
    }


}


