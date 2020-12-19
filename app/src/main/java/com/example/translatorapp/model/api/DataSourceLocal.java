package com.example.translatorapp.model.api;

import com.example.translatorapp.model.data.SearchResult;
import com.example.translatorapp.model.repo.RoomSearch;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public class DataSourceLocal implements IDataSource<List<SearchResult>> {

    @Inject
    RoomSearch localProvider;

    @Override
    public Observable<List<SearchResult>> getData(String word) {
        return localProvider.getData(word);
    }

    @Override
    public Single<List<String>> getHistoryData() {
        return localProvider.getHistoryData();
    }

    @Override
    public Completable putData(String word, List<SearchResult> results) {
        return localProvider.putData(word, results);
    }
}