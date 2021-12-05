package com.example.food_orderig.activity.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.food_orderig.R;
import com.example.food_orderig.activity.mainpage.MainActivity;
import com.example.food_orderig.database.DatabaseHelper;
import com.example.food_orderig.model.Product;

public class ActivityLogin extends AppCompatActivity {

    TextView buttonlogin;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        buttonlogin=findViewById(R.id.buttonlogin);

        buttonlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent a = new Intent(ActivityLogin.this, MainActivity.class);
                startActivity(a);
            }
        });


//        startActivity(new Intent(this, ActivityProduct.class));

//        new Thread(() -> {
//            db =DatabaseHelper.getInstance(this);
//            Product p = new Product("nahid","qom");
//            db.productDao().insertProduct(p);
//
////            List<Product> a = db.productDao().getList();
////            for (int i = 0; i < a.size() ; i++) {
////                Log.e("qqqqq", "onCreate: " );
////            }
//        });
//        Log.e("qqq", "hi");

    }
}