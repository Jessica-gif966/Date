package com.example.nicedate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class InicioConsultorio extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_consultorio);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mostrarfragment(new CitasConsultorio());

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.nav_consultorio);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuitem) {

                if (menuitem.getItemId() == R.id.CitasC) {
                    mostrarfragment(new CitasConsultorio());
                }

                if (menuitem.getItemId() == R.id.ConsultoriosC) {
                    mostrarfragment(new Consultorio());
                }

                if (menuitem.getItemId() == R.id.PerfilC) {
                    mostrarfragment(new PerfilConsultorio());
                }

                return true;
            }
        });
    }


    private void mostrarfragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentusuario, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sesion,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case  R.id.cerrarsesion:
                Intent salir =new Intent(getApplicationContext(),TipoCuenta.class);
                startActivity(salir);
                finish();
                break;
            case R.id.salir:
                finishAffinity();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}