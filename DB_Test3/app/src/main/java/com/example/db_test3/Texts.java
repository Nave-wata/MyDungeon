package com.example.db_test3;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "TextData")
public class Texts {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "Text")
    public String text;
}
