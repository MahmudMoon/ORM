package com.example.moon.orm.application;

import android.support.v7.widget.AppCompatSpinner;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.app.Application;

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);
    }
}
