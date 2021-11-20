package com.example.food_orderig.activity.customer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.food_orderig.R;
import com.example.food_orderig.database.DatabaseHelper;
import com.example.food_orderig.database.dao.CustomerDao;
import com.example.food_orderig.model.Customer;
import com.example.food_orderig.model.Product;
import com.google.gson.Gson;

import java.util.List;

public class ActivityAddOrEditCostomer extends AppCompatActivity {

    EditText name_addcustomer,phone_addcustomer,address_addcustomer;
    TextView textViewcancle;
    CustomerDao dao;
    DatabaseHelper db;
    TextView btn_save_customer;
    String name,phone,address;
    LinearLayout anim_customer;
    Customer a = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_or_edit_costomer);

        btn_save_customer=findViewById(R.id.save_customer);
        textViewcancle=findViewById(R.id.cancle_customer);
        name_addcustomer=findViewById(R.id.add_edit_name_customer);
        phone_addcustomer=findViewById(R.id.add_edit_number_customer);
        address_addcustomer=findViewById(R.id.add_edit_address_customer);

        anim_customer=findViewById(R.id.anim_customer);
        anim_customer.setTranslationY(-1500f);
        anim_customer.animate().translationYBy(1500f).setDuration(1500);


//        Intent intent=getIntent();
//        String zero = intent.getStringExtra("idcustomer");
//
//        String one = intent.getStringExtra("namecustomer");
//        name_addcustomer.setText(one);
//
//        String two = intent.getStringExtra("phonecustomer");
//        phone_addcustomer.setText(two);
//
//        String three = intent.getStringExtra("addresscustomer");
//        address_addcustomer.setText(three);

        if (getIntent().getExtras() != null){
            String getNameCustomer = getIntent().getStringExtra("Customer");
            a = new Gson().fromJson(getNameCustomer,Customer.class);
            name_addcustomer.setText(a.name);
            phone_addcustomer.setText(a.phone);
            address_addcustomer.setText(a.address);
        }

        db = DatabaseHelper.getInstance(getApplicationContext());
        dao = db.customerDao();

        btn_save_customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name = name_addcustomer.getText().toString();
                phone = phone_addcustomer.getText().toString();
                address = address_addcustomer.getText().toString();

                if (a == null){
                    if(TextUtils.isEmpty(name) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(address)){
                        Toast.makeText(getApplicationContext(), "فیلد مورد نظر را پرکنید", Toast.LENGTH_SHORT).show();
                    }else {
                        dao.insertCustomer(new Customer (name, phone , address));
                        finish();
                    }
                }else {
                    a.name = name;
                    a.phone = phone;
                    a.address = address;
                    Log.e("qqqq", "onClick: update product=" + a.id );
                    dao.updateCustomer(a);
                    finish();
                }


            }
        });

        textViewcancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { finish();
            }
        });
    }


}