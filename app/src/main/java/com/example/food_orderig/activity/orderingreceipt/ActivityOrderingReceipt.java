package com.example.food_orderig.activity.orderingreceipt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.food_orderig.R;
import com.example.food_orderig.adapter.AdapterReceipt;
import com.example.food_orderig.adapter.AdaptersavedOrdering;
import com.example.food_orderig.database.DatabaseHelper;
import com.example.food_orderig.database.dao.DetailOrderDao;

public class ActivityOrderingReceipt extends AppCompatActivity {

    RecyclerView recyclerView;
    AdapterReceipt adapterReceipt;

    DatabaseHelper db;
    DetailOrderDao dao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordering_receipt);

        initDatabase();
        initId();
        initRecycler();
    }

    private void initDatabase(){

        db = DatabaseHelper.getInstance(getApplicationContext());
        dao = db.detailOrderDao();
    }

    private void initId(){

        recyclerView = findViewById(R.id.recycle_receipt);
    }

    private void initRecycler(){
        recyclerView.setHasFixedSize(true);
        adapterReceipt = new AdapterReceipt(dao.getDetailOrderList(),this);
        recyclerView.setAdapter(adapterReceipt);
    }
}