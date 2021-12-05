package com.example.food_orderig.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.example.food_orderig.model.Customer;
import java.util.List;

@Dao
public interface CustomerDao {

    @Query("SELECT * FROM table_customer")
    List <Customer> getList();

    @Query("Select * from table_customer where id = :id limit 1")
    Customer getID(int id);

    @Insert
    void insertCustomer(Customer customer);

    @Update
    void updateCustomer(Customer customer);

    @Delete
    void deleteCustomer(Customer customer);
}
