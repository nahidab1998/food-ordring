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

    @ColumnInfo (name = "price")
    public  String price;

    @Ignore
    public int amount = 1;

    public Product(String name, String category) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
    }

    @Ignore
    public Product(String name, String category , String price) {
        this.name = name;
        this.category = category;
        this.price = price;
    }
}
