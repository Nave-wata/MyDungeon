package com.example.db_test3.db.usersinfo;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "UsersInfo")
public class UsersInfo {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "Name")
    public String name;

    @ColumnInfo(name = "Year")
    public int year;

    @ColumnInfo(name = "Month")
    public int month;

    @ColumnInfo(name = "Day")
    public int day;
}

