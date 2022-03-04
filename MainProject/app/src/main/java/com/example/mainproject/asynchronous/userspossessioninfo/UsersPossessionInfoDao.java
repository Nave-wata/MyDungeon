package com.example.mainproject.asynchronous.userspossessioninfo;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UsersPossessionInfoDao {
    @Insert
    void insetTask(UsersPossessionInfo usersPossessionInfo);

    @Query("UPDATE UsersPossessionInfo " +
            "SET Name   = :name, " +
                "DP_A_F = :DP_A_F, " +
                "Money_A_F = :Money_A_F " +
            "WHERE Name = :name")
    void updateTask(String name, long DP_A_F, long Money_A_F);

    @Query("SELECT * FROM UsersPossessionInfo WHERE Name = :name")
    List<UsersPossessionInfo> getLineTask(String name);
}
