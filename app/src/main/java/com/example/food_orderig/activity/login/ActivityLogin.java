package com.example.food_orderig.activity.login;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.hardware.biometrics.BiometricPrompt;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.food_orderig.R;
import com.example.food_orderig.activity.file.FileActivity;
import com.example.food_orderig.activity.mainpage.MainActivity;
import com.example.food_orderig.database.DatabaseHelper;
import com.example.food_orderig.database.dao.UserDao;
import com.example.food_orderig.helper.App;
import com.example.food_orderig.helper.Session;
import com.example.food_orderig.model.Customer;
import com.example.food_orderig.model.NewUser;
import com.example.food_orderig.model.Product;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.util.concurrent.Executor;

public class ActivityLogin extends AppCompatActivity {

    private TextView buttonlogin;
    private TextView new_account;
    private EditText username , password ;
    private TextInputEditText username_login , password_login ;
    private NewUser a = null;
    private String user , pass ;
    private String user_login , pass_login ;
    private CheckBox checkBox;
    public static ImageView fingerprint;
    private UserDao userDao;
    private DatabaseHelper db;
    private RelativeLayout save_register;
    private SharedPreferences shPref;
    public static final String MyPref = "MyPrefers";
    public static final String Name = "nameKey";
    public static final String Pass = "passKey";
    public static final String check = "isCheck";
    private androidx.biometric.BiometricPrompt biometricPrompt;
    private androidx.biometric.BiometricPrompt.PromptInfo promptInfo ;



    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init_database();
        init_id();
        new_acount();
        buttonLogin();
        setcheckBox();
        setFingerprint();
//        set_TapEditText();

    }

//    private void set_TapEditText() {
//
//        username.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (!hasFocus) {
//                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                    imm.hideSoftInputFromWindow(v.getWindowToken() , 0);
//                }
//            }
//        });
//        password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (!hasFocus) {
//                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                    imm.hideSoftInputFromWindow(v.getWindowToken() , 0);
//                }
//            }
//        });
//    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    private void setFingerprint() {

        BiometricManager biometricManager = androidx.biometric.BiometricManager.from(ActivityLogin.this);
        switch (biometricManager.canAuthenticate()) {

            case BiometricManager.BIOMETRIC_SUCCESS:

                break;

            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:

                fingerprint.setVisibility(View.GONE);

                break;

            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:

                fingerprint.setVisibility(View.GONE);

                break;

            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:

                fingerprint.setVisibility(View.GONE);

                break;
        }

        Executor executor = ContextCompat.getMainExecutor(ActivityLogin.this);

        biometricPrompt = new androidx.biometric.BiometricPrompt(ActivityLogin.this, executor, new androidx.biometric.BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull androidx.biometric.BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);

                Intent a = new Intent(ActivityLogin.this, MainActivity.class);
                startActivity(a);
//                Toast.makeText(getApplicationContext(), "ورود با موفقیت", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
            }
        });

        promptInfo = new androidx.biometric.BiometricPrompt.PromptInfo.Builder().setTitle("ورود با اثرانگشت")
                .setDescription("لطفا انگشت خود را روی حسگر اثر انگشت قرار دهید ").setNegativeButtonText("بستن").build();
        fingerprint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                biometricPrompt.authenticate(promptInfo);

            }
        });

    }

    private void setcheckBox() {
        checkBox.setChecked(false);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    String getUser = username_login.getText().toString();
                    String getPass = password_login.getText().toString();
                    if (TextUtils.isEmpty(getUser)|| TextUtils.isEmpty(getPass)){
                        Toast.makeText(ActivityLogin.this, "فیلد های خالی را پر کنید", Toast.LENGTH_SHORT).show();
                        checkBox.setChecked(false);
                    }else {
                        Boolean checkBox_state = checkBox.isChecked();
                        Session.getInstance().putExtra(Name , getUser);
                        Session.getInstance().putExtra(Pass , getPass);
                        Session.getInstance().putExtra(check , checkBox_state);
                    }
                }else if (!isChecked){
                    Session.getInstance().clearExtras();
                }
            }
        });

        if(Session.getInstance().getString(Name) != null || Session.getInstance().getString(Pass) != null ){
            username_login.setText(Session.getInstance().getString(Name));
            password_login.setText(Session.getInstance().getString(Pass));
            checkBox.setChecked(Session.getInstance().getBoolean(check));
        }
    }

    private void buttonLogin(){

        buttonlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                user_login = username_login.getText().toString();
                pass_login = password_login.getText().toString();

                if (TextUtils.isEmpty(user_login) || TextUtils.isEmpty(pass_login)){

                    Toast.makeText(ActivityLogin.this, "آیتم های خالی را پر کنید", Toast.LENGTH_SHORT).show();

                }else if ( userDao.getOneName(user_login ,pass_login) != null ) {

//                    Intent a = new Intent(ActivityLogin.this, MainActivity.class);
                    Intent a = new Intent(ActivityLogin.this, MainActivity.class);
                    a.putExtra("name_restaurant", user_login);
                    startActivity(a);
                }else {
                    Toast.makeText(ActivityLogin.this, "همچین رستورانی وجود ندارد", Toast.LENGTH_SHORT).show();
                }
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
        username_login = findViewById(R.id.text_inputuser);
        password_login = findViewById(R.id.text_inputpass);
        checkBox = findViewById(R.id.remember);
        fingerprint = findViewById(R.id.fingerprint);
    }

    private void new_acount(){

        new_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(ActivityLogin.this);
                bottomSheetDialog.setContentView(R.layout.register);
                bottomSheetDialog.show();

                username = bottomSheetDialog.findViewById(R.id.username_register);
                password = bottomSheetDialog.findViewById(R.id.password_register);
                save_register =bottomSheetDialog.findViewById(R.id.save_register);
                save_register.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        user = username.getText().toString();
                        pass = password.getText().toString();

                        if (TextUtils.isEmpty(user) || TextUtils.isEmpty(pass)){

                            Toast.makeText(ActivityLogin.this, "فیلد های خالی را پر کنید", Toast.LENGTH_SHORT).show();

                        }else {
                            userDao.insertNewUser(new NewUser (user, pass));
                            Toast.makeText(ActivityLogin.this, "با موفقیت ذخیره شد ", Toast.LENGTH_SHORT).show();
                            bottomSheetDialog.dismiss();
                        }
                    }
                });

            }
        });

    }
}