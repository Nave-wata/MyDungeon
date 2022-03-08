package com.example.mainproject.asynchronous.userspossession;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.example.mainproject.BaseStatusFragment;

@Entity(tableName = "UsersPossession", indices = {@Index(value = "Name", unique = true)})
public class UsersPossession {
    @PrimaryKey(autoGenerate = true)
    public Integer id;

    @ColumnInfo(name = "Name")
    public String name;

    @ColumnInfo(name = "DP")
    public String dp;

    @ColumnInfo(name = "Money")
    public String money;

    public UsersPossession(String name,
                           String dp,
                           String money) {
        this.name = name;
        this.dp = dp;
        this.money = money;
    }

    public byte[] getDP() { return new BaseStatusFragment().CastByte(dp); }

    public byte[] getMoney() { return new BaseStatusFragment().CastByte(money); }
}
