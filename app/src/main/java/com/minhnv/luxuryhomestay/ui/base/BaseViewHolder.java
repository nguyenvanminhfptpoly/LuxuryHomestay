package com.minhnv.luxuryhomestay.ui.base;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.minhnv.luxuryhomestay.data.DataManager;
import com.minhnv.luxuryhomestay.utils.rx.SchedulerProvider;

import io.reactivex.disposables.CompositeDisposable;

public class BaseViewHolder extends RecyclerView.ViewHolder {
    private final DataManager dataManager;

    private final SchedulerProvider schedulerProvider;

    private CompositeDisposable compositeDisposable;


    public BaseViewHolder(@NonNull View itemView, DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(itemView);
        this.dataManager = dataManager;
        this.schedulerProvider = schedulerProvider;
    }

    public DataManager getDataManager() {
        return dataManager;
    }

    public SchedulerProvider getSchedulerProvider() {
        return schedulerProvider;
    }

    public CompositeDisposable getCompositeDisposable() {
        return compositeDisposable;
    }

    public void setCompositeDisposable(CompositeDisposable compositeDisposable) {
        this.compositeDisposable = compositeDisposable;
    }
}
