package com.example.db_test3;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "TextData")
public class Texts {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "Text")
    public String text;

    public Texts (String text) {
        this.text = text;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }
}
