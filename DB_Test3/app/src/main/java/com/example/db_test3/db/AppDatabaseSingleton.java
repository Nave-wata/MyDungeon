package com.example.db_test3.db;

import android.content.Context;
import androidx.room.Room;

import com.example.db_test3.db.AppDatabase;

public class AppDatabaseSingleton {
    private static AppDatabase instance = null;

    public static AppDatabase getInstance(Context context) {
        if (instance != null) {
            return instance;
        }

        instance = Room.databaseBuilder(context, AppDatabase.class, "app-database").build();
        return instance;
    }
}
