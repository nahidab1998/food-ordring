package com.example.food_orderig.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import org.w3c.dom.Text;


@Entity(tableName = "table_order")
public class Order {
    @PrimaryKey(autoGenerate=true)
    public int id;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "unit_code")
    public String unit_code;

    @ColumnInfo (name = "customer_id")
    public  int customer_id;

    @ColumnInfo (name = "status")
    public  int status;

    @ColumnInfo (name = "total")
    public  String total;

    @ColumnInfo (name = "description")
    public String description;

    @ColumnInfo(name = "time")
    public String time;

    @ColumnInfo(name = "date")
    public String date;


    public Order(int id, String name, String unit_code, int customer_id, int status, String total, String description , String time , String date ) {
        this.id = id;
        this.name = name;
        this.unit_code = unit_code;
        this.customer_id = customer_id;
        this.status = status;
        this.total = total;
        this.description = description;
        this.time = time;
        this.date = date;
    }

    @Ignore
    public Order(String name, String unit_code, int customer_id, int status, String total, String description , String time , String date) {
        this.name = name;
        this.unit_code = unit_code;
        this.customer_id = customer_id;
        this.status = status;
        this.total = total;
        this.description = description;
        this.time = time;
        this.date = date;
    }
}
