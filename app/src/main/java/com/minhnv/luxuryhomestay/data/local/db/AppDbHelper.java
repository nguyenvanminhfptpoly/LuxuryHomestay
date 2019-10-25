package com.minhnv.luxuryhomestay.data.local.db;

import com.minhnv.luxuryhomestay.data.model.User;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

@Singleton
public class AppDbHelper implements DbHelper {

    private final AppDatabase database;

    @Inject
    AppDbHelper(AppDatabase database) {
        this.database = database;
    }

    @Override
    public Observable<List<User>> getAll() {
        return database.userDao().getAll();
    }

    @Override
    public Observable<Boolean> insertUser(User user) {
        return Observable.fromCallable(() -> {
            database.userDao().insert(user);
            return true;
        });
    }
}
