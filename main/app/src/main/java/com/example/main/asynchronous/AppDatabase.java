package com.example.main.asynchronous;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.main.asynchronous.usersinfo.UsersInfo;
import com.example.main.asynchronous.usersinfo.UsersInfoDao;


@Database(entities = {UsersInfo.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UsersInfoDao usersInfoDao();
}
