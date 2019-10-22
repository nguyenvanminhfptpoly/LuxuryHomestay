package com.minhnv.luxuryhomestay.ui.base;

import android.app.ProgressDialog;

import androidx.lifecycle.ViewModel;

import com.minhnv.luxuryhomestay.data.DataManager;
import com.minhnv.luxuryhomestay.utils.rx.SchedulerProvider;
import com.minhnv.luxuryhomestay.utils.rx.tracking.TrackingIncaditor;

import java.lang.ref.WeakReference;

import io.reactivex.disposables.CompositeDisposable;

public abstract class BaseViewModel<N> extends ViewModel {
    private final DataManager dataManager;

    private final SchedulerProvider schedulerProvider;

    private CompositeDisposable compositeDisposable;

    private  TrackingIncaditor trackingIncaditor;

    private ProgressDialog progressDialog;

    private WeakReference<N> mNavigator;
    protected BaseViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        this.dataManager = dataManager;
        this.schedulerProvider = schedulerProvider;
        this.compositeDisposable = new CompositeDisposable();
        this.trackingIncaditor = new TrackingIncaditor(schedulerProvider);
    }

    protected N getNavigator(){
        return mNavigator.get();
    }

    public void setNavigator(N navigator){
        this.mNavigator = new WeakReference<>(navigator);
    }

    @Override
    protected void onCleared() {
        compositeDisposable.dispose();
        super.onCleared();
    }

    public CompositeDisposable getCompositeDisposable(){
        return compositeDisposable;
    }

    public DataManager getDataManager(){
        return dataManager;
    }

    public TrackingIncaditor getTrackingIncaditor(){
        return trackingIncaditor;
    }

    public SchedulerProvider getSchedulerProvider(){
        return schedulerProvider;
    }


}
