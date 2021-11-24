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
    @ColumnInfo(name = "unit_code")
    public String unit_code;
    @ColumnInfo(name = "customer_name")
    public String customer_name;
    @ColumnInfo (name = "customer_id")
    public  int customer_id;
    @ColumnInfo (name = "status")
    public  int status;
    @ColumnInfo (name = "total")
    public  double total;
    @ColumnInfo (name = "description")
    public Text description;

    public Order(int id, String unit_code, String customer_name, int customer_id, int status, double total, Text description) {
        this.id = id;
        this.unit_code = unit_code;
        this.customer_name = customer_name;
        this.customer_id = customer_id;
        this.status = status;
        this.total = total;
        this.description = description;
    }

    public Order(String unit_code, String customer_name, int customer_id, int status, double total, Text description) {
        this.unit_code = unit_code;
        this.customer_name = customer_name;
        this.customer_id = customer_id;
        this.status = status;
        this.total = total;
        this.description = description;
    }
}
