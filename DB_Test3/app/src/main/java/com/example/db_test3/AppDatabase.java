package com.example.db_test3;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Texts.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TextsDao textsDao();
}
