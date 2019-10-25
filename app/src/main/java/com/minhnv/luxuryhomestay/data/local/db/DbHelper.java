package com.minhnv.luxuryhomestay.data.local.db;

import com.minhnv.luxuryhomestay.data.model.User;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

public interface DbHelper {
    Observable<List<User>> getAll();

    Observable<Boolean> insertUser(User user);


}
