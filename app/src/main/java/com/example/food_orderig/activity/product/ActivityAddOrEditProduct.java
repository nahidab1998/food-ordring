package com.example.food_orderig.activity.product;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
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
import com.example.food_orderig.model.Product;
import com.google.gson.Gson;

public class ActivityAddOrEditProduct extends AppCompatActivity {

    EditText name , price;
    TextView textViewcancle;
    ImageView imageView_image_product;
    DatabaseHelper db;
    ProductDao dao_product;
    GroupingDao dao_grouping;
    TextView btn_save_product;
    String name_product , price_product , categoryProduct;
    LinearLayout anim_product;
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> adapter_autocomplete;
    Product p = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_or_edit_product);

        name=findViewById(R.id.add_edit_name_product);
        price = findViewById(R.id.add_edit_price_product);
        price.addTextChangedListener(new NumberTextWatcherForThousand(price));
        autoCompleteTextView = findViewById(R.id.autoComplete);

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


        textViewcancle=findViewById(R.id.cancel_product);
        btn_save_product = findViewById(R.id.save_product);
        imageView_image_product = findViewById(R.id.add_img_food_product);

        db = DatabaseHelper.getInstance(getApplicationContext());
        dao_product = db.productDao();
        dao_grouping = db.groupingDao();
        autoCompleteTextView = findViewById(R.id.autoComplete);
        adapter_autocomplete = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_dropdown_item_1line,dao_grouping.getname());
        autoCompleteTextView.setThreshold(1);
        autoCompleteTextView.setAdapter(adapter_autocomplete);


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
                price_product = price.getText().toString();
                categoryProduct = autoCompleteTextView.getText().toString();

                if (p == null){
                    if(TextUtils.isEmpty(name_product) || TextUtils.isEmpty(categoryProduct) || TextUtils.isEmpty(price_product)){
                        Toast.makeText(getApplicationContext(), "فیلد مورد نظر را پرکنید", Toast.LENGTH_SHORT).show();
                    }else {
                        dao_product.insertProduct(new Product(name_product,categoryProduct, price_product));
                    }
                }else {
                    p.name = name_product;
                    p.category = categoryProduct;
                    p.price = price_product;
                    Log.e("qqqq", "onClick: update product=" + p.id );
                    dao_product.updateProduct(p);
                }

                finish();
            }
        });
    }
//    private String converText(String text){
//        StringBuilder stringBuilder = new StringBuilder(text);
//        for (int i = stringBuilder.length() - 3 ; i >0 ; i -= 3){
//            stringBuilder.insert( i , ",");
//        }
//        return stringBuilder.toString();
//    }

}