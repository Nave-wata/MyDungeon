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

    @ColumnInfo(name = "Salt")
    public String salt;

    @ColumnInfo(name = "hash")
    public String hash;

    public UsersInfo(String name, String salt, String hash) {
        this.name = name;
        this.salt = salt;
        this.hash = hash;
    }

    public String getName() {
        return name;
    }
}