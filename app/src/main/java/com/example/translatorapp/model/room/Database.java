package com.example.translatorapp.model.room;

import androidx.room.RoomDatabase;

import com.example.translatorapp.model.room.dao.QueriesDao;

@androidx.room.Database(entities = {RoomQueries.class}, version = 1, exportSchema = false)
public abstract class Database extends RoomDatabase {

    public static final String DB_NAME = "database.db";

    public abstract QueriesDao queriesDao();
    //public abstract FavoritesDao favoritesDao();
}