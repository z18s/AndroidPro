package com.example.translatorapp.view;

import com.example.translatorapp.model.data.DataModel;

public interface IMainView {
    void init();
    void renderData(DataModel dataModel);
}