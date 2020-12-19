package com.example.translatorapp.model.room;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "favorites")
public class RoomFavorites {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "favorite")
    public String favorite;

    public RoomFavorites(@NonNull String favorite) {
        this.favorite = favorite;
    }

    @NonNull
    public String getFavorite() {
        return favorite;
    }
}