package com.example.translatorapp.model.repo;

import com.example.translatorapp.logger.ILogger;
import com.example.translatorapp.model.api.ApiService;
import com.example.translatorapp.model.api.IDataSource;
import com.example.translatorapp.model.data.SearchResult;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitSearch implements IDataSource<List<SearchResult>>, ILogger {

    private static final String TAG = RetrofitSearch.class.getSimpleName();

    private static final String BASE_URL_LOCATIONS = "https://dictionary.skyeng.ru/api/public/v1/";

    @Override
    public Observable<List<SearchResult>> getData(String word) {
        showVerboseLog(TAG, "getData");
        return getService(BaseInterceptor.getInstance()).search(word);
    }

    private ApiService getService(Interceptor interceptor) {
        return createRetrofit(interceptor).create(ApiService.class);
    }

    private Retrofit createRetrofit(Interceptor interceptor) {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL_LOCATIONS)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .client(createOkHttpClient(interceptor))
                .build();
    }

    private OkHttpClient createOkHttpClient(Interceptor interceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();
    }
}