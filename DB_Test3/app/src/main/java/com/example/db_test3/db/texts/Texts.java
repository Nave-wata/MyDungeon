package com.example.db_test3.db.texts;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "TextData")
public class Texts {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "Text")
    public String text;

    public Texts(String text) {
        this.text = text;
    }

    public Texts setId(int id) {
        this.id = id;
        return this;
    }

    public Texts setText(String text) {
        this.text = text;
        return this;
    }

    public String getText() {
        return text;
    }
}
