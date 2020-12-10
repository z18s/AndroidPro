package com.example.translatorapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.translatorapp.model.data.DataModel;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public abstract class BaseViewModel<T extends DataModel> extends ViewModel {

    final MutableLiveData<T> liveDataForView;
    final CompositeDisposable compositeDisposable;

    public BaseViewModel(MutableLiveData<T> liveDataForView, CompositeDisposable compositeDisposable) {
        this.liveDataForView = liveDataForView;
        this.compositeDisposable = compositeDisposable;
    }

    public LiveData<T> getData(String word, boolean isOnline) {
        return liveDataForView;
    }

    @Override
    protected void onCleared() {
        compositeDisposable.clear();
    }
}