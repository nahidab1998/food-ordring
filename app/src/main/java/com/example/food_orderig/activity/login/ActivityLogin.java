package com.example.food_orderig.activity.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.food_orderig.R;
import com.example.food_orderig.activity.product.ActivityProduct;
import com.example.food_orderig.database.DatabaseHelper;
import com.example.food_orderig.model.Product;

import java.util.List;

public class ActivityLogin extends AppCompatActivity {
    DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        startActivity(new Intent(this, ActivityProduct.class));


        new Thread(() -> {
            db =DatabaseHelper.getInstance(this);
            Product p = new Product("ahid","qom");
            db.productDao().updatePerson(p);
        });


        List<Product> p = db.productDao().getList();
        for (int i = 0; i < p.size() ; i++) {
            Log.e("qqqqq", "onCreate: " + p.get(i).id );
        }

    }
}