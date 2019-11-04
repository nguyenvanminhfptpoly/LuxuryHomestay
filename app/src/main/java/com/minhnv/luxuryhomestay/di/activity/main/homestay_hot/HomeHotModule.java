package com.minhnv.luxuryhomestay.di.activity.main.homestay_hot;

import com.minhnv.luxuryhomestay.ui.main.adapter.StaggeredRecyclerViewAdapter;

import dagger.Module;
import dagger.Provides;

@Module
public class HomeHotModule {

    @HomeHotScope
    @Provides
    static StaggeredRecyclerViewAdapter staggeredRecyclerViewAdapter(){
        return new StaggeredRecyclerViewAdapter();
    }
}
