package com.example.fragmenttest2.asynchronous;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.fragmenttest2.asynchronous.usersinfo.UsersInfo;
import com.example.fragmenttest2.asynchronous.usersinfo.UsersInfoDao;


@Database(entities = {UsersInfo.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UsersInfoDao usersInfoDao();
}
