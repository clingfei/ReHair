package com.example.app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

public class IntroductoryActivity extends AppCompatActivity {
    ImageView logo,appname,bg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introductory);
        logo = findViewById(R.id.logo);
        appname = findViewById(R.id.app_name);
        bg = findViewById(R.id.bg);
        bg.animate().translationY(-3000).setDuration(1000).setStartDelay(2000);
        appname.animate().translationY(2200).setDuration(1000).setStartDelay(2000);
        logo.animate().translationY(2200).setDuration(1000).setStartDelay(2000);
    }

}