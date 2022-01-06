package com.example.nicedate;

import android.app.Application;

import com.androidnetworking.AndroidNetworking;
//import com.androidnetworking.AndroidNetworking;

public class Capp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //AndroidNetworking.initialize(getApplicationContext());
        AndroidNetworking.initialize(getApplicationContext());
    }
}
