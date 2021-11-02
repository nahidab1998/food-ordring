package com.example.food_orderig.activity.product;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.food_orderig.R;
import com.example.food_orderig.activity.product.mvp.ProductModel;
import com.example.food_orderig.activity.product.mvp.ProductView;
import com.example.food_orderig.database.DatabaseHelper;
import com.example.food_orderig.database.dao.ProductDao;
import com.example.food_orderig.helper.AdapterProduct;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ActivityProduct extends AppCompatActivity implements ProductView {

    ProductModel presenter;
    RecyclerView recyclerView_product;
    FloatingActionButton floatingActionButton_product;
    ProductDao dao_product;
    DatabaseHelper db;
    AdapterProduct adapterProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        recyclerView_product=findViewById(R.id.recycle_product);

        presenter = new ProductModel(this);
        presenter.getData();

        db= DatabaseHelper.getInstance(getApplicationContext());
        dao_product= db.productDao();
        adapterProduct = new AdapterProduct( dao_product.getList() , this );
        recyclerView_product.setAdapter(adapterProduct);

        floatingActionButton_product = findViewById(R.id.fab_product);
        floatingActionButton_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent add_new_product = new Intent(ActivityProduct.this, ActivityAddOrEditProduct.class);
                startActivity(add_new_product);
            }
        });

    }
    @Override
    public void setData(String name) {
//        Toast.makeText(this, name, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (db != null) db.close();
    }

}