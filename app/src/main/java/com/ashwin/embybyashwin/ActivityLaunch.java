package com.ashwin.embybyashwin;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityLaunch extends AppCompatActivity {
    
    private String TAG = "ActivityLaunch";
    private Boolean isLogin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_launch);

        OpenLoginActivity();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void OpenMainActivity(){
        // Send user to ActivityMain as soon as this activity loads
        Intent intent = new Intent(this, ActivityMain.class);
        startActivity(intent);
        finish();
    }

    private void OpenLoginActivity(){
        // Send user to ActivityLogin as soon as this activity loads
        Intent intent = new Intent(this, ActivityLogin.class);
        startActivity(intent);
        finish();
    }


}

