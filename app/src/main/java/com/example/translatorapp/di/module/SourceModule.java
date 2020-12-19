package com.example.translatorapp.di.module;

import com.example.translatorapp.model.api.IDataSource;
import com.example.translatorapp.model.data.SearchResult;
import com.example.translatorapp.model.repo.ISearchRepo;
import com.example.translatorapp.model.repo.RetrofitSearch;
import com.example.translatorapp.model.repo.RoomSearch;
import com.example.translatorapp.model.repo.SearchRepo;
import com.example.translatorapp.model.room.Database;

import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static com.example.translatorapp.di.DaggerConstants.SOURCE_LOCAL;
import static com.example.translatorapp.di.DaggerConstants.SOURCE_REMOTE;

@Module
public class SourceModule {

    @Provides
    @Singleton
    @Named(SOURCE_REMOTE)
    ISearchRepo<List<SearchResult>> provideRepositoryRemote(@Named(SOURCE_REMOTE) IDataSource<List<SearchResult>> dataSourceRemote) {
        return new SearchRepo(dataSourceRemote);
    }

    @Provides
    @Singleton
    @Named(SOURCE_LOCAL)
    ISearchRepo<List<SearchResult>> provideRepositoryLocal(@Named(SOURCE_LOCAL) IDataSource<List<SearchResult>> dataSourceLocal) {
        return new SearchRepo(dataSourceLocal);
    }

    @Provides
    @Singleton
    @Named(SOURCE_REMOTE)
    IDataSource<List<SearchResult>> provideDataSourceRemote() {
        return new RetrofitSearch();
    }

    @Provides
    @Singleton
    @Named(SOURCE_LOCAL)
    IDataSource<List<SearchResult>> provideDataSourceLocal(Database db) {
        return new RoomSearch(db);
    }
}