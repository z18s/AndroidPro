package com.example.translatorapp.model.api;

import com.example.translatorapp.model.data.SearchResult;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("words/search")
    Observable<List<SearchResult>> search(@Query("search") String wordToSearch);
}