package com.example.translatorapp._presenter;

import com.example.translatorapp.logger.ILogger;
import com.example.translatorapp.model.repo.MainInteractor;
import com.example.translatorapp.model.api.DataSourceLocal;
import com.example.translatorapp.model.api.DataSourceRemove;
import com.example.translatorapp.model.data.DataModel;
import com.example.translatorapp.model.data.DataStatus;
import com.example.translatorapp.model.data.SearchResult;
import com.example.translatorapp.model.repo.SearchRepo;
import com.example.translatorapp._presenter.list.IResultListPresenter;
import com.example.translatorapp._view.IMainView;
import com.example.translatorapp.view.item.IResultItemView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
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
                        .subscribeWith(getDataObserver())
        );
    }

    private DisposableObserver<DataModel> getDataObserver() {
        return new DisposableObserver<DataModel>() {
            public void onNext(@NonNull DataModel data) {
                showVerboseLog(TAG, "DisposableObserver.onNext");
                if (data instanceof DataModel.Success) {
                    showVerboseLog(TAG, "DataModel.Success");
                    if (((DataModel.Success) data).getData() == null || ((DataModel.Success) data).getData().isEmpty()) {
                        currentView.updateData(DataStatus.EMPTY, null, null);
                    } else {
                        resultListPresenter.data.clear();
                        resultListPresenter.data.addAll(((DataModel.Success)data).getData());
                        currentView.updateData(DataStatus.SUCCESS, null, null);
                    }
                } else if (data instanceof DataModel.Loading) {
                    showVerboseLog(TAG, "DataModel.Loading");
                    currentView.updateData(DataStatus.LOADING, ((DataModel.Loading) data).getProgress(), null);
                } else if (data instanceof DataModel.Error) {
                    showVerboseLog(TAG, "DataModel.Error");
                    currentView.updateData(DataStatus.ERROR, null, ((DataModel.Error) data).getError().getMessage());
                }
            }

            public void onError(@NonNull Throwable e) {
                showVerboseLog(TAG, "DisposableObserver.onError");
            }

            public void onComplete() {
            }
        };
    }

    private void setRecyclerData(IResultItemView view, SearchResult searchResult) {
        showVerboseLog(TAG, "setRecyclerData");

        searchResult.getTextObservable().subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
            }

            @Override
            public void onNext(@NonNull String headerText) {
                view.setHeader(headerText);
                view.setDescription(searchResult.getMeanings().get(0).getTranslation().getTranslation());
                showVerboseLog(TAG, "setRecyclerData.onNext - " + headerText);
            }

            @Override
            public void onError(@NonNull Throwable e) {
            }

            @Override
            public void onComplete() {
            }
        });
    }

    private final ResultListPresenter resultListPresenter = new ResultListPresenter();

    public class ResultListPresenter implements IResultListPresenter {

        private final List<SearchResult> data = new ArrayList<>();

        @Override
        public void onItemClick(IResultItemView view) {
            showVerboseLog(TAG, "ResultListPresenter.onItemClick - " + view.getPos());
        }

        @Override
        public void bindView(IResultItemView view) {
            int index = view.getPos();
            SearchResult searchResult = data.get(index);
            setRecyclerData(view, searchResult);
        }

        @Override
        public int getCount() {
            return data.size();
        }
    }

    public IResultListPresenter getPresenter() {
        return resultListPresenter;
    }
}