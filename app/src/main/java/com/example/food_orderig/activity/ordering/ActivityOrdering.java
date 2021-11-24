package com.example.food_orderig.activity.ordering;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.food_orderig.R;
import com.example.food_orderig.activity.customer.ActivityCustomer;
import com.example.food_orderig.activity.product.ActivityProduct;
import com.example.food_orderig.database.DatabaseHelper;
import com.example.food_orderig.database.dao.ProductDao;
import com.example.food_orderig.helper.AdapterOrdering;
import com.example.food_orderig.model.Customer;
import com.example.food_orderig.model.Product;
import com.google.gson.Gson;

public class ActivityOrdering extends AppCompatActivity {

    RecyclerView recyclerView_pruduct;
    AdapterOrdering adapterOrdering;
    DatabaseHelper db;
    ProductDao dao_product;
    LinearLayout box_customer;
    TextView txtname;
    CardView record_order,add_order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordering);

        db = DatabaseHelper.getInstance(getApplicationContext());
        dao_product = db.productDao();

        txtname = findViewById(R.id.txtname);

        set_recycler_ordering();
        initID();
        initBoxCustomer();
        initBoxProduct();
    }

    public void set_recycler_ordering(){
        recyclerView_pruduct = findViewById(R.id.recycler_ordering);
        recyclerView_pruduct.setHasFixedSize(true);
        adapterOrdering = new AdapterOrdering( dao_product.getList(),this );
        recyclerView_pruduct.setAdapter(adapterOrdering);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            switch (requestCode){
                case 100:
                    String json_customer = data.getExtras().getString("json_customer");
                    Customer customer = new Gson().fromJson(json_customer,Customer.class);
                    txtname.setText(customer.name);
                    Toast.makeText(this, ""+customer.name, Toast.LENGTH_SHORT).show();
                    break;
                case 200:
                    String json_product = data.getExtras().getString("json_product");
                    Product product = new Gson().fromJson(json_product,Product.class);
                    Toast.makeText(this, ""+product.name, Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
    private void initID(){
        add_order = findViewById(R.id.add_order);
        box_customer = findViewById(R.id.box_custome);
    }

    private void initBoxCustomer(){
        box_customer.setOnClickListener(v ->{
            Intent intent = new Intent(this, ActivityCustomer.class);
            intent.putExtra("for_order", true);
            startActivityForResult(intent,100);
        });
    }

    private void initBoxProduct(){
        add_order.setOnClickListener(v ->{
            Intent intent = new Intent(this, ActivityProduct.class);
            intent.putExtra("for_order", true);
            startActivityForResult(intent,200);
        });
    }

}