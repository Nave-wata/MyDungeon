package com.example.main.asynchronous.usersinfo;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;


@Entity(tableName = "UsersInfo", indices = {@Index(value = "Name", unique = true)})
public class UsersInfo {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "Name")
    public String name;

    @ColumnInfo(name = "Salt")
    public String salt;

    @ColumnInfo(name = "hash")
    public String hash;

    public UsersInfo(String name,
                     String salt,
                     String hash) {
        this.name = name;
        this.salt = salt;
        this.hash = hash;
    }

    public String getName() { return name; }

    public String getSalt() { return salt; }

    public String getHash() { return hash; }
}
