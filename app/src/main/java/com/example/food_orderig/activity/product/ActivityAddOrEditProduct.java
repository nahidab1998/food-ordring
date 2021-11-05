package com.example.food_orderig.activity.product;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.food_orderig.R;
import com.example.food_orderig.database.DatabaseHelper;
import com.example.food_orderig.database.dao.ProductDao;
import com.example.food_orderig.model.Product;

public class ActivityAddOrEditProduct extends AppCompatActivity {

    EditText name , category , price;
    TextView textViewcancle;
    ImageView imageView_image_product;
    DatabaseHelper db;
    ProductDao dao;
    Button btn_save_product;
    String name_product , name_category , price_product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_or_edit_product);

        name=findViewById(R.id.add_edit_name_product);
        category = findViewById(R.id.add_edit_category_product);
        price = findViewById(R.id.add_edit_price_product);

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

                if (TextUtils.isEmpty(name_product) || TextUtils.isEmpty(name_category)) {

                    Toast.makeText(ActivityAddOrEditProduct.this, "فیلد مورد نظر را پر کنید", Toast.LENGTH_SHORT).show();

                }else {
                    Product product = new Product(name_product , name_category , price_product);
                    dao.insertProduct(product);
                    finish();
                }
            }
        });
    }
}