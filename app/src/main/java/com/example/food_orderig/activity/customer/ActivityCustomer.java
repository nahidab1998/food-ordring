package com.example.food_orderig.activity.customer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.example.food_orderig.R;
import com.example.food_orderig.database.DatabaseHelper;
import com.example.food_orderig.database.dao.CustomerDao;
import com.example.food_orderig.helper.AdapterCustomer;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ActivityCustomer extends AppCompatActivity {

    FloatingActionButton fab_customer;
    CustomerDao dao_customer;
    DatabaseHelper db;
    AdapterCustomer adapterCustomer;
    RecyclerView recyclerView_customer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        fab_customer=findViewById(R.id.fab_customer);

        db= DatabaseHelper.getInstance(getApplicationContext());
        dao_customer = db.customerDao();

        adapterCustomer = new AdapterCustomer( dao_customer.getList() , this );
        recyclerView_customer = findViewById(R.id.recycle_customer);
        recyclerView_customer.setAdapter(adapterCustomer);

//        recyclerView_customer.setLayoutManager(new GridLayoutManager(this,2));

        fab_customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent add_new_customer = new Intent(ActivityCustomer.this,ActivityAddOrEditCostomer.class);
                startActivity(add_new_customer);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (db != null) db.close();
    }
}