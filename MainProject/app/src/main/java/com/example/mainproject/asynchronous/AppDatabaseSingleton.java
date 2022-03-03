package com.example.mainproject.asynchronous;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;

import com.example.mainproject.asynchronous.possessioninfo.PossessionInfo;
import com.example.mainproject.asynchronous.usersinfo.UsersInfo;


public class AppDatabaseSingleton {
    private static AppDatabase instance = null;

    public static AppDatabase getInstance(Context context) {
        if (instance != null) {
            return instance;
        }

        instance = Room.databaseBuilder(context, AppDatabase.class, "main.db").build();
        return instance;
    }
}