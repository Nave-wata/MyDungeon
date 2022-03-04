package com.example.mainproject.asynchronous.userspossessioninfo;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "UsersPossessionInfo", indices = {@Index(value = "Name", unique = true)})
public class UsersPossessionInfo {
    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "Name")
    public String name;

    @ColumnInfo(name = "DP_A_F")
    public long DP_A_F;

    @ColumnInfo(name = "Money_A_F")
    public long Money_A_F;

    public UsersPossessionInfo(String name, long DP_A_F, long Money_A_F) {
        this.name = name;
        this.DP_A_F = DP_A_F;
        this.Money_A_F = Money_A_F;
    }

    public long getDP() { return this.DP_A_F; }

    public long getMoney() { return this.Money_A_F; }
}
