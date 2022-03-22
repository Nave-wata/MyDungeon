package com.example.mainproject.asynchronous.dungeonlayout;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;


@Entity(tableName = "DungeonLayout", indices = {@Index(value = "Name", unique = true)})
public class DungeonLayout {
    @PrimaryKey(autoGenerate = true)
    public Integer id;

    @ColumnInfo(name = "Name")
    public String name;

    @ColumnInfo(name = "Row0")
    public String row0;

    @ColumnInfo(name = "Row1")
    public String row1;

    @ColumnInfo(name = "Row2")
    public String row2;

    @ColumnInfo(name = "Row3")
    public String row3;

    @ColumnInfo(name = "Row4")
    public String row4;

    @ColumnInfo(name = "Row5")
    public String row5;

    @ColumnInfo(name = "Row6")
    public String row6;

    @ColumnInfo(name = "Row7")
    public String row7;

    @ColumnInfo(name = "Row8")
    public String row8;

    @ColumnInfo(name = "Row9")
    public String row9;

    @ColumnInfo(name = "Row10")
    public String row10;

    @ColumnInfo(name = "Row11")
    public String row11;

    @ColumnInfo(name = "Row12")
    public String row12;

    @ColumnInfo(name = "Row13")
    public String row13;

    @ColumnInfo(name = "Row14")
    public String row14;

    @ColumnInfo(name = "Row15")
    public String row15;

    @ColumnInfo(name = "Row16")
    public String row16;

    @ColumnInfo(name = "Row17")
    public String row17;

    @ColumnInfo(name = "Row18")
    public String row18;

    @ColumnInfo(name = "Row19")
    public String row19;

    public DungeonLayout(String name,
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
                         String row19) {
        this.name = name;
        this.row0 = row0;
        this.row1 = row1;
        this.row2 = row2;
        this.row3 = row3;
        this.row4 = row4;
        this.row5 = row5;
        this.row6 = row6;
        this.row7 = row7;
        this.row8 = row8;
        this.row9 = row9;
        this.row10 = row10;
        this.row11 = row11;
        this.row12 = row12;
        this.row13 = row13;
        this.row14 = row14;
        this.row15 = row15;
        this.row16 = row16;
        this.row17 = row17;
        this.row18 = row18;
        this.row19 = row19;
    }

    public String getRow0() { return row0; }

    public String getRow1() { return row1; }

    public String getRow2() { return row2; }

    public String getRow3() { return row3; }

    public String getRow4() { return row4; }

    public String getRow5() { return row5; }

    public String getRow6() { return row6; }

    public String getRow7() { return row7; }

    public String getRow8() { return row8; }

    public String getRow9() { return row9; }

    public String getRow10() { return row10; }

    public String getRow11() { return row11; }

    public String getRow12() { return row12; }

    public String getRow13() { return row13; }

    public String getRow14() { return row14; }

    public String getRow15() { return row15; }

    public String getRow16() { return row16; }

    public String getRow17() { return row17; }

    public String getRow18() { return row18; }

    public String getRow19() { return row19; }
}
