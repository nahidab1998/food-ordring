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

//    @Query("Select * from table_product where table_product_id = :id limit 1")
//    List<Product> get(int id);

    @Insert
    void insertProduct(Product product);

    @Update
    void updateProduct(Product product);

    @Delete
    void deleteProduct(Product product);
}
