package com.example.mainproject.asynchronous;

import androidx.room.AutoMigration;
import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.mainproject.asynchronous.possessioninfo.PossessionInfo;
import com.example.mainproject.asynchronous.possessioninfo.PossessionInfoDao;
import com.example.mainproject.asynchronous.usersinfo.UsersInfo;
import com.example.mainproject.asynchronous.usersinfo.UsersInfoDao;


/**/

@Database(version = 2, exportSchema = false,
        entities = {
                UsersInfo.class,
                PossessionInfo.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract UsersInfoDao usersInfoDao();
    public abstract PossessionInfoDao possessionInfoDao();
}
