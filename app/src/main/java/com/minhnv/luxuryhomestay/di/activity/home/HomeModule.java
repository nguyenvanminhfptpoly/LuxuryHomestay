package com.minhnv.luxuryhomestay.di.activity.home;

import com.minhnv.luxuryhomestay.ui.main.adapter.CityAdapter;
import com.minhnv.luxuryhomestay.ui.main.adapter.HomeStaysAdapter;
import com.minhnv.luxuryhomestay.ui.main.adapter.HomeStaysPriceAscAdapter;
import com.minhnv.luxuryhomestay.ui.main.adapter.UserAdapter;
import com.minhnv.luxuryhomestay.ui.main.adapter.VinHomeAdapter;


import dagger.Module;
import dagger.Provides;

@Module
class HomeModule {


    @Provides
    static HomeStaysAdapter provideHomestayAdapter(){
        return new HomeStaysAdapter();
    }

    @Provides
    static CityAdapter provideCityAdapter(){
        return new CityAdapter();
    }

    @Provides
    static VinHomeAdapter provideVinHomeAdapter(){
        return new VinHomeAdapter();
    }

    @Provides
    static HomeStaysPriceAscAdapter provideHomeStaysPriceAscAdapter(){
        return new HomeStaysPriceAscAdapter();
    }

    @Provides
    static UserAdapter userAdapter(){
        return new UserAdapter();
    }
}
