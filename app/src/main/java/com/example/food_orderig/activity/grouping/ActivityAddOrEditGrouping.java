package com.example.food_orderig.activity.grouping;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.food_orderig.R;
import com.example.food_orderig.database.DatabaseHelper;
import com.example.food_orderig.database.dao.GroupingDao;
import com.example.food_orderig.helper.App;
import com.example.food_orderig.model.Grouping;
import com.google.gson.Gson;

public class ActivityAddOrEditGrouping extends AppCompatActivity {

    EditText editTextnameGrouping;
    DatabaseHelper db;
    GroupingDao dao;
    TextView btn_save_grouping;
    TextView btn_canclegrouping;
    ImageView imageViewadd_img_grouping;
    String name;
    LinearLayout anim_grouping;

    Grouping b = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_or_edit_grouping);

        editTextnameGrouping = findViewById(R.id.add_edit_number_grouping);
        btn_save_grouping=findViewById(R.id.save_grouping);
        btn_canclegrouping =findViewById(R.id.cancelgrouping);

        anim_grouping= findViewById(R.id.anim_grouping);
        anim_grouping.setTranslationY(-1500f);
        anim_grouping.animate().translationYBy(1500f).setDuration(1500);

        editTextnameGrouping.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken() , 0);
                }

            }
        });

        if (getIntent().getExtras() != null){
            String getNameGrouping = getIntent().getStringExtra("Grouping");
            b = new Gson().fromJson(getNameGrouping,Grouping.class);
            editTextnameGrouping.setText(b.name);
        }

        btn_canclegrouping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        db = App.getDatabase();
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

                if (b == null){
                    if(TextUtils.isEmpty(name)){
                        Toast.makeText(getApplicationContext(), "فیلد مورد نظر را پرکنید", Toast.LENGTH_SHORT).show();
                    }else {
                        dao.insertGrouping(new Grouping(name));
                        finish();
                    }
                }else {
                    b.name = name;
                    Log.e("qqqq", "onClick: update product=" + b.id );
                    dao.updateGrouping(b);
                    finish();
                }



            }
        });

    }
}