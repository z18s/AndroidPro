package com.example.translatorapp.model.room;

import androidx.room.TypeConverter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SearchConverter {

    @TypeConverter
    public List<String> toList(String str) {
        return Arrays.asList(str.split(";;"));
    }

    @TypeConverter
    public String fromList(List<String> list) {
        return list.stream().collect(Collectors.joining(";;"));
    }
}