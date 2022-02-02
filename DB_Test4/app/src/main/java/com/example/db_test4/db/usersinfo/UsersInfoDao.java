package com.example.db_test4.db.usersinfo;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UsersInfoDao {
    @Insert
    void insert(UsersInfo usersInfo);
    
    @Delete
    void delete(UsersInfo usersInfo);

    @Query("DELETE FROM UsersInfo WHERE Name = :name")
    void deleteUserInfo(String name);

    @Query("DELETE FROM UsersInfo")
    void deleteAll();

    @Query("SELECT * FROM UsersInfo")
    List<UsersInfo> getAll();
}