package com.example.food_orderig.activity.product;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.food_orderig.R;
import com.example.food_orderig.activity.grouping.ActivityAddOrEditGrouping;
import com.example.food_orderig.database.DatabaseHelper;
import com.example.food_orderig.database.dao.GroupingDao;
import com.example.food_orderig.database.dao.ProductDao;
import com.example.food_orderig.design.NumberTextWatcherForThousand;
import com.example.food_orderig.helper.App;
import com.example.food_orderig.helper.Permition;
import com.example.food_orderig.helper.Tools;
import com.example.food_orderig.model.Grouping;
import com.example.food_orderig.model.Product;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;

public class ActivityAddOrEditProduct extends AppCompatActivity {

    private EditText name , price;
    private TextView textViewcancle;
    private ImageView imageView_image_product , imageView_img_product_back;
    private FloatingActionButton camera;
    private DatabaseHelper db;
    private ProductDao dao_product;
    private GroupingDao dao_grouping;
    private TextView btn_save_product;
    private String name_product , price_product , categoryProduct , save;
    private Uri img_uri;
    private ConstraintLayout anim_product;
    private AutoCompleteTextView autoCompleteTextView;
    private ArrayAdapter<String> adapter_autocomplete;
    private Product p = null;
    private static final int pick_image=1;
    private Uri resultUri;
    private String cameraPermission[];
    private String storagePermission[];
    private static final int GalleryPick = 1;
    private static final int CAMERA_REQUEST = 100;
    private static final int STORAGE_REQUEST = 200;
    private static final int IMAGEPICK_GALLERY_REQUEST = 300;
    private static final int IMAGE_PICKCAMERA_REQUEST = 400;
    private static final int STORAGE_PERMISSION_CODE = 101;
    public static final int CALL_PERMISSION_CODE = 100;
    private String Timemilisecond = String.valueOf(System.currentTimeMillis());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_or_edit_product);

        setDatabase();
        setId();
        setImage();
        setCliableEdittext();
        setCancleButton();
        setSaveButton();
        page_animation();



        price.addTextChangedListener(new NumberTextWatcherForThousand(price));

        cameraPermission = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        //get Gson from ProductAdapter
        if (getIntent().getExtras() != null){
            String getNameProduct = getIntent().getStringExtra("product");
            p = new Gson().fromJson(getNameProduct,Product.class);
            name.setText(p.name);
            autoCompleteTextView.setText(p.category);
            img_uri = Uri.parse(p.picture);
            imageView_image_product.setImageURI(Uri.parse(p.picture));
            imageView_img_product_back.setVisibility(View.GONE);
            price.setText(p.price);
        }

        //for autoconmplate
        adapter_autocomplete = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_dropdown_item_1line,dao_grouping.getname());
        autoCompleteTextView.setThreshold(1);
        autoCompleteTextView.setAdapter(adapter_autocomplete);
    }

    private void page_animation() {
        anim_product.setTranslationY(-1500f);
        anim_product.animate().translationYBy(1500f).setDuration(1500);
    }

    private void setCancleButton() {

        textViewcancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { finish();}
        });
    }

    private void setCliableEdittext() {

        name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken() , 0);
                }
            }
        });

        price.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken() , 0);
                }
            }
        });

        autoCompleteTextView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken() , 0);
                }
            }
        });

    }

    private void setDatabase() {
        db = App.getDatabase();
        dao_product = db.productDao();
        dao_grouping = db.groupingDao();
    }

    private void setId() {
        name=findViewById(R.id.add_edit_name_product);
        price = findViewById(R.id.add_edit_price_product);
        autoCompleteTextView = findViewById(R.id.autoComplete);
        camera = findViewById(R.id.camera);
        anim_product = findViewById(R.id.anim_product);
        textViewcancle=findViewById(R.id.cancel_product);
        btn_save_product = findViewById(R.id.save_product);
        imageView_image_product = findViewById(R.id.add_img_food_product);
        imageView_img_product_back = findViewById(R.id.image_back_product);
    }

    private void setImage(){
        camera.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {

                Permition permition;
                permition = new Permition(100,getApplicationContext(), ActivityAddOrEditProduct.this);
                if(permition.checkPermission()) {
                    CropImage.activity()
                            .setGuidelines(CropImageView.Guidelines.ON)
                            .start(ActivityAddOrEditProduct.this);
                }
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                resultUri = result.getUri();
                save = Tools.saveFile(Tools.getBytes(resultUri),new File( Environment.getExternalStorageDirectory() + "/DCIM/Foods") , Timemilisecond +".jpg");
                imageView_image_product.setImageURI(resultUri);
//                Picasso.with(this).load(resultUri).into(imageView_image_product);
                imageView_img_product_back.setVisibility(View.GONE);
            }else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    private void setSaveButton() {

        btn_save_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name_product = name.getText().toString();
                price_product = price.getText().toString();
                categoryProduct = autoCompleteTextView.getText().toString();

                if (p == null){

                    if (imageView_image_product.getDrawable() == null){
                        Toast.makeText(getApplicationContext(), "???????? ???? ?????? ?????????? ????????", Toast.LENGTH_SHORT).show();
                    }
                     else if(TextUtils.isEmpty(name_product) || TextUtils.isEmpty(categoryProduct) || TextUtils.isEmpty(price_product)){
                        Toast.makeText(getApplicationContext(), "???????? ???????? ?????? ???? ????????????", Toast.LENGTH_SHORT).show();
                    }else {
                        if (dao_product.getOneName(name_product) != null){
                            Toast.makeText(ActivityAddOrEditProduct.this, "?????? ?????????? ???????? ???????? ", Toast.LENGTH_SHORT).show();

                        }else if(dao_grouping.getOneName(categoryProduct) == null){
                            dao_grouping.insertGrouping(new Grouping(categoryProduct, ""));
                            Toast.makeText(ActivityAddOrEditProduct.this, "???????? ???????? ???????? ?????????? ???? ", Toast.LENGTH_SHORT).show();
                        }else{
                            dao_product.insertProduct(new Product(name_product,categoryProduct, price_product , save));
                            finish();
                        }
                    }

                }else {
                    p.name = name_product;
                    p.category = categoryProduct;
                    p.price = price_product;
                    if (resultUri == null){
                        resultUri = img_uri;
                        p.picture = resultUri.toString();
                    }else {
                        p.picture = resultUri.toString();
                    }
                    p.picture = resultUri.toString();
                    Log.e("qqqq", "onClick: update product=" + p.id );
                    dao_product.updateProduct(p);
                    finish();
                }
            }
        });
    }

}