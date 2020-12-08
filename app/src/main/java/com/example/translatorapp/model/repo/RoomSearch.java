package com.example.translatorapp.model.repo;

import com.example.translatorapp.model.api.IDataSource;
import com.example.translatorapp.model.data.SearchResult;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public class RoomSearch implements IDataSource<List<SearchResult>> {

    @Override
    public Observable<List<SearchResult>> getData(String word) {
        return null;
    }
}