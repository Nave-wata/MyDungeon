package com.example.main.asynchronous;

import androidx.room.AutoMigration;
import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.main.asynchronous.possessioninfo.PossessionInfo;
import com.example.main.asynchronous.possessioninfo.PossessionInfoDao;
import com.example.main.asynchronous.usersinfo.UsersInfo;
import com.example.main.asynchronous.usersinfo.UsersInfoDao;


/**/

@Database(version = 2,
        entities = {
            UsersInfo.class,
            PossessionInfo.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract UsersInfoDao usersInfoDao();
    public abstract PossessionInfoDao possessionInfoDao();
}
