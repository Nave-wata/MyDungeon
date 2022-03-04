package com.example.mainproject.asynchronous.possession;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface PossessionInfoDao {
    @Insert
    void inset(PossessionInfo possessionInfo);

    @Query("UPDATE PossessionInfo" +
            "SET Name   = :name, " +
                "DP_A_F = :DP_A_F, " +
                "Money_A_F = :Money_A_F")
    void Update(String name, long DP_A_F, long Money_A_F);
}
