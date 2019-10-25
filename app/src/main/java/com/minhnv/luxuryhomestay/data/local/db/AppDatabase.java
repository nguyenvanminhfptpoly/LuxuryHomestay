package com.minhnv.luxuryhomestay.data.local.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.minhnv.luxuryhomestay.data.local.db.dao.UserDao;
import com.minhnv.luxuryhomestay.data.model.User;

@Database(entities = {User.class},version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
}
