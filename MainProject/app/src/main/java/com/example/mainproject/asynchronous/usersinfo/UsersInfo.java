package com.example.mainproject.asynchronous.usersinfo;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;


@Entity(tableName = "UsersInfo", indices = {@Index(value = "Name", unique = true)})
public class UsersInfo {
    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "Name")
    public String name;

    @ColumnInfo(name = "Salt")
    public String salt;

    @ColumnInfo(name = "Hash")
    public String hash;

    @ColumnInfo(name = "Year")
    public int year;

    @ColumnInfo(name = "Month")
    public int month;

    @ColumnInfo(name = "Day")
    public int day;

    @ColumnInfo(name = "Hour")
    public int hour;

    @ColumnInfo(name = "Minute")
    public int minute;

    @ColumnInfo(name = "Second")
    public int second;

    public String getName() { return name; }

    public String getSalt() { return salt; }

    public String getHash() { return hash; }

    public int getYear() { return year; }

    public int getMonth() { return month; }

    public int getDay() { return day; }

    public int getHour() { return hour; }

    public int getMinute() { return minute; }

    public int getSecond() { return second; }
}