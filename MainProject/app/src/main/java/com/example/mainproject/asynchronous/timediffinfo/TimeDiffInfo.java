package com.example.mainproject.asynchronous.timediffinfo;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "TimeDiffInfo", indices = {@Index(value = "Name", unique = true)})
public class TimeDiffInfo {
    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "Name")
    public String name;

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

    public TimeDiffInfo(String name,
                        int year,
                        int month,
                        int day,
                        int hour,
                        int minute,
                        int second) {
        this.name = name;
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }

    public String getName() { return name; }

    public int getYear() { return year; }

    public int getMonth() { return month; }

    public int getDay() { return day; }

    public int getHour() { return hour; }

    public int getMinute() { return minute; }

    public int getSecond() { return second; }
}