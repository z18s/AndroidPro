package com.example.translatorapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.translatorapp.logger.ILogger;
import com.example.translatorapp.model.repo.MainInteractor;
import com.example.translatorapp.utils.SchedulerProvider;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;

public class HistoryViewModel extends BaseViewModel<List<String>> implements ILogger {

    private static final String TAG = HistoryViewModel.class.getSimpleName();

    private List<String> data;

    @Inject
    public HistoryViewModel(MainInteractor interactor, SchedulerProvider schedulerProvider) {
        super(null, new MutableLiveData<>(), interactor, new CompositeDisposable(), schedulerProvider);
        showVerboseLog(TAG, "new HistoryViewModel");
    }

    @Override
    public LiveData<List<String>> getHistoryData() {
        onCleared();
        super.compositeDisposable.add(
                interactor.getHistoryData()
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .doOnSubscribe((d) -> liveDataForView.setValue(Collections.singletonList("Loading")))
                        .subscribeWith(getHistoryDataObserver())
        );
        return super.getHistoryData();
    }

    private DisposableSingleObserver<List<String>> getHistoryDataObserver() {
        return new DisposableSingleObserver<List<String>>() {

            @Override
            public void onSuccess(@NonNull List<String> list) {
                showVerboseLog(TAG, "DisposableSingleObserver.onSuccess");
                data = list;
                liveHistoryDataForView.postValue(list);
            }

            public void onError(@NonNull Throwable e) {
                showVerboseLog(TAG, "DisposableSingleObserver.onError");
                liveHistoryDataForView.postValue(Collections.singletonList(e.getMessage()));
            }
        };
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}