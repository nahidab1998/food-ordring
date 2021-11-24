package com.example.food_orderig.model;

import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

import org.w3c.dom.Text;

public class DetailOrder {

    @PrimaryKey(autoGenerate=true)
    public int id;
    @ColumnInfo(name = "name")
    public String name;
    @ColumnInfo(name = "price")
    public double price;
    @ColumnInfo (name = "category")
    public String  category;
    @ColumnInfo(name = "amant")
    public int amant;
    @ColumnInfo(name = "code")
    public String code;

    public DetailOrder(int id, String name, double price, String category, int amant, String code) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
        this.amant = amant;
        this.code = code;
    }

    public DetailOrder(String name, double price, String category, int amant, String code) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.amant = amant;
        this.code = code;
    }
}
