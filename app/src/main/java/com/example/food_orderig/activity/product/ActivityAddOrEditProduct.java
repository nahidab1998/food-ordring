package com.example.food_orderig.activity.product;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.food_orderig.R;

public class ActivityAddOrEditProduct extends AppCompatActivity {


    TextView textViewcancle;
    ImageView imageView_add_image_food;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_or_edit_product);

        textViewcancle=findViewById(R.id.cancel_product);

        imageView_add_image_food=findViewById(R.id.add_img_food);
        imageView_add_image_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ActivityAddOrEditProduct.this, "به زودی", Toast.LENGTH_SHORT).show();
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