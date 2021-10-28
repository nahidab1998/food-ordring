package com.example.food_orderig.activity.mainpage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.food_orderig.R;
import com.example.food_orderig.activity.customer.ActivityCustomer;
import com.example.food_orderig.activity.grouping.ActivityGrouping;
import com.example.food_orderig.activity.product.ActivityProduct;

public class MainActivity extends AppCompatActivity {

    CardView cardViewproduct,cardViewcustomer , cardViewprouping;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cardViewproduct=findViewById(R.id.products);
        cardViewcustomer=findViewById(R.id.customer);
        cardViewprouping=findViewById(R.id.grouping);


        cardViewproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i= new Intent(MainActivity.this, ActivityProduct.class);
                startActivity(i);


            }
        });

        cardViewcustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Toast.makeText(MainActivity.this, "به زودی", Toast.LENGTH_SHORT).show();
                Intent q= new Intent(MainActivity.this, ActivityCustomer.class);
                startActivity(q);

            }
        });

        cardViewprouping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Toast.makeText(MainActivity.this, "به زودی", Toast.LENGTH_SHORT).show();

                Intent r= new Intent(MainActivity.this, ActivityGrouping.class);
                startActivity(r);
            }
        });

    }
}