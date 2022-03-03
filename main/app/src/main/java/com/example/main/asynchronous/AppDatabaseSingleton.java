package com.example.main.asynchronous;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;


public class AppDatabaseSingleton {
    private static AppDatabase instance = null;

    public static AppDatabase getInstance(Context context) {
        if (instance != null) {
            return instance;
        }

        instance = Room.databaseBuilder(context, AppDatabase.class, "app-database").fallbackToDestructiveMigrationOnDowngrade().build();
        return instance;
    }
}
