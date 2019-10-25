package com.minhnv.luxuryhomestay.data.local.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.minhnv.luxuryhomestay.data.model.User;

import java.util.List;

import io.reactivex.Observable;


@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User user);

    @Insert
    void insertAll(List<User> users);

    @Query("SELECT * FROM user")
    Observable<List<User>> getAll();
}
