package com.example.food_orderig.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.example.food_orderig.database.dao.CustomerDao;
import com.example.food_orderig.database.dao.DetailOrderDao;
import com.example.food_orderig.database.dao.GroupingDao;
import com.example.food_orderig.database.dao.ProductDao;
import com.example.food_orderig.database.dao.SavedOrderDao;
import com.example.food_orderig.model.Customer;
import com.example.food_orderig.model.DetailOrder;
import com.example.food_orderig.model.Grouping;
import com.example.food_orderig.model.Order;
import com.example.food_orderig.model.Product;

@Database( entities = {Product.class , Grouping.class , Customer.class , DetailOrder.class , Order.class } , exportSchema = false , version = 1)
public abstract class DatabaseHelper extends RoomDatabase{
    private static final String DB_NAME = "db_name";
    private static DatabaseHelper instance;

    public static synchronized DatabaseHelper getInstance(Context context){

        if (instance==null){
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    DatabaseHelper.class,
                    DB_NAME)
                    .allowMainThreadQueries()
                    .build();

        }

        return instance;
    }

    public abstract ProductDao productDao();
    public abstract GroupingDao groupingDao();
    public abstract CustomerDao customerDao();
    public abstract DetailOrderDao detailOrderDao();
    public abstract SavedOrderDao savedOrderDao();
}