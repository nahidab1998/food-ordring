package com.example.food_orderig.activity.customer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.food_orderig.R;
import com.example.food_orderig.activity.grouping.ActivityAddOrEditGrouping;
import com.example.food_orderig.database.DatabaseHelper;
import com.example.food_orderig.database.dao.CustomerDao;
import com.example.food_orderig.model.Customer;
import com.example.food_orderig.model.Grouping;

public class ActivityAddOrEditCostomer extends AppCompatActivity {

    EditText name_addcustomer,phone_addcustomer,address_addcustomer;
    TextView textViewcancle;
    CustomerDao dao;
    DatabaseHelper db;
    Button btn_save_customer;
    String name,phone,address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_or_edit_costomer);

        btn_save_customer=findViewById(R.id.save_customer);
        textViewcancle=findViewById(R.id.cancle_customer);
        name_addcustomer=findViewById(R.id.add_edit_name_customer);
        phone_addcustomer=findViewById(R.id.add_edit_number_customer);
        address_addcustomer=findViewById(R.id.add_edit_address_customer);


        db = DatabaseHelper.getInstance(getApplicationContext());
        dao = db.customerDao();

        btn_save_customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name = name_addcustomer.getText().toString();
                phone = phone_addcustomer.getText().toString();
                address = address_addcustomer.getText().toString();

                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(address)){

                    Toast.makeText(ActivityAddOrEditCostomer.this, "فیلد مورد نظر را پر کنید", Toast.LENGTH_SHORT).show();

                }else {

//                    String name_customer = name_addcustomer.getText().toString();
//                    String phone_customer = phone_addcustomer.getText().toString();
//                    String address_customer = address_addcustomer.getText().toString();
                    Customer customer = new Customer(name , phone , address);
                    dao.insertCustomer(customer);
                    db.close();
                    finish();
                }
            }
        });

        textViewcancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });


    }

}