package com.example.translatorapp.model.room.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.translatorapp.model.room.RoomQueries;

import java.util.List;

@Dao
public interface QueriesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(RoomQueries queries);

    @Insert
    void insert(List<RoomQueries> queries);

    @Update
    void update(RoomQueries queries);

    @Delete
    void delete(RoomQueries queries);

    @Query("SELECT * FROM searches")
    List<RoomQueries> getAll();

    @Query("SELECT * FROM searches WHERE `query` = :word")
    RoomQueries findByWord(String word);
}