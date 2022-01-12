package com.example.food_orderig.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.food_orderig.model.Customer;
import com.example.food_orderig.model.Grouping;
import com.example.food_orderig.model.NewUser;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM table_User")
    List<NewUser> getList();

    @Query("Select * from table_User where username = :name And password = :password limit 1")
    Grouping getOneName(String name , String password);

    @Insert
    void insertNewUser(NewUser newUser);

    @Update
    void updateNewUser(NewUser newUser);

    @Delete
    void deleteNewUser(NewUser newUser);
}
