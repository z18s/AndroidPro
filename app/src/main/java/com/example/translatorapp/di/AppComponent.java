package com.example.translatorapp.di;

import android.app.Application;

import com.example.translatorapp.application.TranslatorApp;
import com.example.translatorapp.di.module.ActivityModule;
import com.example.translatorapp.di.module.InteractorModule;
import com.example.translatorapp.di.module.SchedulerModule;
import com.example.translatorapp.di.module.SourceModule;
import com.example.translatorapp.di.module.ViewModelModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        SchedulerModule.class,
        ActivityModule.class,
        ViewModelModule.class,
        InteractorModule.class,
        SourceModule.class,
        AndroidSupportInjectionModule.class
})
public interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }

    void inject(TranslatorApp translatorApp);
}