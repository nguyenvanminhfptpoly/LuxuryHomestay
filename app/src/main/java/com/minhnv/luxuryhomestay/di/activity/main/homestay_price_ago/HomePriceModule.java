package com.minhnv.luxuryhomestay.di.activity.main.homestay_price_ago;

import com.minhnv.luxuryhomestay.ui.main.adapter.StaggeredAdapter;

import dagger.Module;
import dagger.Provides;

@Module
public class HomePriceModule {
    @HomePriceScope
    @Provides
    static StaggeredAdapter staggeredAdapter(){
        return new StaggeredAdapter();
    }
}
