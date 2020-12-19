package com.example.translatorapp.di.module;

import com.example.translatorapp.view.HistoryActivity;
import com.example.translatorapp.view.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract MainActivity contributeMainActivity();

    @ContributesAndroidInjector
    abstract HistoryActivity contributeHistoryActivity();
}