package com.example.translatorapp.model.api;

import io.reactivex.rxjava3.core.Observable;

public interface IDataSource<T> {

    Observable<T> getData(String word);
}