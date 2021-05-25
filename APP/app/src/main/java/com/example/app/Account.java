package com.example.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Account extends AppCompatActivity {

    Button btn_head;
    Button btn_accountinfo1;
    Button btn_accountinfo2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        btn_head = findViewById(R.id.head);
        btn_head.setOnClickListener(listener);

        btn_accountinfo1 = findViewById(R.id.accountinfo1);
        btn_accountinfo1.setOnClickListener(listener2);

        btn_accountinfo2 = findViewById(R.id.accountinfo2);
        btn_accountinfo2.setOnClickListener(listener3);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.account);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.account:
                        //startActivity(new Intent(getApplicationContext(),Account.class));
                        //overridePendingTransition(0,0);
                        return true;
                    case R.id.moments:
                        startActivity(new Intent(getApplicationContext(),Moments.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.rehair:
                        startActivity(new Intent(getApplicationContext(),Rehair.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }

    Button.OnClickListener listener = new Button.OnClickListener() {
        public void onClick(View v) {
            Intent intent = new Intent(Account.this, AccountinActivity.class);
            startActivity(intent);
            Account.this.finish();
        }
    };

    Button.OnClickListener listener2 = new Button.OnClickListener() {
        public void onClick(View v) {
            Intent intent = new Intent(Account.this, MainActivity1.class);
            startActivity(intent);
            Account.this.finish();
        }
    };

    Button.OnClickListener listener3 = new Button.OnClickListener() {
        public void onClick(View v) {
            Intent intent = new Intent(Account.this, MainActivity1.class);
            startActivity(intent);
            Account.this.finish();
        }
    };



}