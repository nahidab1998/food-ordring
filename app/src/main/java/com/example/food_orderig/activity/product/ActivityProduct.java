package com.example.food_orderig.activity.product;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.food_orderig.R;
import com.example.food_orderig.activity.product.mvp.ProductModel;
import com.example.food_orderig.activity.product.mvp.ProductView;
import com.example.food_orderig.database.DatabaseHelper;
import com.example.food_orderig.database.dao.ProductDao;
import com.example.food_orderig.helper.AdapterProduct;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ActivityProduct extends AppCompatActivity implements ProductView {

    ProductModel presenter;
    RecyclerView recyclerView_product;
    FloatingActionButton floatingActionButton_product;
    ProductDao dao_product;
    DatabaseHelper db;
    AdapterProduct adapterProduct;
    Toolbar toolbardel_pro;
    ImageButton btnback;
    public boolean isActionMode = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        recyclerView_product=findViewById(R.id.recycle_product);

        presenter = new ProductModel(this);
        presenter.getData();

        toolbardel_pro = findViewById(R.id.toolbardel_pro);
        toolbardel_pro.setVisibility(View.GONE);

        btnback = findViewById(R.id.btn_back);
        btnback.setVisibility(View.GONE);


        db= DatabaseHelper.getInstance(getApplicationContext());
        dao_product= db.productDao();
        adapterProduct = new AdapterProduct( new ArrayList<>(), this);
        recyclerView_product.setAdapter(adapterProduct);

        recyclerView_product.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {

                if (dy >0 ){

                    floatingActionButton_product.hide();

                }else {

                    floatingActionButton_product.show();
                }
                super.onScrolled(recyclerView, dx, dy);
            }

        });

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
    protected void onResume() {
        super.onResume();
        if (adapterProduct != null){
            adapterProduct.addList(dao_product.getList());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (db != null) db.close();
    }

}