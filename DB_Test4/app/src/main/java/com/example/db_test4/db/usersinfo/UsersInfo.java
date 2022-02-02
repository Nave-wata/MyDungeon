package com.example.db_test4.db.usersinfo;

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

    public UsersInfo(String name, int year, int month, int day) {
        this.name = name;
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public String getName() {
        return name;
    }

    public int getYear() {
        return  year;
    }

    public int getMonth() {
        return  month;
    }

    public int getDay() {
        return day;
    }
}
