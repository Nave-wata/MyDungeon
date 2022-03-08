package com.example.mainproject.asynchronous;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.mainproject.asynchronous.usersapptimes.UsersAppTimes;
import com.example.mainproject.asynchronous.usersapptimes.UsersAppTimesDao;
import com.example.mainproject.asynchronous.usersinfo.UsersInfo;
import com.example.mainproject.asynchronous.usersinfo.UsersInfoDao;
import com.example.mainproject.asynchronous.userspossession.UsersPossession;
import com.example.mainproject.asynchronous.userspossession.UsersPossessionDao;


@Database(version = 2, exportSchema = false,
        entities = {UsersInfo.class,
                    UsersAppTimes.class,
                    UsersPossession.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract UsersInfoDao usersInfoDao();
    public abstract UsersAppTimesDao usersAppTimesDao();
    public abstract UsersPossessionDao usersPossessionDao();
}
