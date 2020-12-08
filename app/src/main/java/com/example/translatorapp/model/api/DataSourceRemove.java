package com.example.translatorapp.model.api;

import com.example.translatorapp.model.RetrofitSearch;
import com.example.translatorapp.model.data.SearchResult;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public class DataSourceRemove implements IDataSource<List<SearchResult>> {

    private final RetrofitSearch remoteProvider = new RetrofitSearch();

    @Override
    public Observable<List<SearchResult>> getData(String word) {
        return remoteProvider.getData(word);
    }
}