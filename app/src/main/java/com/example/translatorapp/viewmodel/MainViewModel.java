package com.example.translatorapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.translatorapp.logger.ILogger;
import com.example.translatorapp.model.api.DataSourceLocal;
import com.example.translatorapp.model.api.DataSourceRemove;
import com.example.translatorapp.model.data.DataModel;
import com.example.translatorapp.model.repo.MainInteractor;
import com.example.translatorapp.model.repo.SearchRepo;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainViewModel extends BaseViewModel<DataModel> implements ILogger {

    private static final String TAG = MainViewModel.class.getSimpleName();

    private DataModel dataModel = null;

    private MainInteractor interactor = new MainInteractor(
            new SearchRepo(new DataSourceRemove()),
            new SearchRepo(new DataSourceLocal())
    );

    public MainViewModel() {
        super(new MutableLiveData<DataModel>(), new CompositeDisposable());
    }

    @Override
    public LiveData<DataModel> getData(String word, boolean isOnline) {
        super.compositeDisposable.add(
                interactor.getData(word, isOnline)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe((d) -> liveDataForView.setValue(new DataModel.Loading(0)))
                        .subscribeWith(getDataObserver())
        );
        return super.getData(word, isOnline);
    }

    private DisposableObserver<DataModel> getDataObserver() {
        return new DisposableObserver<DataModel>() {
            public void onNext(@NonNull DataModel data) {
                showVerboseLog(TAG, "DisposableObserver.onNext");
                dataModel = data;
                liveDataForView.setValue(data);
            }

            public void onError(@NonNull Throwable e) {
                showVerboseLog(TAG, "DisposableObserver.onError");
                liveDataForView.setValue(new DataModel.Error(e));
            }

            public void onComplete() {
            }
        };
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}