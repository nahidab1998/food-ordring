package com.example.food_orderig.activity.product;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
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
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

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

    private Uri resultUri;
    String cameraPermission[];
    String storagePermission[];

    private static final int GalleryPick = 1;
    private static final int CAMERA_REQUEST = 100;
    private static final int STORAGE_REQUEST = 200;
    private static final int IMAGEPICK_GALLERY_REQUEST = 300;
    private static final int IMAGE_PICKCAMERA_REQUEST = 400;

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

        price.addTextChangedListener(new NumberTextWatcherForThousand(price));

        cameraPermission = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};


        anim_product.setTranslationY(-1500f);
        anim_product.animate().translationYBy(1500f).setDuration(1500);

        if (getIntent().getExtras() != null){
            String getNameProduct = getIntent().getStringExtra("product");
            p = new Gson().fromJson(getNameProduct,Product.class);
            name.setText(p.name);
            autoCompleteTextView.setText(p.category);
            price.setText(p.price);
        }

        adapter_autocomplete = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_dropdown_item_1line,dao_grouping.getname());
        autoCompleteTextView.setThreshold(1);
        autoCompleteTextView.setAdapter(adapter_autocomplete);
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
    }

    private void setImage(){
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent gallery = new Intent();
//                gallery.setType("image/*");
////                gallery.setAction(Intent.ACTION_GET_CONTENT);
//                gallery.setAction(Intent.ACTION_OPEN_DOCUMENT);
//                startActivityForResult(Intent.createChooser(gallery,"select picture"),pick_image);
////                Toast.makeText(ActivityAddOrEditProduct.this, "به زودی", Toast.LENGTH_SHORT).show();
                String options[] = {"دوربین", "گالری"};
                AlertDialog.Builder builder = new AlertDialog.Builder(ActivityAddOrEditProduct.this);
                builder.setTitle("انتخاب تصویر از");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            if (!checkCameraPermission()) {
                                requestCameraPermission();
                            } else {
                                pickFromGallery();
                            }
                        } else if (which == 1) {
                            if (!checkStoragePermission()) {
                                requestStoragePermission();
                            } else {
                                pickFromGallery();
                            }
                        }
                    }
                });
                builder.create().show();
            }
        });
    }

    private Boolean checkStoragePermission() {
        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result;
    }

    // Requesting  gallery permission
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestStoragePermission() {
        requestPermissions(storagePermission, STORAGE_REQUEST);
    }

    // checking camera permissions
    private Boolean checkCameraPermission() {
        boolean result = ContextCompat.checkSelfPermission(ActivityAddOrEditProduct.this, Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
        boolean result1 = ContextCompat.checkSelfPermission(ActivityAddOrEditProduct.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result && result1;
    }

    // Requesting camera permission
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestCameraPermission() {
        requestPermissions(cameraPermission, CAMERA_REQUEST);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CAMERA_REQUEST: {
                if (grantResults.length > 0) {
                    boolean camera_accepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean writeStorageaccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (camera_accepted && writeStorageaccepted) {
                        pickFromGallery();
                    } else {
                        Toast.makeText(ActivityAddOrEditProduct.this, "لطفاً مجوزهای دوربین و ذخیره سازی را فعال کنید", Toast.LENGTH_LONG).show();
                    }
                }
            }
            break;
            case STORAGE_REQUEST: {
                if (grantResults.length > 0) {
                    boolean writeStorageaccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (writeStorageaccepted) {
                        pickFromGallery();
                    } else {
                        Toast.makeText(ActivityAddOrEditProduct.this, "لطفاً مجوزهای ذخیره سازی را فعال کنید", Toast.LENGTH_LONG).show();
                    }
                }
            }
            break;
        }
    }

    // Here we will pick image from gallery or camera
    private void pickFromGallery() {
        CropImage.activity().start(ActivityAddOrEditProduct.this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                resultUri = result.getUri();
//                imageView_image_product.setImageURI(resultUri);
                Picasso.with(this).load(resultUri).into(imageView_image_product);
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
                    if(TextUtils.isEmpty(name_product) || TextUtils.isEmpty(categoryProduct) || TextUtils.isEmpty(price_product) || imageView_image_product.getDrawable() == null){
                        Toast.makeText(getApplicationContext(), "فیلد مورد نظر را پرکنید", Toast.LENGTH_SHORT).show();
                    }else if (dao_product.getOneName(name_product) != null){

                        Toast.makeText(ActivityAddOrEditProduct.this, "این محصول وجود دارد ", Toast.LENGTH_SHORT).show();

                    }else {

                        dao_product.insertProduct(new Product(name_product,categoryProduct, price_product , resultUri.toString()));
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

}