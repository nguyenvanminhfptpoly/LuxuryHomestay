package com.minhnv.luxuryhomestay.di.component;

import android.app.Application;

import com.minhnv.luxuryhomestay.BaseApplication;
import com.minhnv.luxuryhomestay.di.activity.ActivityBuilder;
import com.minhnv.luxuryhomestay.di.module.AppModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

@Singleton
@Component(modules = {AndroidInjectionModule.class, AppModule.class, ActivityBuilder.class})
public interface AppComponent {

    void inject(BaseApplication baseApplication);

    @Component.Builder
    interface Builder{
        @BindsInstance
        Builder application(Application application);

        AppComponent build();

    }
}
