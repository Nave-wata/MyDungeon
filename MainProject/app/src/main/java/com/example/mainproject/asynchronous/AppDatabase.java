package com.example.mainproject.asynchronous;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.mainproject.asynchronous.dungeonlayout.DungeonLayout;
import com.example.mainproject.asynchronous.dungeonlayout.DungeonLayoutDao;
import com.example.mainproject.asynchronous.usersapptimes.UsersAppTimes;
import com.example.mainproject.asynchronous.usersapptimes.UsersAppTimesDao;
import com.example.mainproject.asynchronous.usersinfo.UsersInfo;
import com.example.mainproject.asynchronous.usersinfo.UsersInfoDao;
import com.example.mainproject.asynchronous.userspossession.UsersPossession;
import com.example.mainproject.asynchronous.userspossession.UsersPossessionDao;


@Database(
        version = 1,
        exportSchema = false,
        entities = {
                UsersInfo.class,
                UsersAppTimes.class,
                UsersPossession.class,
                DungeonLayout.class
        })
public abstract class AppDatabase extends RoomDatabase {
    public abstract UsersInfoDao usersInfoDao();
    public abstract UsersAppTimesDao usersAppTimesDao();
    public abstract UsersPossessionDao usersPossessionDao();
    public abstract DungeonLayoutDao dungeonLayoutDao();
}
