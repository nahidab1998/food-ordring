package com.example.food_orderig.activity.grouping;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.food_orderig.R;
import com.example.food_orderig.database.DatabaseHelper;
import com.example.food_orderig.database.dao.GroupingDao;
import com.example.food_orderig.helper.AdapterGrouping;
import com.example.food_orderig.model.Grouping;
import com.example.food_orderig.model.Product;

public class ActivityAddOrEditGrouping extends AppCompatActivity {

    EditText editTextnameGrouping;
    DatabaseHelper db;
    GroupingDao dao;
    TextView btn_save_grouping;
    TextView btn_canclegrouping;
    ImageView imageViewadd_img_grouping;
    String name;
    LinearLayout anim_grouping;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_or_edit_grouping);

        editTextnameGrouping = findViewById(R.id.add_edit_number_grouping);
        btn_save_grouping=findViewById(R.id.save_grouping);
        btn_canclegrouping =findViewById(R.id.cancelgrouping);

        anim_grouping= findViewById(R.id.anim_grouping);
        anim_grouping.setTranslationY(-900f);
        anim_grouping.animate().translationYBy(900f).setDuration(1000);


        Intent intent=getIntent();
        String one = intent.getStringExtra("namegrouping");
        editTextnameGrouping.setText(one);

        btn_canclegrouping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        db = DatabaseHelper.getInstance(getApplicationContext());
        dao = db.groupingDao();

        imageViewadd_img_grouping=findViewById(R.id.add_img_food_grouping);
        imageViewadd_img_grouping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ActivityAddOrEditGrouping.this, "به زودی", Toast.LENGTH_SHORT).show();
            }
        });

        btn_save_grouping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name = editTextnameGrouping.getText().toString();
                if (TextUtils.isEmpty(name)) {

                    Toast.makeText(ActivityAddOrEditGrouping.this, "فیلد مورد نظر را پر کنید", Toast.LENGTH_SHORT).show();

                }else {
                    Grouping grouping = new Grouping(name);
                    dao.insertGrouping(grouping);
                    finish();
                }

            }
        });

    }
}