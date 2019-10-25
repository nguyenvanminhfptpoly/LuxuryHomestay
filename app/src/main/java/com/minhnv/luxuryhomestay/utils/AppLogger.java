package com.minhnv.luxuryhomestay.utils;

import com.minhnv.luxuryhomestay.BuildConfig;

import timber.log.Timber;

public class AppLogger {
    public static void init() {
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }
    public static void d(String s, Object... objects) {
        Timber.d(s, objects);
    }
}
