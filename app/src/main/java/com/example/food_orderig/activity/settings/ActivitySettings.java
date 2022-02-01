package com.example.food_orderig.activity.settings;

import static com.example.food_orderig.activity.login.ActivityLogin.fingerprint;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import com.example.food_orderig.R;
import com.example.food_orderig.helper.Session;

public class ActivitySettings extends AppCompatActivity {

    Switch aSwitch;
    public static final String switch1 = "switchKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        initID();
        setSwitch();
    }

    private void setSwitch() {
        Boolean switchState = aSwitch.isChecked();
        aSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (switchState == false){
                    Toast.makeText(ActivitySettings.this, "test", Toast.LENGTH_SHORT).show();
                    aSwitch.setChecked(false);
                }else {
                    Session.getInstance().putExtra(swich1 , getUser);
                }

            }
        });
    }

    private void initID() {
        aSwitch = findViewById(R.id.switch_login);
    }
}