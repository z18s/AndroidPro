package com.example.translatorapp.model.api;

import com.example.translatorapp.model.repo.RoomSearch;
import com.example.translatorapp.model.data.SearchResult;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public class DataSourceLocal implements IDataSource<List<SearchResult>> {

    private final RoomSearch localProvider = new RoomSearch();

    @Override
    public Observable<List<SearchResult>> getData(String word) {
        return localProvider.getData(word);
    }
}