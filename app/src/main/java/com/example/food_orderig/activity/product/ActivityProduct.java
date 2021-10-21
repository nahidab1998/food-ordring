package com.example.food_orderig.activity.product;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.food_orderig.R;
import com.example.food_orderig.activity.product.mvp.ProductModel;
import com.example.food_orderig.activity.product.mvp.ProductView;

public class ActivityProduct extends AppCompatActivity implements ProductView {
    ProductModel presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        presenter = new ProductModel(this);
        presenter.getData();

    }

    @Override
    public void setData(String name) {
        Toast.makeText(this, name, Toast.LENGTH_SHORT).show();
    }
}