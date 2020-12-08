package com.example.translatorapp.presenter.list;

import com.example.translatorapp.view.item.IItemView;

public interface IListPresenter<V extends IItemView> {
    void onItemClick(V view);
    void bindView(V view);
    int getCount();
}