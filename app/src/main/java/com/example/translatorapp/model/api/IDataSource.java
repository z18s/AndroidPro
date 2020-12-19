package com.example.translatorapp.model.api;

import com.example.translatorapp.model.data.SearchResult;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;

public interface IDataSource<T> {

    Observable<T> getData(String word);
}