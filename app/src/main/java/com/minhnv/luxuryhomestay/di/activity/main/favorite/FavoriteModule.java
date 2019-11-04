package com.minhnv.luxuryhomestay.di.activity.main.favorite;

import com.minhnv.luxuryhomestay.ui.main.adapter.FavoriteAdapter;

import dagger.Module;
import dagger.Provides;

@Module
public class FavoriteModule {

    @FavoriteScope
    @Provides
    static FavoriteAdapter favoriteAdapter(){
        return new FavoriteAdapter();
    }
}
