package com.example.mainproject.asynchronous.usersinfo;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;


@Dao
public interface UsersInfoDao {
    @Query("INSERT INTO UsersInfo(Name, Salt, Hash, Year, Month, Day, Hour, Minute, Second) VALUES(:name, :salt, :hash, 0, 0, 0, 0, 0, 0)")
    void insertNames(String name, String salt, String hash);

    @Query("DELETE FROM UsersInfo WHERE Name = :name")
    void deleteUserInfo(String name);

    @Query("DELETE FROM UsersInfo")
    void deleteAll();

    @Query("SELECT * FROM UsersInfo WHERE Name = :name")
    List<UsersInfo> getLine(String name);

    @Query("SELECT * FROM UsersInfo")
    List<UsersInfo> getAll();
}