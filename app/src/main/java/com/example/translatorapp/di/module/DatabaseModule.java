package com.example.translatorapp.di.module;

import androidx.room.Room;

import com.example.translatorapp.application.TranslatorApp;
import com.example.translatorapp.model.room.Database;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DatabaseModule {

    @Singleton
    @Provides
    Database database() {
        return Room.databaseBuilder(TranslatorApp.instance, Database.class, Database.DB_NAME)
                .build();
    }
}