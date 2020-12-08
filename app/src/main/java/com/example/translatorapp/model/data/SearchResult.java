package com.example.translatorapp.model.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

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

    public Observable<String> getTextObservable() {
        return Observable.just(text);
    }
}