package com.minhnv.luxuryhomestay.utils.rx.tracking;

import com.minhnv.luxuryhomestay.utils.rx.SchedulerProvider;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.subjects.BehaviorSubject;

public class TrackingIncaditor extends Observable<Boolean> {

    private SchedulerProvider schedulerProvider;
    private BehaviorSubject<Integer> variable = BehaviorSubject.createDefault(0);
    private Observable<Boolean> isLoading;

    public TrackingIncaditor(SchedulerProvider schedulerProvider) {
    }

    public void init(){
        isLoading = variable
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .onErrorReturnItem(0)
                .map(it -> {return   it > 0;}).share();

    }



    @Override
    protected void subscribeActual(Observer<? super Boolean> observer) {
        isLoading.subscribe(observer);
    }

    private void increment(){
        if(variable.getValue() != null) {
            variable.onNext((variable.getValue()) + 1);
        }
    }

    private void decrement(){
        if(variable.getValue() != null){
            variable.onNext((variable.getValue()) - 1);
        }
    }



}

