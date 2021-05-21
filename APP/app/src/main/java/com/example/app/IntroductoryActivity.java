package com.example.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class IntroductoryActivity extends AppCompatActivity {
    ImageView logo,appname,bg;
    Animation anim;
    Handler mHandler = new Handler();
    Button btn;


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

        anim = AnimationUtils.loadAnimation(this,R.anim.login_page);

        btn = (Button) findViewById(R.id.BTN);
        btn.setOnClickListener(listener);
        };

    Button.OnClickListener listener = new Button.OnClickListener() {
        public void onClick(View v) {
            Intent intent = new Intent(IntroductoryActivity.this, LoginActivity.class);
            startActivity(intent);
            IntroductoryActivity.this.finish();
        }
    };
 }