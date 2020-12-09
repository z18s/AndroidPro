package com.example.translatorapp.model.repo;

import io.reactivex.rxjava3.core.Observable;

public interface ISearchRepo<T> {

    Observable<T> getData(String word);
}