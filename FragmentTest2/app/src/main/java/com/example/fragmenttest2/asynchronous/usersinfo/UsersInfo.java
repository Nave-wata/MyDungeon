package com.example.fragmenttest2.asynchronous.usersinfo;

import android.annotation.SuppressLint;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.SimpleDateFormat;
import java.util.Date;


@Entity(tableName = "UsersInfo")
public class UsersInfo {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "Name")
    public String name;

    @ColumnInfo(name = "Time")
    public String time;

    public UsersInfo(String name) {
        Date dateObj = new Date();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm ss");
        String time = format.format(dateObj);

        this.name = name;
        this.time = time;
    }

    public String getName() {
        return name;
    }
}