package com.example.food_orderig.activity.product;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.food_orderig.R;
import com.example.food_orderig.database.DatabaseHelper;
import com.example.food_orderig.database.dao.ProductDao;
import com.example.food_orderig.model.Product;
import com.google.gson.Gson;

public class ActivityAddOrEditProduct extends AppCompatActivity {

    EditText name , category , price;
    TextView textViewcancle;
    ImageView imageView_image_product;
    DatabaseHelper db;
    ProductDao dao;
    TextView btn_save_product;
    String name_product , name_category , price_product;
    LinearLayout anim_product;
    Product p = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_or_edit_product);

        name=findViewById(R.id.add_edit_name_product);
        category = findViewById(R.id.add_edit_category_product);
        price = findViewById(R.id.add_edit_price_product);

        anim_product = findViewById(R.id.anim_product);
        anim_product.setTranslationY(-900f);
        anim_product.animate().translationYBy(900f).setDuration(1000);

//        Intent intent=getIntent();
//        String one = intent.getStringExtra("nameproduct");
//        name.setText(one);
//
//        String two = intent.getStringExtra("categoryproducy");
//        category.setText(two);
//
//        String three = intent.getStringExtra("priceproduct");
//        price.setText(three);

        if (getIntent().getExtras() != null){
            String getNameProduct = getIntent().getStringExtra("product");
            p = new Gson().fromJson(getNameProduct,Product.class);
            name.setText(p.name);
            price.setText(p.price);
            category.setText(p.category);
        }

        textViewcancle=findViewById(R.id.cancel_product);
        btn_save_product = findViewById(R.id.save_product);
        imageView_image_product = findViewById(R.id.add_img_food_product);

        db = DatabaseHelper.getInstance(getApplicationContext());
        dao = db.productDao();

        imageView_image_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ActivityAddOrEditProduct.this, "به زودی", Toast.LENGTH_SHORT).show();
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
                name_category = category.getText().toString();
                price_product = price.getText().toString();

                if (p == null){
                    if(TextUtils.isEmpty(name_product) || TextUtils.isEmpty(price_product) || TextUtils.isEmpty(name_category)){
                        Toast.makeText(getApplicationContext(), "فیلد مورد نظر را پرکنید", Toast.LENGTH_SHORT).show();
                    }else {
                        dao.insertProduct(new Product(name_product, price_product , name_category));
                    }
                }else {
                    p.name = name_product;
                    p.price = price_product;
                    p.category = name_category;
                    Log.e("qqqq", "onClick: update product=" + p.id );
                    dao.updateProduct(p);
                }

                finish();
            }
        });
    }
}