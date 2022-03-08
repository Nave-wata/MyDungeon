package com.example.mainproject.asynchronous.userspossession;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UsersPossessionDao {
    @Insert
    void signUpTask(UsersPossession usersPossession);

    @Query("UPDATE UsersPossession " +
            "SET DP = :dp, " +
            "    Money = :money " +
            "WHERE Name = :name")
    void updatePossessionTask(String name, String dp, String money);

    @Query("SELECT * FROM UsersPossession WHERE Name = :name")
    List<UsersPossession> getPossession(String name);
}
