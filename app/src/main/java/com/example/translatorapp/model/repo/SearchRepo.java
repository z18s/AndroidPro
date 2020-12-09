package com.example.translatorapp.model.repo;

import com.example.translatorapp.model.data.SearchResult;
import com.example.translatorapp.model.api.IDataSource;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public class SearchRepo implements ISearchRepo<List<SearchResult>> {

    private final IDataSource<List<SearchResult>> dataSource;

    public SearchRepo(IDataSource<List<SearchResult>> dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Observable<List<SearchResult>> getData(String word) {
        return dataSource.getData(word);
    }
}