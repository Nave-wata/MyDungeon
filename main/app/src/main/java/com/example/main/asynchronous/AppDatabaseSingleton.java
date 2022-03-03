package com.example.main.asynchronous;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;

import com.example.main.asynchronous.possessioninfo.PossessionInfo;
import com.example.main.asynchronous.usersinfo.UsersInfo;


public class AppDatabaseSingleton {
    private static AppDatabase instance = null;

    public static AppDatabase getInstance(Context context, String DB_Name) {
        if (instance != null) {
            return instance;
        }

        if (DB_Name == UsersInfo.NAME) {
            instance = Room.databaseBuilder(context, AppDatabase.class, "UsersInfo.db").fallbackToDestructiveMigration().build();
            return instance;
        }
        if (DB_Name == PossessionInfo.NAME) {
            instance = Room.databaseBuilder(context, AppDatabase.class, "PossessionInfo.db").fallbackToDestructiveMigration().build();
            return instance;
        }

        return null;
    }
}
