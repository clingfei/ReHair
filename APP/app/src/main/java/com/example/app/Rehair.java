package com.example.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Rehair extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rehair);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.rehair);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.account:
                        startActivity(new Intent(getApplicationContext(),Account.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.moments:
                        startActivity(new Intent(getApplicationContext(),Moments.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.rehair:
                        //startActivity(new Intent(getApplicationContext(),Rehair.class));
                        //overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }
}