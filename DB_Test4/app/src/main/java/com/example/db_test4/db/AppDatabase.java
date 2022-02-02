package com.example.db_test4.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.db_test4.db.usersinfo.UsersInfo;
import com.example.db_test4.db.usersinfo.UsersInfoDao;

@Database(entities = {UsersInfo.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UsersInfoDao usersInfoDao();
}