package com.example.main.asynchronous.possessioninfo;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.main.asynchronous.usersinfo.UsersInfo;

import java.util.List;

@Dao
public interface PossessionInfoDao {
    @Insert
    void insert(PossessionInfo possessionInfo);

    @Query("DELETE FROM PossessionInfo WHERE Name = :name")
    void deletePossessionInfo(String name);

    @Query("DELETE FROM PossessionInfo")
    void deleteAll();

    @Query("SELECT * FROM PossessionInfo WHERE Name = :name")
    List<UsersInfo> getLine(String name);

    @Query("SELECT * FROM PossessionInfo")
    List<UsersInfo> getAll();
}
