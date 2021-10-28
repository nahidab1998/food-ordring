package com.example.food_orderig.activity.customer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.food_orderig.R;
import com.example.food_orderig.activity.login.ActivityLogin;
import com.example.food_orderig.activity.mainpage.MainActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ActivityCustomer extends AppCompatActivity {


    FloatingActionButton fab_customer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        fab_customer=findViewById(R.id.fab_customer);
        fab_customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent o = new Intent(ActivityCustomer.this,ActivityAddOrEditCostomer.class);
                startActivity(o);
            }
        });
    }
}