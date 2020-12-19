package com.example.translatorapp.model.api;

import com.example.translatorapp.model.repo.RetrofitSearch;
import com.example.translatorapp.model.data.SearchResult;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;

public class DataSourceRemove implements IDataSource<List<SearchResult>> {

    @Inject
    RetrofitSearch remoteProvider;

    @Override
    public Observable<List<SearchResult>> getData(String word) {
        return remoteProvider.getData(word);
    }
}