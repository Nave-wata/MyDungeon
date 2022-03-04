package com.example.mainproject.asynchronous.possession;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PossessionInfoDao {
    @Insert
    void insetTask(PossessionInfo possessionInfo);

    @Query("UPDATE PossessionInfo " +
            "SET Name   = :name, " +
                "DP_A_F = :DP_A_F, " +
                "Money_A_F = :Money_A_F " +
            "WHERE Name = :name")
    void updateTask(String name, long DP_A_F, long Money_A_F);

    @Query("SELECT * FROM PossessionInfo WHERE Name = :name")
    List<PossessionInfo> GetLineTask(String name);
}
