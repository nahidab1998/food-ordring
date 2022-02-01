package com.example.food_orderig.activity.settings;

import static com.example.food_orderig.activity.login.ActivityLogin.fingerprint;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
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
    }

    private void setSwitch() {
        Boolean switchState = aSwitch.isChecked();
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Session.getInstance().putExtra(switch1 , switchState);
                    fingerprint.setVisibility(View.VISIBLE);
                   aSwitch.setChecked(true);
                    Toast.makeText(ActivitySettings.this, "true", Toast.LENGTH_SHORT).show();

                }else if(!isChecked){
                    Session.getInstance().clearExtras();
                    fingerprint.setVisibility(View.GONE);
                    aSwitch.setChecked(false);
                    Toast.makeText(ActivitySettings.this, "false", Toast.LENGTH_SHORT).show();
                }
            }
        });

            aSwitch.setChecked(Session.getInstance().getBoolean(switch1));
//            fingerprint.setVisibility(View.GONE);
        if (switchState == true){
            fingerprint.setVisibility(View.VISIBLE);
        }else{
            fingerprint.setVisibility(View.GONE);
        }
    }

    private void initID() {
        aSwitch = findViewById(R.id.switch_login);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setSwitch();
    }
}