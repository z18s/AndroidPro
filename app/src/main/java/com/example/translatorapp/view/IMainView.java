package com.example.translatorapp.view;

import com.example.translatorapp.model.data.DataStatus;

public interface IMainView {
    void init();
    void updateData(DataStatus status, Integer progress, String text);
}