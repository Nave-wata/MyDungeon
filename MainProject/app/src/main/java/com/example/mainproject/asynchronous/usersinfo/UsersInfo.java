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

    public UsersInfo setPass(
            String name,
            String salt,
            String hash)
    {
        UsersInfo usersInfo = new UsersInfo();
        this.name = name;
        this.salt = salt;
        this.hash = hash;

        return usersInfo;
    }

    public void setTime(
            String name,
            int year,
            int month,
            int day,
            int hour,
            int minute,
            int second)
    {
        this.name = name;
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }

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