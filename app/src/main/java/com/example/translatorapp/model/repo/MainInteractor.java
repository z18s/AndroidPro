package com.example.translatorapp.model.repo;

import com.example.translatorapp.model.data.DataModel;
import com.example.translatorapp.model.data.SearchResult;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public class MainInteractor {

    private final ISearchRepo<List<SearchResult>> remoteRepository;
    private final ISearchRepo<List<SearchResult>> localRepository;

    public MainInteractor(ISearchRepo<List<SearchResult>> remoteRepository, ISearchRepo<List<SearchResult>> localRepository) {
        this.remoteRepository = remoteRepository;
        this.localRepository = localRepository;
    }

    public Observable<DataModel> getData(String word, boolean isOnline) {
        if (isOnline) {
            return remoteRepository.getData(word).flatMap((searchResults) -> {
                return Observable.just(new DataModel.Success(searchResults));
            });
        } else {
            return localRepository.getData(word).flatMap((searchResults) -> {
                return Observable.just(new DataModel.Success(searchResults));
            });
        }
    }
}