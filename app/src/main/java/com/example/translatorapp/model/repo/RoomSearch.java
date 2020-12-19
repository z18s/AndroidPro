package com.example.translatorapp.model.repo;

import com.example.translatorapp.logger.ILogger;
import com.example.translatorapp.model.api.IDataSource;
import com.example.translatorapp.model.data.SearchResult;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RoomSearch implements IDataSource<List<SearchResult>>, ILogger {

public class RoomSearch implements IDataSource<List<SearchResult>> {

    @Override
    public Observable<List<SearchResult>> getData(String word) {
        return null;
    }
}