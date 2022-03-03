package com.example.main.asynchronous;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.main.asynchronous.possessioninfo.PossessionInfo;
import com.example.main.asynchronous.possessioninfo.PossessionInfoDao;
import com.example.main.asynchronous.usersinfo.UsersInfo;
import com.example.main.asynchronous.usersinfo.UsersInfoDao;


@Database(entities = {
        PossessionInfo.class},
        version = 2)
public abstract class Test extends RoomDatabase {
    public abstract PossessionInfoDao possessionInfoDao();
}
