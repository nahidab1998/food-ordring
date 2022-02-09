package com.example.food_orderig.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.example.food_orderig.model.Order;
import java.util.List;

@Dao
public interface SavedOrderDao {
    @Query("Select * from table_order")
    List<Order> getOrderList();

    @Query("select * from table_order where date >=:date")
    List<Order> getOrderListDate(String date);

    @Query("Select total from table_order ")
    List<String> alldate();

    @Query("Select total from table_order where date = :datee ")
    List<String> dailyTotal( String datee);

    @Query("Select * from table_order where customer_id = :id limit 1 " )
    Order getid( int id);

    @Query("DELETE from table_order where customer_id = :id " )
    void deteteID(int id);

    @Query("Select total from table_order")
    List<String> getAllTotal();

    @Insert
    void insertOrder(Order order);

    @Update
    void updateOrder(Order order );

    @Delete
    void deleteOrder(Order order);

}
