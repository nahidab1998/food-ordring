package com.example.food_orderig.activity.grouping;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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

import java.io.File;
import java.io.IOException;

public class ActivityAddOrEditGrouping extends AppCompatActivity {

    private EditText editTextnameGrouping;
    private DatabaseHelper db;
    private GroupingDao dao;
    private TextView btn_save_grouping;
    private TextView btn_canclegrouping;
    private ImageView imageViewadd_img_grouping;
    private static final int pick_image=1;
    private String name;
    private ImageView image_camera;
    private LinearLayout anim_grouping;
    private CardView camera;
    private Uri imageuri;
    private File file;
    private String picture_s;
    private Grouping b = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_or_edit_grouping);

        initDataBase();
        initID();
        set_AddImage();
        btn_save();
        btn_close();
        page_animation();
        set_TapEditText();

        //get Gson from GroupingAdapter
        if (getIntent().getExtras() != null){
            String getNameGrouping = getIntent().getStringExtra("Grouping");
            b = new Gson().fromJson(getNameGrouping,Grouping.class);
            editTextnameGrouping.setText(b.name);
        }
    }

    private void set_TapEditText() {

        editTextnameGrouping.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken() , 0);
                }
            }
        });
    }

    private void page_animation() {
        anim_grouping.setTranslationY(-1500f);
        anim_grouping.animate().translationYBy(1500f).setDuration(1500);
    }

    private void btn_close() {
        btn_canclegrouping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void btn_save() {

        btn_save_grouping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name = editTextnameGrouping.getText().toString();
//                picture_s = imageViewadd_img_grouping.getDrawable().toString();
                if (b == null){
                    if(imageViewadd_img_grouping.getDrawable() == null){
                        Toast.makeText(getApplicationContext(), "لطفا یک عکس انتخاب کنید", Toast.LENGTH_SHORT).show();
                    }
                    else if(TextUtils.isEmpty(name) ){
                        Toast.makeText(getApplicationContext(), "فیلد مورد نظر را پرکنید", Toast.LENGTH_SHORT).show();
                    }else if (dao.getOneName(name) != null){

                        Toast.makeText(ActivityAddOrEditGrouping.this, "این دسته بندی وجود دارد", Toast.LENGTH_SHORT).show();

                    }else {
                        dao.insertGrouping(new Grouping(name , imageuri.toString()));
                        finish();
                    }
                }else {
                    b.name = name;

                    Log.e("qqqq", "onClick: update product=" + b.id );
                    dao.updateGrouping(b);
//                    Toast.makeText(getApplicationContext(),  " دسته بندی " + old_name + " با موفقیت به " + name + " تغییر کرد", Toast.LENGTH_LONG).show();
                    finish();
                }

            }
        });
    }

    private void set_AddImage() {

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent gallery = new Intent();
                gallery.setType("image/*");
//                gallery.setAction(Intent.ACTION_GET_CONTENT);
                gallery.setAction(Intent.ACTION_OPEN_DOCUMENT);
                startActivityForResult(Intent.createChooser(gallery,"select picture"),pick_image);
//                Toast.makeText(ActivityAddOrEditGrouping.this, "به زودی", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initID() {
        editTextnameGrouping = findViewById(R.id.add_edit_number_grouping);
        btn_save_grouping=findViewById(R.id.save_grouping);
        btn_canclegrouping =findViewById(R.id.cancelgrouping);
        anim_grouping= findViewById(R.id.anim_grouping);
        camera=findViewById(R.id.camera);
        imageViewadd_img_grouping=findViewById(R.id.add_img_food_grouping);
    }

    private void initDataBase() {
        db = App.getDatabase();
        dao = db.groupingDao();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == pick_image && resultCode == RESULT_OK){
//            imageuri=data.getData();
////            file = new File(imageuri.getPath());
////            picture_s = file.toString();
//            try {
//                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),imageuri);
//                imageViewadd_img_grouping.setImageBitmap(bitmap);
//            }catch (IOException e){
//                e.printStackTrace();
//            }
//        }
        if (requestCode == pick_image){
            if (resultCode == RESULT_OK)
            imageuri=data.getData();
            imageViewadd_img_grouping.setImageURI(imageuri);
        }
    }
}