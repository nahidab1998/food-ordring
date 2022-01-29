package com.example.food_orderig.activity.grouping;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
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
import com.example.food_orderig.activity.product.ActivityAddOrEditProduct;
import com.example.food_orderig.database.DatabaseHelper;
import com.example.food_orderig.database.dao.GroupingDao;
import com.example.food_orderig.helper.App;
import com.example.food_orderig.helper.Tools;
import com.example.food_orderig.model.Grouping;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.IOException;

public class ActivityAddOrEditGrouping extends AppCompatActivity {

    private EditText editTextnameGrouping;
    private DatabaseHelper db;
    private GroupingDao dao;
    private TextView btn_save_grouping;
    private TextView btn_canclegrouping;
    private ImageView imageViewadd_img_grouping , imageView_img_grouping_back;
    private static final int pick_image=1;
    private String name , save;
    private ImageView image_camera;
    private ConstraintLayout anim_grouping;
    private FloatingActionButton camera;
    private Uri imageuri , img_uri;
    private File file;
    private String picture_s;
    private Grouping b = null;
    private static final int CAMERA_REQUEST = 100;
    private static final int STORAGE_REQUEST = 200;
    private static final int IMAGEPICK_GALLERY_REQUEST = 300;
    private static final int IMAGE_PICKCAMERA_REQUEST = 400;
    private static final int STORAGE_PERMISSION_CODE = 101;
    public static final int CALL_PERMISSION_CODE = 100;
    private String cameraPermission[];
    private String storagePermission[];
    private String Timemilisecond = String.valueOf(System.currentTimeMillis());


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
            img_uri = Uri.parse(b.picture);
            imageView_img_grouping_back.setVisibility(View.GONE);
            imageViewadd_img_grouping.setImageURI(Uri.parse(b.picture));
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
                        Toast.makeText(getApplicationContext(), "لطفا یک عکس آپلود کنید", Toast.LENGTH_SHORT).show();
                    }
                    else if(TextUtils.isEmpty(name) ){
                        Toast.makeText(getApplicationContext(), "فیلد مورد نظر را پرکنید", Toast.LENGTH_SHORT).show();
                    }else if (dao.getOneName(name) != null){

                        Toast.makeText(ActivityAddOrEditGrouping.this, "این دسته بندی وجود دارد", Toast.LENGTH_SHORT).show();

                    }else {
                        dao.insertGrouping(new Grouping(name , save));
                        finish();
                    }
                }else {
                    b.name = name;
                    if (imageuri == null){
                        imageuri = img_uri;
                        b.picture = imageuri.toString();
                    }else {
                        b.picture = imageuri.toString();
                    }
                    b.picture = imageuri.toString();
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

                String options[] = {"دوربین", "گالری"};
                AlertDialog.Builder builder = new AlertDialog.Builder(ActivityAddOrEditGrouping.this);
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
        boolean result = ContextCompat.checkSelfPermission(ActivityAddOrEditGrouping.this, Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
        boolean result1 = ContextCompat.checkSelfPermission(ActivityAddOrEditGrouping.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
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
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setMessage("فعال سازی مجوزها");
                        builder.setPositiveButton("تنظیمات", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package", getPackageName(), null);
                                intent.setData(uri);
                                startActivityForResult(intent, STORAGE_PERMISSION_CODE);
                            }
                        });
                        builder.setNegativeButton("خروچ", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        builder.show();
//                        Toast.makeText(ActivityAddOrEditProduct.this, "لطفاً دسترسی به دوربین و فایل را فعال کنید", Toast.LENGTH_LONG).show();
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
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setMessage("فعال سازی مجوزها");
                        builder.setPositiveButton("تنظیمات", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package", getPackageName(), null);
                                intent.setData(uri);
                                startActivityForResult(intent, STORAGE_PERMISSION_CODE);
                            }
                        });
                        builder.setNegativeButton("بستن", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        builder.show();
//                        Toast.makeText(ActivityAddOrEditProduct.this, "لطفاً مجوزهای ذخیره سازی را فعال کنید", Toast.LENGTH_LONG).show();
                    }
                }
            }
            break;
        }
    }

    // Here we will pick image from gallery or camera
    private void pickFromGallery() {
        CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).start(ActivityAddOrEditGrouping.this);
    }



    private void initID() {
        editTextnameGrouping = findViewById(R.id.add_edit_number_grouping);
        btn_save_grouping=findViewById(R.id.save_grouping);
        btn_canclegrouping =findViewById(R.id.cancelgrouping);
        anim_grouping= findViewById(R.id.anim_grouping);
        camera=findViewById(R.id.camera);
        imageViewadd_img_grouping=findViewById(R.id.add_img_food_grouping);
        imageView_img_grouping_back = findViewById(R.id.image_back_grouping);
    }

    private void initDataBase() {
        db = App.getDatabase();
        dao = db.groupingDao();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                imageuri = result.getUri();
                save = Tools.saveFile(Tools.getBytes(imageuri),new File( Environment.getExternalStorageDirectory() + "/DCIM/Foods") , Timemilisecond +".jpg");
                imageViewadd_img_grouping.setImageURI(imageuri);
//                Picasso.with(this).load(resultUri).into(imageView_image_product);
                imageView_img_grouping_back.setVisibility(View.GONE);
            }else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }

    }
}