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

    @Query("select * from table_order where date>=datetime('now', 'now', '-7 day')")
    List<Order> getOrderListDate();

    @Insert
    void insertOrder(Order order);

    @Update
    void updateOrder(Order order );

    @Delete
    void deleteOrder(Order order);

}
