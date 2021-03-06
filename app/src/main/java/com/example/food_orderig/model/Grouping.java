package com.example.food_orderig.model;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "table_grouping")
public class Grouping {

    @PrimaryKey(autoGenerate=true)
    public int id;
    @ColumnInfo(name = "name")
    public String name;
    @ColumnInfo(name = "picture")
    public String picture;

    public Grouping(int id, String name ,String picture) {
        this.id = id;
        this.name = name;
        this.picture = picture;
    }

    @Ignore
    public Grouping(String name , String picture) {
        this.name = name;
        this.picture = picture;

    }
}
