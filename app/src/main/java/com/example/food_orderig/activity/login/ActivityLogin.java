package com.example.food_orderig.activity.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.food_orderig.R;
import com.example.food_orderig.activity.mainpage.MainActivity;
import com.example.food_orderig.database.DatabaseHelper;
import com.example.food_orderig.database.dao.UserDao;
import com.example.food_orderig.helper.App;
import com.example.food_orderig.model.Customer;
import com.example.food_orderig.model.NewUser;
import com.example.food_orderig.model.Product;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class ActivityLogin extends AppCompatActivity {

    private TextView buttonlogin;
    DatabaseHelper db;
    private TextView new_account;
    private EditText username , password ;
    RelativeLayout save_register;
    private NewUser a = null;
    private String user , pass ;
    UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init_database();
        init_id();

        buttonlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent a = new Intent(ActivityLogin.this, MainActivity.class);
                startActivity(a);
            }
        });

        new_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(ActivityLogin.this);
                bottomSheetDialog.setContentView(R.layout.register);

                bottomSheetDialog.show();

                username = findViewById(R.id.username_register);
                password = findViewById(R.id.password_register);


                save_register =bottomSheetDialog.findViewById(R.id.save_register);

                save_register.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

//                        Toast.makeText(ActivityLogin.this, "hello", Toast.LENGTH_SHORT).show();
                        user = username.getText().toString();
                        pass = password.getText().toString();

                        if (a == null){
                            if (TextUtils.isEmpty(user) || TextUtils.isEmpty(pass)){

                                Toast.makeText(ActivityLogin.this, "آیتم های خالی را پر کنید", Toast.LENGTH_SHORT).show();

                            }else {

                                userDao.insertNewUser(new NewUser (user, pass));
                                bottomSheetDialog.dismiss();
                            }
                        }
                    }
                });

            }
        });
    }

    public void init_database(){
        db = App.getDatabase();
        userDao = db.userDao();
    }

    private void init_id(){
        buttonlogin=findViewById(R.id.buttonlogin);
        new_account = findViewById(R.id.new_account);
    }
}