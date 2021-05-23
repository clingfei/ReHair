package com.example.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Moments extends AppCompatActivity {

    Button btn_send;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moments);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.moments);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.account:
                        startActivity(new Intent(getApplicationContext(),Account.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.moments:
                        //startActivity(new Intent(getApplicationContext(),Moments.class));
                        //overridePendingTransition(0,0);
                        return true;
                    case R.id.rehair:
                        startActivity(new Intent(getApplicationContext(),Rehair.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        btn_send = findViewById(R.id.btn_sendMoments);
        btn_send.setOnClickListener(listener);
    }
    Button.OnClickListener listener = new Button.OnClickListener() {
        public void onClick(View v) {
            Intent intent = new Intent(Moments.this, SendMomentsActivity.class);
            startActivity(intent);
        }
    };
}