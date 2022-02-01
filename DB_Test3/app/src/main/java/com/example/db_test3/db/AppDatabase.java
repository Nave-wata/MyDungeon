package com.example.db_test3.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.db_test3.db.tests.Texts;
import com.example.db_test3.db.tests.TextsDao;

@Database(entities = {Texts.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TextsDao textsDao();
}
