package com.example.food_orderig.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;


@Entity(tableName = "table_product")
public class Product {
    @PrimaryKey(autoGenerate=true)
    public int id;
    @ColumnInfo(name = "name")
    public String name;
    @ColumnInfo(name = "category")
    public String category;

    public Product(int id, String name, String category) {
        this.id = id;
        this.name = name;
        this.category = category;
    }

    @Ignore
    public Product(String name, String category) {
        this.name = name;
        this.category = category;
    }
}
