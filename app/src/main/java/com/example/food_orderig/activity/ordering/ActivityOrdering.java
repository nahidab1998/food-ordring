package com.example.food_orderig.activity.ordering;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.food_orderig.R;
import com.example.food_orderig.activity.customer.ActivityCustomer;
import com.example.food_orderig.activity.product.ActivityProduct;
import com.example.food_orderig.database.DatabaseHelper;
import com.example.food_orderig.database.dao.DetailOrderDao;
import com.example.food_orderig.database.dao.ProductDao;
import com.example.food_orderig.adapter.AdapterOrdering;
import com.example.food_orderig.helper.Tools;
import com.example.food_orderig.model.Customer;
import com.example.food_orderig.model.DetailOrder;
import com.example.food_orderig.model.Product;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ActivityOrdering extends AppCompatActivity {

    RecyclerView recyclerView;
    AdapterOrdering adapterOrdering;
    DatabaseHelper db;
    ProductDao dao_product;
    LinearLayout box_customer;
    TextView txtname;
    CardView record_order,add_order;
//    private SlidrInterface slidr;
    TextView name_customer;
    LottieAnimationView lottie;
    DetailOrder detailOrder ;
    DetailOrderDao detailOrderDao;
    List<Product> list1 ;
    TextView number_order;
    TextView total ;
    TextView save_order ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordering);

        db = DatabaseHelper.getInstance(getApplicationContext());
        dao_product = db.productDao();

        txtname = findViewById(R.id.txtname);


        initID();
        initBoxCustomer();
        initBoxProduct();

        list1 = new ArrayList<>();
        adapterOrdering = new AdapterOrdering(list1, this, new AdapterOrdering.Listener() {
            @Override
            public void onAdded(int pos) {
                list1.get(pos).amount = list1.get(pos).amount + 1;
                adapterOrdering.notifyItemChanged(pos);
                initCounter();
            }

            @Override
            public void onRemove(int pos) {
                if (list1.get(pos).amount > 0){
                    list1.get(pos).amount = list1.get(pos).amount - 1;
                    adapterOrdering.notifyItemChanged(pos);
                }else {
                    list1.remove(pos);
                    adapterOrdering.notifyDataSetChanged();
                }
                initCounter();
            }
        });

        recyclerView.setAdapter(adapterOrdering);
        recyclerView.setHasFixedSize(true);
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
//                    Toast.makeText(this, ""+product.name, Toast.LENGTH_SHORT).show();

                    list1.add(product);
                    adapterOrdering.notifyDataSetChanged();
                    initCounter();
                    break;
            }
        }
    }

    private void initID(){
        add_order = findViewById(R.id.add_order);
        box_customer = findViewById(R.id.box_custome);
        name_customer=findViewById(R.id.txtname);
        total = findViewById(R.id.total);
        save_order = findViewById(R.id.save_order);
        number_order = findViewById(R.id.text_number_of_order);
        recyclerView = findViewById(R.id.recycler_ordering);


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
    private void initCounter(){
        number_order.setText(list1.size()+"");
        total.setText(getTotalPrice()+"");
    }

    private Integer getTotalPrice(){
        int p = 0;
        for (int i = 0; i < list1.size(); i++) {
            p = p + (Tools.convertToPrice(list1.get(i).price) * list1.get(i).amount);
        }
        return p;
    }

}