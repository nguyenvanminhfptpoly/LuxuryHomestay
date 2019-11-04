package com.minhnv.luxuryhomestay.di.activity.main.booking;

import com.minhnv.luxuryhomestay.ui.main.adapter.BookingAdapter;

import dagger.Module;
import dagger.Provides;

@Module
public class BookingModule {

    @BookingScope
    @Provides
    static BookingAdapter provideBookingAdapter(){
        return new BookingAdapter();
    }
}
