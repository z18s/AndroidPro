package com.example.translatorapp.application;

import android.app.Application;

import com.example.translatorapp.di.AppComponent;
import com.example.translatorapp.di.DaggerAppComponent;

public class TranslatorApp extends Application {

    public static TranslatorApp instance;

    private AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        component = DaggerAppComponent
                .builder()
                .application(this)
                .build();
    }

    public AppComponent getComponent() {
        return component;
    }
}