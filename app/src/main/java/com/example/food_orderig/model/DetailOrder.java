package com.example.food_orderig.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import org.w3c.dom.Text;

@Entity(tableName = "detailorder")
public class DetailOrder {

    @PrimaryKey(autoGenerate=true)
    public int id;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "price")
    public String price;

    @ColumnInfo (name = "category")
    public String  category;

    @ColumnInfo(name = "amant")
    public int amant;

    @ColumnInfo(name = "code")
    public String code;

    @ColumnInfo (name = "time")
    public  String time;

    @ColumnInfo (name = "date")
    public  String date;



    public DetailOrder(int id, String name, String price, String category, int amant, String code , String time, String date ) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
        this.amant = amant;
        this.code = code;
        this.time = time;
        this.date = date;
    }

    @Ignore
    public DetailOrder(String name, String price, String category, int amant, String code , String time, String date ) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.amant = amant;
        this.code = code;
        this.time = time ;
        this.date = date ;
    }
}
