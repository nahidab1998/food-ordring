package com.example.food_orderig.activity.grouping;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.example.food_orderig.R;

public class ActivityGrouping extends AppCompatActivity {

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grouping);

        imageView=findViewById(R.id.img_grouping);


    }
}