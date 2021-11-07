package com.example.food_orderig.activity.customer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.food_orderig.R;
import com.example.food_orderig.database.DatabaseHelper;
import com.example.food_orderig.database.dao.CustomerDao;
import com.example.food_orderig.model.Customer;

import java.util.List;

public class ActivityAddOrEditCostomer extends AppCompatActivity {

    EditText name_addcustomer,phone_addcustomer,address_addcustomer;
    TextView textViewcancle;
    CustomerDao dao;
    DatabaseHelper db;
    TextView btn_save_customer;
    String name,phone,address;
    ImageView delete;
    Context context;

    List<Customer> list;
    Customer customer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_or_edit_costomer);

        btn_save_customer=findViewById(R.id.save_customer);
        textViewcancle=findViewById(R.id.cancle_customer);
        name_addcustomer=findViewById(R.id.add_edit_name_customer);
        phone_addcustomer=findViewById(R.id.add_edit_number_customer);
        address_addcustomer=findViewById(R.id.add_edit_address_customer);

//        Bundle bn=getIntent().getExtras();
//        String nameedittext= bn.getString("nameCustomer");
//        name_addcustomer.setText(Bundle.(nameedittext));

        Intent intent=getIntent();
        String one = intent.getExtras().getString("key1");
        name_addcustomer.setText(one);


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                db = DatabaseHelper.getInstance(context.getApplicationContext());
//                dao = db.customerDao();
//                dao.deleteCustomer(customer);
//                list.remove();
////                notifyItemRemoved(pos);
////                notifyItemRangeChanged(pos,list.size());
////                notifyDataSetChanged();
//                Toast.makeText(context, "با موفقیت حذف شد ", Toast.LENGTH_LONG).show();

            }
        });

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

                    Customer customer = new Customer(name , phone , address);
                    dao.insertCustomer(customer);
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