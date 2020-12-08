package com.example.translatorapp.presenter;

import com.example.translatorapp.logger.ILogger;
import com.example.translatorapp.model.data.DataModel;
import com.example.translatorapp.model.MainInteractor;
import com.example.translatorapp.model.api.DataSourceLocal;
import com.example.translatorapp.model.api.DataSourceRemove;
import com.example.translatorapp.model.repo.SearchRepo;
import com.example.translatorapp.view.IMainView;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainPresenter<T extends DataModel, V extends IMainView> implements ILogger {

    private static final String TAG = MainPresenter.class.getSimpleName();

    private V currentView = null;

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    private MainInteractor interactor = new MainInteractor(
            new SearchRepo(new DataSourceRemove()),
            new SearchRepo(new DataSourceLocal())
    );

    public void attachView(V view) {
        showVerboseLog(TAG, "attachView");
        if (view != currentView) {
            currentView = view;
        }
    }

    public void detachView(V view) {
        showVerboseLog(TAG, "detachView");
        compositeDisposable.clear();
        if (view == currentView) {
            currentView = null;
        }
    }

    public void onClick(String word, boolean isOnline) {
        showVerboseLog(TAG, "onClick");
        compositeDisposable.add(
                interactor.getData(word, isOnline)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(getObserver())
        );
    }

    private DisposableObserver<DataModel> getObserver() {
        return new DisposableObserver<DataModel>() {
            public void onNext(@NonNull DataModel data) {

                currentView.renderData(data);
            }

            public void onError(@NonNull Throwable e) {
                currentView.renderData(new DataModel.Error(e));
            }

            public void onComplete() {
            }
        };
    }
}