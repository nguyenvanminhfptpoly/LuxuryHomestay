package com.minhnv.luxuryhomestay.di.activity.main.detail;

import com.minhnv.luxuryhomestay.ui.main.adapter.CommentAdapter;
import com.minhnv.luxuryhomestay.ui.main.adapter.ImageDetailAdapter;

import dagger.Module;
import dagger.Provides;

@Module
public class DetailModule {

    @DetailScope
    @Provides
    static ImageDetailAdapter imageDetailAdapter(){
        return  new ImageDetailAdapter();
    }

    @DetailScope
    @Provides
    static CommentAdapter commentAdapter(){
        return new CommentAdapter();
    }
}
