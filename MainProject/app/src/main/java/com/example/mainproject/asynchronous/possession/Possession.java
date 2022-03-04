package com.example.mainproject.asynchronous.possession;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity(tableName = "PossessionInfo", indices = {@Index(value = "Name", unique = true)})
public class Possession {
    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "Name")
    public String name;

    @ColumnInfo(name = "A_E")
    public long A_E;

    @ColumnInfo(name = "F_J")
    public long F_J;

    @ColumnInfo(name = "K_O")
    public long K_O;

    @ColumnInfo(name = "P_T")
    public long P_T;

    @ColumnInfo(name = "U_Y")
    public long U_Y;

    @ColumnInfo(name = "Z_AD")
    public long Z_AD;

    public Possession(String name,
                      ArrayList<Byte> DP)
    {
        for (int i = 0; i < DP.size(); i++) {

        }

        this.A_E = A_E;
        this.F_J = F_J;
        this.K_O = K_O;
        this.P_T = P_T;
        this.U_Y = U_Y;
        this.Z_AD = Z_AD;
    }
}
