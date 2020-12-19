package com.example.translatorapp.model.repo;

import com.example.translatorapp.logger.ILogger;
import com.example.translatorapp.model.api.IDataSource;
import com.example.translatorapp.model.data.SearchResult;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public class SearchRepo implements ISearchRepo<List<SearchResult>>, ILogger {

    private static final String TAG = SearchRepo.class.getSimpleName();

    private final IDataSource<List<SearchResult>> dataSource;

    public SearchRepo(IDataSource<List<SearchResult>> dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Observable<List<SearchResult>> getData(String word) {
        showVerboseLog(TAG, "getData");
        return dataSource.getData(word);
    }

    @Override
    public Single<List<String>> getHistoryData() {
        showVerboseLog(TAG, "getHistoryData");
        return dataSource.getHistoryData();
    }

    @Override
    public Completable putData(String word, List<SearchResult> results) {
        showVerboseLog(TAG, "putData");
        return dataSource.putData(word, results);
    }
}