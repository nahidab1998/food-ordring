package com.example.food_orderig.activity.ordering;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.food_orderig.R;
import com.example.food_orderig.activity.customer.ActivityCustomer;
import com.example.food_orderig.activity.product.ActivityProduct;
import com.example.food_orderig.database.DatabaseHelper;
import com.example.food_orderig.database.dao.DetailOrderDao;
import com.example.food_orderig.adapter.AdapterOrdering;
import com.example.food_orderig.database.dao.SavedOrderDao;
import com.example.food_orderig.helper.Tools;
import com.example.food_orderig.model.Customer;
import com.example.food_orderig.model.DetailOrder;
import com.example.food_orderig.model.Order;
import com.example.food_orderig.model.Product;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ActivityOrdering extends AppCompatActivity {

    RecyclerView recyclerView;
    AdapterOrdering adapterOrdering;

    DatabaseHelper db;
    List<Product> orderDetailList;

    DetailOrderDao dao_detailorder;
    SavedOrderDao dao_savedorder;
    Customer customer;

    LinearLayout box_customer;
    CardView record_order,add_order;
    CardView cardView_pay;
//    private SlidrInterface slidr;
    TextView txtname;
    TextView name_customer;
    TextView number_order;
    TextView total ;
    TextView save_order ;
    CardView card_numberorder;
    ImageView delete_ordering;

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
        initSaveOrdeing();
        delete_ordering();

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


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            switch (requestCode){
                case 100:
                    String json_customer = data.getExtras().getString("json_customer");
                    customer = new Gson().fromJson(json_customer,Customer.class);
                    txtname.setText(customer.name);
                    txtname.setTextColor(getResources().getColor(R.color.matn));

                    break;
                case 200:
                    String json_product = data.getExtras().getString("json_product");
                    Product product = new Gson().fromJson(json_product,Product.class);
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
        delete_ordering = findViewById(R.id.delete_add_order);
        cardView_pay = findViewById(R.id.cardvie_pay);


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
            cardView_pay.setVisibility(View.VISIBLE);
            card_numberorder.setVisibility(View.VISIBLE);
            number_order.setText(orderDetailList.size()+"");
        }else {
            cardView_pay.setVisibility(View.GONE);
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

    private void delete_ordering(){
        delete_ordering.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
    }

    private void initSaveOrdeing(){
        save_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dao_savedorder.insertOrder(new Order(customer.name , "1" , customer.id , 1 , total.getText()+"" , "با تمام مخلفات" , getCurrentTime()));
                for (int i = 0; i < orderDetailList.size(); i++) {
                    dao_detailorder.insertDetailOrder(new DetailOrder(orderDetailList.get(i).name , orderDetailList.get(i).price , orderDetailList.get(i).category ,
                            Tools.convertToPrice(number_order.getText().toString()) , dao_savedorder.getOrderList().get(i).unit_code ));

                    Toast.makeText(ActivityOrdering.this, "سفارش ذخیره شد", Toast.LENGTH_SHORT).show();
                    finish();

                }
            }
        });
    }

    public String getCurrentTime(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy  |  hh:mm:ss aa");
        String datetime = dateFormat.format(c.getTime());
        return datetime;
    }

}