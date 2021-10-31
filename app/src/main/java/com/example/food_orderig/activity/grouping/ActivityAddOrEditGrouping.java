package com.example.food_orderig.activity.grouping;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.food_orderig.R;
import com.example.food_orderig.database.DatabaseHelper;
import com.example.food_orderig.model.Grouping;
import com.example.food_orderig.model.Product;

public class ActivityAddOrEditGrouping extends AppCompatActivity {

    EditText editTextnameGrouping;
    DatabaseHelper db;
    Button btn_save_grouping;
    Button btn_canclegrouping;
    ImageView imageViewadd_img_grouping;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_or_edit_grouping);
        editTextnameGrouping = findViewById(R.id.add_edit_number_grouping);
        btn_save_grouping=findViewById(R.id.save_grouping);

        imageViewadd_img_grouping=findViewById(R.id.add_img_food_grouping);
        imageViewadd_img_grouping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(ActivityAddOrEditGrouping.this, "به زودی", Toast.LENGTH_SHORT).show();
            }
        });

        editTextnameGrouping.getText();

        btn_save_grouping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                String b=editTextnameGrouping.getText().toString();
//                db =DatabaseHelper.getInstance(getApplicationContext());
//                Grouping a = new Grouping(b);
//                db.groupingDao().insertGrouping(a);


            }
        });

    }
}