package com.example.food_orderig.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;


@Entity(tableName = "table_User")
public class NewUser {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "username")
    public String username;

    @ColumnInfo(name = "password")
    public String password;

    public NewUser (int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    @Ignore
    public NewUser(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
