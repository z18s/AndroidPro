package com.example.translatorapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.translatorapp.model.data.DataModel;
import com.example.translatorapp.model.repo.MainInteractor;
import com.example.translatorapp.utils.ISchedulerProvider;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public abstract class BaseViewModel<T extends DataModel> extends ViewModel {

    final MutableLiveData<T> liveDataForView;
    final MainInteractor interactor;
    final CompositeDisposable compositeDisposable;
    final ISchedulerProvider schedulerProvider;

    public BaseViewModel(MutableLiveData<T> liveDataForView, MainInteractor interactor, CompositeDisposable compositeDisposable, ISchedulerProvider schedulerProvider) {
        this.liveDataForView = liveDataForView;
        this.interactor = interactor;
        this.compositeDisposable = compositeDisposable;
        this.schedulerProvider = schedulerProvider;
    }

    public LiveData<T> getData(String word, boolean isOnline) {
        return liveDataForView;
    }

    @Override
    protected void onCleared() {
        compositeDisposable.clear();
    }
}