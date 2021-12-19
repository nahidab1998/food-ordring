package com.example.food_orderig.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.food_orderig.model.Grouping;
import com.example.food_orderig.model.Product;

import java.util.List;

@Dao
public interface ProductDao {
    @Query("SELECT * FROM table_product")
    List<Product> getList();

    @Query("SELECT * FROM table_product WHERE category = :category")
    List<Product> getListByCategory(String category);

//    @Query("Select * from table_product where table_product_id = :id limit 1")
//    List<Product> get(int id);
    @Query("Select * from table_product where name = :name limit 1")
    Product getOneName(String name);

    @Insert
    void insertProduct(Product product);

    @Update
    void updateProduct(Product product);

    @Delete
    void deleteProduct(Product product);
}
