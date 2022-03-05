package com.example.mainproject.asynchronous;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.mainproject.asynchronous.userspossessioninfo.UsersPossessionInfo;
import com.example.mainproject.asynchronous.userspossessioninfo.UsersPossessionInfoDao;
import com.example.mainproject.asynchronous.usersinfo.UsersInfo;
import com.example.mainproject.asynchronous.usersinfo.UsersInfoDao;


/**/

@Database(version = 2, exportSchema = false,
        entities = {
                UsersInfo.class,
                UsersPossessionInfo.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract UsersInfoDao usersInfoDao();
    public abstract UsersPossessionInfoDao possessionInfoDao();
}
