package com.example.translatorapp.model.repo;

import com.example.translatorapp.model.data.DataModel;
import com.example.translatorapp.model.data.SearchResult;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

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
                return Observable.just(new DataModel.Success(searchResults)).concatWith(localRepository.putData(word, searchResults));
            });
        } else {
            return localRepository.getData(word).flatMap((searchResults) -> {
                return Observable.just(new DataModel.Success(searchResults));
            });
        }
    }

    public Single<List<String>> getHistoryData() {
        return localRepository.getHistoryData();
    }
}