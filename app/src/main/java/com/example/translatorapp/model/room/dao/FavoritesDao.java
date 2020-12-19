package com.example.translatorapp.model.room.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.translatorapp.model.room.RoomFavorites;

import java.util.List;

@Dao
public interface FavoritesDao {
/*
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(RoomFavorites favorites);

    @Insert
    void insert(List<RoomFavorites> favorites);

    @Update
    void update(RoomFavorites favorites);

    @Delete
    void delete(RoomFavorites favorites);

    @Query("SELECT * FROM favorites")
    List<RoomFavorites> getAll();

    @Query("SELECT * FROM favorites WHERE favorite = :word")
    RoomFavorites findByWord(String word);*/
}