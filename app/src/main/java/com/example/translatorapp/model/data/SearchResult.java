package com.example.translatorapp.model.data;

import com.example.translatorapp.model.data.Meanings;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchResult {

    @SerializedName("text")
    String text;

    @SerializedName("meanings")
    List<Meanings> meanings;

    public String getText() {
        return text;
    }

    public List<Meanings> getMeanings() {
        return meanings;
    }
}