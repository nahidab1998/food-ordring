package com.example.food_orderig.activity.ordering;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.food_orderig.R;
import com.example.food_orderig.database.DatabaseHelper;
import com.example.food_orderig.database.dao.ProductDao;
import com.example.food_orderig.helper.AdapterOrdering;

public class ActivityOrdering extends AppCompatActivity {

    RecyclerView recyclerView_pruduct;
    AdapterOrdering adapterOrdering;
    DatabaseHelper db;
    ProductDao dao_product;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordering);

        db = DatabaseHelper.getInstance(getApplicationContext());
        dao_product = db.productDao();

        set_recycler_ordering();
    }

    public void set_recycler_ordering(){
        recyclerView_pruduct = findViewById(R.id.recycler_ordering);
        recyclerView_pruduct.setHasFixedSize(true);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
//        recyclerView_pruduct.setLayoutManager(layoutManager);
        adapterOrdering = new AdapterOrdering( dao_product.getList(),this );
        recyclerView_pruduct.setAdapter(adapterOrdering);
    }


}