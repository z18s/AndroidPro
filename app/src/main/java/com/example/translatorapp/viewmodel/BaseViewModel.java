package com.example.translatorapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.translatorapp.model.repo.MainInteractor;
import com.example.translatorapp.utils.ISchedulerProvider;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public abstract class BaseViewModel<T> extends ViewModel {

    final MutableLiveData<T> liveDataForView;
    final MutableLiveData<T> liveHistoryDataForView;
    final MainInteractor interactor;
    final CompositeDisposable compositeDisposable;
    final ISchedulerProvider schedulerProvider;

    public BaseViewModel(MutableLiveData<T> liveDataForView, MutableLiveData<T> liveHistoryDataForView, MainInteractor interactor, CompositeDisposable compositeDisposable, ISchedulerProvider schedulerProvider) {
        this.liveDataForView = liveDataForView;
        this.liveHistoryDataForView = liveHistoryDataForView;
        this.interactor = interactor;
        this.compositeDisposable = compositeDisposable;
        this.schedulerProvider = schedulerProvider;
    }

    public LiveData<T> getData(String word, boolean isOnline) {
        return liveDataForView;
    }

    public LiveData<T> getHistoryData() {
        return liveHistoryDataForView;
    }

    @Override
    protected void onCleared() {
        compositeDisposable.clear();
    }
}