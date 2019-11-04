package com.minhnv.luxuryhomestay.di.activity.main.vinhomes;

import com.minhnv.luxuryhomestay.ui.main.adapter.VinHomeDetailAdapter;

import dagger.Module;
import dagger.Provides;

@Module
public class VinHomeModule {

    @VinHomeScope
    @Provides
    static VinHomeDetailAdapter vinHomeDetailAdapter(){
        return new VinHomeDetailAdapter();
    }
}
