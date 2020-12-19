package com.example.translatorapp.model.repo;

import com.example.translatorapp.model.data.SearchResult;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;

public interface ISearchRepo<T> {

    Observable<T> getData(String word);
}