package com.example.translatorapp.model.room;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.List;

@Entity(tableName = "searches")
public class RoomQueries {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "query")
    public String query;

    @TypeConverters({SearchConverter.class})
    @ColumnInfo(name = "results")
    public List<String> resultList;

    public RoomQueries(@NonNull String query, List<String> resultList) {
        this.query = query;
        this.resultList = resultList;
    }

    @NonNull
    public String getQuery() {
        return query;
    }

    public List<String> getResultList() {
        return resultList;
    }
}