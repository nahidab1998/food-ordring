package com.example.food_orderig.activity.ordering;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.food_orderig.R;
import com.example.food_orderig.activity.customer.ActivityCustomer;
import com.example.food_orderig.activity.product.ActivityProduct;
import com.example.food_orderig.database.DatabaseHelper;
import com.example.food_orderig.database.dao.DetailOrderDao;
import com.example.food_orderig.adapter.AdapterOrdering;
import com.example.food_orderig.database.dao.SavedOrderDao;
import com.example.food_orderig.helper.Tools;
import com.example.food_orderig.model.Customer;
import com.example.food_orderig.model.Order;
import com.example.food_orderig.model.Product;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ActivityOrdering extends AppCompatActivity {

    RecyclerView recyclerView;
    AdapterOrdering adapterOrdering;

    Customer customer;
    DatabaseHelper db;
    List<Product> orderDetailList;

    DetailOrderDao dao_detailorder;
    SavedOrderDao dao_savedorder;

    LinearLayout box_customer;
    CardView record_order,add_order;
//    private SlidrInterface slidr;
    TextView txtname;
    TextView name_customer;
    TextView number_order;
    TextView total ;
    TextView save_order ;
    CardView card_numberorder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordering);

        Log.e("bbbbb", "onCreate: " + System.currentTimeMillis() );

        db = DatabaseHelper.getInstance(getApplicationContext());
        dao_savedorder = db.savedOrderDao();
        dao_detailorder = db.detailOrderDao();

        initID();
        initBoxCustomer();
        initBoxProduct();

        orderDetailList = new ArrayList<>();
        adapterOrdering = new AdapterOrdering(orderDetailList, this, new AdapterOrdering.Listener() {
            @Override
            public void onAdded(int pos) {
                orderDetailList.get(pos).amount = orderDetailList.get(pos).amount + 1;
                adapterOrdering.notifyItemChanged(pos);
                initCounter();
            }

            @Override
            public void onRemove(int pos) {
                if (orderDetailList.get(pos).amount > 1){
                    orderDetailList.get(pos).amount = orderDetailList.get(pos).amount - 1;
                    adapterOrdering.notifyItemChanged(pos);
                }else {
                    orderDetailList.remove(pos);
                    adapterOrdering.notifyDataSetChanged();
                }
                initCounter();
            }
        });

        recyclerView.setAdapter(adapterOrdering);
        recyclerView.setHasFixedSize(true);

//        initSaveOrdeing();
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
                    txtname.setTextColor(getResources().getColor(R.color.matn));
//                    Toast.makeText(this, ""+customer.name, Toast.LENGTH_SHORT).show();
                    break;
                case 200:
                    String json_product = data.getExtras().getString("json_product");
                    Product product = new Gson().fromJson(json_product,Product.class);
//                    Toast.makeText(this, ""+product.name, Toast.LENGTH_SHORT).show();

                    insertToOrderList(product);
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
        card_numberorder = findViewById(R.id.card_numberorder);
        txtname = findViewById(R.id.txtname);


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
        if (orderDetailList.size() > 0){
            card_numberorder.setVisibility(View.VISIBLE);
            number_order.setText(orderDetailList.size()+"");
        }else {
            card_numberorder.setVisibility(View.GONE);
        }
        total.setText(getTotalPrice()+"");
    }

    private void insertToOrderList(Product product){
        for (int i = 0; i < orderDetailList.size(); i++) {
            if (orderDetailList.get(i).id == product.id){
                orderDetailList.get(i).amount = orderDetailList.get(i).amount + 1;
                adapterOrdering.notifyDataSetChanged();
                initCounter();
                return;
            }
        }
        orderDetailList.add(product);
        adapterOrdering.notifyDataSetChanged();
        initCounter();
    }

    private Integer getTotalPrice(){
        int p = 0;
        for (int i = 0; i < orderDetailList.size(); i++) {
            p = p + (Tools.convertToPrice(orderDetailList.get(i).price) * orderDetailList.get(i).amount);
        }
        return p;
    }

    private void initSaveOrdeing(){
        save_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dao_savedorder.insertOrder(new Order(customer.name , "1" , customer.id , 1 , total.getText()+"" , "با تمام مخلفات" ));
            }
        });
    }

}