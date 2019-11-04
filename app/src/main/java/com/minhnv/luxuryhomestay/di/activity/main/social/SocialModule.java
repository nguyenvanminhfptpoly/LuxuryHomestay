package com.minhnv.luxuryhomestay.di.activity.main.social;


import com.minhnv.luxuryhomestay.ui.main.adapter.LuxuryAdapter;
import com.minhnv.luxuryhomestay.ui.main.adapter.SocialAdapter;

import dagger.Module;
import dagger.Provides;

@Module
public class SocialModule {

    @SocialScope
    @Provides
    static LuxuryAdapter luxuryAdapter(){
        return new LuxuryAdapter();
    }

    @SocialScope
    @Provides
    static SocialAdapter socialAdapter(){
        return new SocialAdapter();
    }
}
