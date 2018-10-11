package com.kumarsunil17.tinstudent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActitivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_actitivity);
        startActivity(new Intent(SplashActitivity.this,MainActivity.class));
        finish();
    }
}
