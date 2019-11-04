package com.minhnv.luxuryhomestay.di.activity.main.homestay_city;

import com.minhnv.luxuryhomestay.ui.main.adapter.CityDetailAdapter;

import dagger.Module;
import dagger.Provides;

@Module
public class HomeCityModule {

    @HomeCityScope
    @Provides
    static CityDetailAdapter cityDetailAdapter(){
        return new CityDetailAdapter();
    }
}
