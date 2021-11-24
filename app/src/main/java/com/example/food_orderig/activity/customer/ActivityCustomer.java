package com.example.food_orderig.activity.customer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.food_orderig.R;
import com.example.food_orderig.database.DatabaseHelper;
import com.example.food_orderig.database.dao.CustomerDao;
import com.example.food_orderig.helper.AdapterCustomer;
import com.example.food_orderig.model.Customer;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ActivityCustomer extends AppCompatActivity {

    FloatingActionButton fab_customer;
    CustomerDao dao_customer;
    DatabaseHelper db;
    AdapterCustomer adapterCustomer;
    RecyclerView recyclerView_customer;
    LinearLayout call;
    TextView name,phone,address;
    private boolean for_order = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        fab_customer=findViewById(R.id.fab_customer);

        db= DatabaseHelper.getInstance(getApplicationContext());
        dao_customer = db.customerDao();
        name =findViewById(R.id.name_customer);
        phone =findViewById(R.id.phone_customer);
        address = findViewById(R.id.address_customer);

        call = findViewById(R.id.call);

//        for_order = getIntent().getExtras().getBoolean("for_order",false);


        adapterCustomer = new AdapterCustomer(new ArrayList<>(), this , new AdapterCustomer.Listener() {
            @Override
            public void onClickListener(Customer customer) {
//                if (for_order){
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("json_customer",new Gson().toJson(customer));
                    setResult(Activity.RESULT_OK,returnIntent);
                    finish();
//                }
            }
        });

        recyclerView_customer = findViewById(R.id.recycle_customer);
        recyclerView_customer.setAdapter(adapterCustomer);

        recyclerView_customer.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {

                if (dy >0 ){

                    fab_customer.hide();

                }else {

                    fab_customer.show();
                }
                super.onScrolled(recyclerView, dx, dy);
            }
        });

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
    protected void onResume() {
        super.onResume();
        if (adapterCustomer != null){
            adapterCustomer.addList(dao_customer.getList());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (db != null) db.close();
    }

}