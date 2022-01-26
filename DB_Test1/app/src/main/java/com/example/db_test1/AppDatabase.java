package com.example.db_test1;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {AccessTime.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract AccessTimeDao accessTimeDao();
}