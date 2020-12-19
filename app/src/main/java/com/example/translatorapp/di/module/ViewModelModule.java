package com.example.translatorapp.di.module;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.translatorapp.di.ViewModelFactory;
import com.example.translatorapp.di.ViewModelKey;
import com.example.translatorapp.viewmodel.HistoryViewModel;
import com.example.translatorapp.viewmodel.MainViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module(includes = {InteractorModule.class})
public abstract class ViewModelModule {

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel.class)
    abstract ViewModel contributeMainViewModel (MainViewModel mainViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(HistoryViewModel.class)
    abstract ViewModel contributeHistoryViewModel (HistoryViewModel historyViewModel);
}