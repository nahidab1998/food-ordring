package com.example.food_orderig.activity.product;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.food_orderig.R;
import com.example.food_orderig.activity.product.mvp.ProductModel;
import com.example.food_orderig.activity.product.mvp.ProductView;
import com.example.food_orderig.database.DatabaseHelper;
import com.example.food_orderig.database.dao.GroupingDao;
import com.example.food_orderig.database.dao.ProductDao;
import com.example.food_orderig.adapter.AdapterGroupingProduct;
import com.example.food_orderig.adapter.AdapterProduct;
import com.example.food_orderig.model.Product;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.ArrayList;

public class ActivityProduct extends AppCompatActivity implements ProductView {

//    ProductModel presenter;
    RecyclerView recyclerView_product;
    RecyclerView recyclerView_category;
    FloatingActionButton floatingActionButton_product;
    ProductDao dao_product;
    GroupingDao dao_grouping;
    DatabaseHelper db;
    AdapterProduct adapterProduct;
    AdapterGroupingProduct adapterGroupingProduct;
//    CardView toolbarmain_pro;

    private Boolean for_order = false ;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        if(getIntent().getExtras() != null){
            for_order = getIntent().getBooleanExtra("for_order",false);
        }


//        toolbarmain_pro = findViewById(R.id.toolbarmain_product);
        floatingActionButton_product = findViewById(R.id.fab_product);

//        presenter = new ProductModel(this);
//        presenter.getData();


        db= DatabaseHelper.getInstance(getApplicationContext());
        dao_product= db.productDao();
        dao_grouping = db.groupingDao();

        set_recycle_grouping_product();
        set_RecyclerView_product();

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

        floatingActionButton_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent add_new_product = new Intent(ActivityProduct.this, ActivityAddOrEditProduct.class);
                startActivity(add_new_product);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(adapterProduct != null){
            adapterProduct.addList(dao_product.getList());
        }
    }

    public  void set_RecyclerView_product(){

        recyclerView_product=findViewById(R.id.recycle_product);
        recyclerView_product.setHasFixedSize(true);
        adapterProduct = new AdapterProduct(new ArrayList<>(), this, new AdapterProduct.Listener() {
            @Override
            public void onClick(Product product , int pos) {

                if(for_order){
                    for_order = getIntent().getBooleanExtra("for_order",false);
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("json_product", new Gson().toJson(product));
                    setResult(Activity.RESULT_OK, returnIntent);
                    finish();
                }else {
//
                    adapterProduct.showBottomSheetDialogclick(pos);
                }
            }
        });
        recyclerView_product.setAdapter(adapterProduct);
        adapterProduct.notifyDataSetChanged();
    }

    public void set_recycle_grouping_product(){
        recyclerView_category =findViewById(R.id.recycler_grouping_product);
        recyclerView_category.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this ,LinearLayoutManager.HORIZONTAL , false);
        recyclerView_category.setLayoutManager(layoutManager);
        adapterGroupingProduct = new AdapterGroupingProduct(dao_grouping.getList(),this);
        recyclerView_category.setAdapter(adapterGroupingProduct);
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