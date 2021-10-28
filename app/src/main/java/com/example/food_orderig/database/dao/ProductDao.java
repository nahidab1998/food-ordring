package com.example.food_orderig.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.food_orderig.model.Product;

import java.util.List;

@Dao
public interface ProductDao {
    @Query("SELECT * FROM table_product")
    List<Product> getList();

    @Insert
    void insertPerson(Product person);

    @Update
    void updatePerson(Product person);

    @Delete
    void deletePerson(Product person);
}