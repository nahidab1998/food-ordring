package com.example.food_orderig.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.example.food_orderig.model.Grouping;

import java.util.ArrayList;
import java.util.List;


@Dao
public interface GroupingDao {

    @Query("SELECT * FROM table_grouping")
    List<Grouping> getList();

    @Query("SELECT name FROM table_grouping")
    List<String> getname();

    @Insert
    void insertGrouping(Grouping grouping);

    @Update
    void updateGrouping(Grouping grouping);

    @Delete
    void deleteGrouping(Grouping grouping);
}
