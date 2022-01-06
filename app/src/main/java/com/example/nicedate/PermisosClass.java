package com.example.nicedate;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;

import java.util.ArrayList;
import java.util.List;

public class PermisosClass {

    Context context1 = null;

    public PermisosClass(Context context1){
        this.context1 = context1;
    }
    public List<String> comprobarPermisos(){
        //Creamos el arreglo
        List<String> permisos = new ArrayList<String>();

        //Testeamos los permisos del manifest
        if(context1.checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_DENIED)
            permisos.add(Manifest.permission.CALL_PHONE);

        return permisos;
    }
}
