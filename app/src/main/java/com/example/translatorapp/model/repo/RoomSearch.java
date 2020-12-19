package com.example.translatorapp.model.repo;

import com.example.translatorapp.logger.ILogger;
import com.example.translatorapp.model.api.IDataSource;
import com.example.translatorapp.model.data.SearchResult;
import com.example.translatorapp.model.room.Database;
import com.example.translatorapp.model.room.RoomQueries;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RoomSearch implements IDataSource<List<SearchResult>>, ILogger {

    private static final String TAG = RoomSearch.class.getSimpleName();

    private final Database db;

    public RoomSearch(Database db) {
        this.db = db;
    }

    @Override
    public Observable<List<SearchResult>> getData(String word) {
        showVerboseLog(TAG, "getData");
        return Observable.fromCallable(() -> {

            RoomQueries roomQueries = db.queriesDao().findByWord(word);
            List<String> resultList = roomQueries.getResultList();

            List<SearchResult> results = new ArrayList<>();

            for (String resultItem : resultList) {

                SearchResult result = new SearchResult(resultItem, new ArrayList<>());
                results.add(result);
            }
            return results;
        });
    }

    @Override
    public Single<List<String>> getHistoryData() {
        showVerboseLog(TAG, "getHistoryData");
        return Single.fromCallable(() -> {
            List<RoomQueries> queriesList = db.queriesDao().getAll();
            showVerboseLog(TAG, "queriesList " + queriesList);
            List<String> results = new ArrayList<>();
            showVerboseLog(TAG, "results - " + results);
            for (RoomQueries queriesItem : queriesList) {
                String result = queriesItem.getQuery();
                showVerboseLog(TAG, "result - " + result);
                results.add(result);
            }
            showVerboseLog(TAG, "results - " + results);
            return results;
        });
    }

    @Override
    public Completable putData(String word, List<SearchResult> results) {
        showVerboseLog(TAG, "putData");
        return Completable.fromAction(() -> {
            List<String> resultList = new ArrayList<>();

            for (SearchResult result : results) {
                resultList.add(result.getText());
            }
            db.queriesDao().insert(new RoomQueries(word, resultList));
            showVerboseLog(TAG, "putData - DONE");
        }).subscribeOn(Schedulers.io());
    }
}