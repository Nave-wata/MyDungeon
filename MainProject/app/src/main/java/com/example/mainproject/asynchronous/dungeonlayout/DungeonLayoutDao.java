package com.example.mainproject.asynchronous.dungeonlayout;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DungeonLayoutDao {
    @Insert
    void signUpTask(DungeonLayout dungeonLayout);

    @Query("UPDATE DungeonLayout " +
            "SET Row0 = :row0, " +
            "    Row1 = :row1, " +
            "    Row2 = :row2, " +
            "    Row3 = :row3, " +
            "    Row4 = :row4, " +
            "    Row5 = :row5, " +
            "    Row6 = :row6, " +
            "    Row7 = :row7, " +
            "    Row8 = :row8, " +
            "    Row9 = :row9, " +
            "    Row10 = :row10, " +
            "    Row11 = :row11, " +
            "    Row12 = :row12, " +
            "    Row13 = :row13, " +
            "    Row14 = :row14, " +
            "    Row15 = :row15, " +
            "    Row16 = :row16, " +
            "    Row17 = :row17, " +
            "    Row18 = :row18, " +
            "    Row19 = :row19 " +
            "WHERE Name = :name")
    void updateDungeonLayout(String name,
                             String row0,
                             String row1,
                             String row2,
                             String row3,
                             String row4,
                             String row5,
                             String row6,
                             String row7,
                             String row8,
                             String row9,
                             String row10,
                             String row11,
                             String row12,
                             String row13,
                             String row14,
                             String row15,
                             String row16,
                             String row17,
                             String row18,
                             String row19);

    @Query("SELECT * FROM DungeonLayout WHERE Name = :name")
    List<DungeonLayout> getDungeonLayout(String name);
}
