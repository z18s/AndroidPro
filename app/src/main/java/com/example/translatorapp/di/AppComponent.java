package com.example.translatorapp.di;

import android.app.Application;

import com.example.translatorapp.di.module.ActivityModule;
import com.example.translatorapp.di.module.DatabaseModule;
import com.example.translatorapp.di.module.InteractorModule;
import com.example.translatorapp.di.module.SchedulerModule;
import com.example.translatorapp.di.module.SourceModule;
import com.example.translatorapp.di.module.ViewModelModule;
import com.example.translatorapp.view.MainActivity;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

@Singleton
@Component(modules = {
        SchedulerModule.class,
        ActivityModule.class,
        ViewModelModule.class,
        InteractorModule.class,
        SourceModule.class,
        DatabaseModule.class
})
public interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }

    void inject(MainActivity mainActivity);
}