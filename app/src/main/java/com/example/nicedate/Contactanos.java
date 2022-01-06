package com.example.nicedate;

import static android.content.ContentValues.TAG;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class Contactanos extends Fragment {
    View Contactanos;
    Button instagram,maps,face,celular,whats;
    public Contactanos() {}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Contactanos = inflater.inflate(R.layout.fragment_contactanos, container, false);
        maps = (Button) Contactanos.findViewById(R.id.mapas);
        instagram = (Button) Contactanos.findViewById(R.id.Instagram);
        face= (Button) Contactanos.findViewById(R.id.face);
        celular= (Button) Contactanos.findViewById(R.id.Celular);
        whats= (Button) Contactanos.findViewById(R.id.whats);

        maps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri gmmIntentUri = Uri.parse("geo:19.119262905413976, -98.77268515577994?q=Nice Date");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });

        instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://instagram.com/_u/nice_date01");
                Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);
                likeIng.setPackage("com.instagram.android");
                try {
                    startActivity(likeIng);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://instagram.com/nice_date01")));
                }
            }
        });

        face.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String facebookId = "fb://profile/112116421296426";
                String urlPage = "https://www.facebook.com/profile.php?id=112116421296426";
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(facebookId )));
                } catch (Exception e) {
                    Log.e(TAG, "Facebook no se encuentra instalado.");
                    //Abre url de pagina.
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(urlPage)));
                }

            }
        });

        celular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent llamar = new Intent(Intent.ACTION_CALL, Uri.parse("tel:5616249371"));
                startActivity(llamar);
            }
        });

        whats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String telefono = "5215616249371";
                String mensaje = "Buen día NiceDate, deseo conocer mas sobre los servicios que ofrece. ";
                try {
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_VIEW);
                    String uri = "whatsapp://send?phone=" + telefono + "&text=" + mensaje;
                    sendIntent.setData(Uri.parse(uri));
                    startActivity(sendIntent);
                }catch (Exception e){
                    Toast.makeText(getActivity(), "Whatsapp no se encuentra instalado", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });

        return Contactanos;
    }
}