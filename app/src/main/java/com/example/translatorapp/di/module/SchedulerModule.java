package com.example.translatorapp.di.module;

import com.example.translatorapp.utils.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class SchedulerModule {

    @Provides
    public SchedulerProvider getSchedulerProvider() {
        return new SchedulerProvider();
    }
}