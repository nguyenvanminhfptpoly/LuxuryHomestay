package com.minhnv.luxuryhomestay.di.module;

import android.app.Application;
import android.content.Context;

import androidx.room.Room;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.minhnv.luxuryhomestay.BaseApplication;
import com.minhnv.luxuryhomestay.data.AppDataManager;
import com.minhnv.luxuryhomestay.data.DataManager;
import com.minhnv.luxuryhomestay.data.local.db.AppDatabase;
import com.minhnv.luxuryhomestay.data.local.db.AppDbHelper;
import com.minhnv.luxuryhomestay.data.local.db.DbHelper;
import com.minhnv.luxuryhomestay.data.local.db.dao.UserDao;
import com.minhnv.luxuryhomestay.data.local.preference.AppPreferenceHelper;
import com.minhnv.luxuryhomestay.data.local.preference.PreferenceHelper;
import com.minhnv.luxuryhomestay.data.remote.ApiHelper;
import com.minhnv.luxuryhomestay.data.remote.AppApiHelper;
import com.minhnv.luxuryhomestay.di.DatabaseInfo;
import com.minhnv.luxuryhomestay.di.PreferenceInfo;
import com.minhnv.luxuryhomestay.utils.ApiUtils;
import com.minhnv.luxuryhomestay.utils.AppConstants;
import com.minhnv.luxuryhomestay.utils.rx.AppSchedulerProvider;
import com.minhnv.luxuryhomestay.utils.rx.SchedulerProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class AppModule {
    @Provides
    @Singleton
    ApiHelper provideApiHelper(Gson gson) {
        return new AppApiHelper(gson);
    }

    @Provides
    @Singleton
    Context provideContext(Application application) {
        return application;
    }

    @Provides
    @Singleton
    BaseApplication provideBaseApplication(BaseApplication baseApplication) {
        return baseApplication;
    }

    @Provides
    @Singleton
    DataManager provideDataManager(AppDataManager appDataManager) {
        return appDataManager;
    }

    @Provides
    @Singleton
    Gson provideGson() {
        return new GsonBuilder().setLenient().create();
    }

    @Provides
    SchedulerProvider provideSchedulerProvider() {
        return new AppSchedulerProvider();
    }

    @Provides
    @PreferenceInfo
    String providePreferenceName() {
        return AppConstants.PREF_NAME;
    }

    @Provides
    @Singleton
    PreferenceHelper providePreferencesHelper(AppPreferenceHelper appPreferencesHelper) {
        return appPreferencesHelper;
    }

    @Singleton
    @Provides
    static Retrofit provideRetrofitInstance(){
        return new Retrofit.Builder()
                .baseUrl(ApiUtils.baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    AppDatabase provideAppDatabase(@DatabaseInfo String dbName,Context context){
        return Room.databaseBuilder(context,AppDatabase.class,dbName).fallbackToDestructiveMigration().build();
    }

    @Provides
    @Singleton
    UserDao provideUserDao(AppDatabase appDatabase) {
        return appDatabase.userDao();
    }

    @Provides
    @DatabaseInfo
    String provideDatabaseName(){
        return AppConstants.DB_NAME;
    }

    @Provides
    @Singleton
    DbHelper provideDbHelper(AppDbHelper helper){
        return helper;
    }



}
