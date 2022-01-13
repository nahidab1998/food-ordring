package com.example.food_orderig.activity.orderingdetails;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.food_orderig.R;
import com.example.food_orderig.adapter.AdaptersavedOrdering;
import com.example.food_orderig.database.DatabaseHelper;
import com.example.food_orderig.database.dao.SavedOrderDao;
import com.example.food_orderig.helper.App;

public class ActivityOrderingDetails extends AppCompatActivity {

    RecyclerView recyclerView;
    AdaptersavedOrdering adaptersavedOrdering;

    SavedOrderDao dao;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordering_details);

        initDatabase();
        initId();
        initRecycler();


    }

    private void initDatabase(){
        dao = App.getDatabase().savedOrderDao();
    }

    private void initId(){
        recyclerView = findViewById(R.id.recycle_saved);
    }

    private void initRecycler(){
        recyclerView.setHasFixedSize(true);
        adaptersavedOrdering = new AdaptersavedOrdering(this, dao.getOrderListDate("1400/10/21"));
        recyclerView.setAdapter(adaptersavedOrdering);
    }
}