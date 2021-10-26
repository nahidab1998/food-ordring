package com.example.food_orderig.activity.product;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.helper.widget.Carousel;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Adapter;
import android.widget.Toast;

import com.example.food_orderig.R;
import com.example.food_orderig.activity.product.mvp.ProductModel;
import com.example.food_orderig.activity.product.mvp.ProductView;

public class ActivityProduct extends AppCompatActivity implements ProductView {
    ProductModel presenter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        recyclerView=findViewById(R.id.recycle);

        presenter = new ProductModel(this);
        presenter.getData();

    }

    @Override
    public void setData(String name) {
        Toast.makeText(this, name, Toast.LENGTH_SHORT).show();
    }
}