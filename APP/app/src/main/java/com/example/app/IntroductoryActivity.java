package com.example.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class IntroductoryActivity extends AppCompatActivity {
    ImageView logo,appname,bg;
    Animation anim;
    Handler mHandler = new Handler();


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

        setContentView(R.layout.activity_introductory);
        mHandler.postDelayed(new Runnable(){
            public void run(){
                Intent intent = new Intent(IntroductoryActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        },3000);
    }

}