package com.example.translatorapp.di.module;

import com.example.translatorapp.model.data.SearchResult;
import com.example.translatorapp.model.repo.ISearchRepo;
import com.example.translatorapp.model.repo.MainInteractor;

import java.util.List;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

import static com.example.translatorapp.di.DaggerConstants.SOURCE_LOCAL;
import static com.example.translatorapp.di.DaggerConstants.SOURCE_REMOTE;

@Module
public class InteractorModule {

    @Provides
    MainInteractor provideInteractor(@Named(SOURCE_REMOTE) ISearchRepo<List<SearchResult>> repositoryRemote,
                                     @Named(SOURCE_LOCAL) ISearchRepo<List<SearchResult>> repositoryLocal) {
        return new MainInteractor(repositoryRemote, repositoryLocal);
    }
}