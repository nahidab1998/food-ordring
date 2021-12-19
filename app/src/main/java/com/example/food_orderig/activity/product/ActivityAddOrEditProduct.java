package com.example.food_orderig.activity.product;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
import com.example.food_orderig.model.Product;
import com.google.gson.Gson;

public class ActivityAddOrEditProduct extends AppCompatActivity {

    private EditText name , price;
    private TextView textViewcancle;
    private ImageView imageView_image_product;
    private CardView camera;
    private DatabaseHelper db;
    private ProductDao dao_product;
    private GroupingDao dao_grouping;
    private TextView btn_save_product;
    private String name_product , price_product , categoryProduct;
    private LinearLayout anim_product;
    private AutoCompleteTextView autoCompleteTextView;
    private ArrayAdapter<String> adapter_autocomplete;
    Product p = null;
    private static final int pick_image=1;
    private Uri imageuri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_or_edit_product);

        name=findViewById(R.id.add_edit_name_product);
        price = findViewById(R.id.add_edit_price_product);
        price.addTextChangedListener(new NumberTextWatcherForThousand(price));
        autoCompleteTextView = findViewById(R.id.autoComplete);
        camera = findViewById(R.id.camera);

        autoCompleteTextView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken() , 0);
                }

            }
        });

        anim_product = findViewById(R.id.anim_product);
        anim_product.setTranslationY(-1500f);
        anim_product.animate().translationYBy(1500f).setDuration(1500);


        if (getIntent().getExtras() != null){
            String getNameProduct = getIntent().getStringExtra("product");
            p = new Gson().fromJson(getNameProduct,Product.class);
            name.setText(p.name);
            autoCompleteTextView.setText(p.category);
            price.setText(p.price);
        }

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



        textViewcancle=findViewById(R.id.cancel_product);
        btn_save_product = findViewById(R.id.save_product);
        imageView_image_product = findViewById(R.id.add_img_food_product);

        db = App.getDatabase();
        dao_product = db.productDao();
        dao_grouping = db.groupingDao();
        adapter_autocomplete = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_dropdown_item_1line,dao_grouping.getname());
        autoCompleteTextView.setThreshold(1);
        autoCompleteTextView.setAdapter(adapter_autocomplete);


        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent gallery = new Intent();
                gallery.setType("image/*");
//                gallery.setAction(Intent.ACTION_GET_CONTENT);
                gallery.setAction(Intent.ACTION_OPEN_DOCUMENT);
                startActivityForResult(Intent.createChooser(gallery,"select picture"),pick_image);
//                Toast.makeText(ActivityAddOrEditProduct.this, "به زودی", Toast.LENGTH_SHORT).show();
            }
        });


        textViewcancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { finish();}
        });

        btn_save_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name_product = name.getText().toString();
                price_product = price.getText().toString();
                categoryProduct = autoCompleteTextView.getText().toString();

                if (p == null){
                    if(TextUtils.isEmpty(name_product) || TextUtils.isEmpty(categoryProduct) || TextUtils.isEmpty(price_product) || imageView_image_product.getDrawable() == null){
                        Toast.makeText(getApplicationContext(), "فیلد مورد نظر را پرکنید", Toast.LENGTH_SHORT).show();
                    }else if (dao_product.getOneName(name_product) != null){

                        Toast.makeText(ActivityAddOrEditProduct.this, "این محصول وجود دارد ", Toast.LENGTH_SHORT).show();

                    }else {

                        dao_product.insertProduct(new Product(name_product,categoryProduct, price_product , imageuri.toString()));
                        finish();
                    }
                }else {
                    p.name = name_product;
                    p.category = categoryProduct;
                    p.price = price_product;
                    Log.e("qqqq", "onClick: update product=" + p.id );
                    dao_product.updateProduct(p);
                    finish();
                }


            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == pick_image){
            if (resultCode == RESULT_OK)
                imageuri=data.getData();
            imageView_image_product.setImageURI(imageuri);
        }
    }

}