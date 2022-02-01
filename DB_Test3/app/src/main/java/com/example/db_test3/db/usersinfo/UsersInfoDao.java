package com.example.db_test3.db.usersinfo;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface UsersInfoDao {
    @Insert
    void insert(UsersInfo name, UsersInfo year, UsersInfo month, UsersInfo day);

    @Query("DELETE FROM UsersInfo WHERE Name = :name")
    void deleteUserInfo(String name);
}
