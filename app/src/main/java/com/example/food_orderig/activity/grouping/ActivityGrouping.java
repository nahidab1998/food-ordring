package com.example.food_orderig.activity.grouping;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.food_orderig.R;
import com.example.food_orderig.activity.customer.ActivityAddOrEditCostomer;
import com.example.food_orderig.activity.customer.ActivityCustomer;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ActivityGrouping extends AppCompatActivity {

    ImageView imageView;
    FloatingActionButton fab_grouping;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grouping);

        imageView=findViewById(R.id.img_grouping);
        fab_grouping =findViewById(R.id.fab_grouping);

        fab_grouping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent add_new_grouping = new Intent(ActivityGrouping.this, ActivityAddOrEditGrouping.class);
                startActivity(add_new_grouping);
            }
        });


    }
}