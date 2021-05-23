package com.example.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SendMomentsActivity extends AppCompatActivity {

    Button btn_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_moments);
        btn_back = findViewById(R.id.btn_backToMoments);
        btn_back.setOnClickListener(listener);
    }
    Button.OnClickListener listener = new Button.OnClickListener() {
        public void onClick(View v) {
            Intent intent = new Intent(SendMomentsActivity.this, Moments.class);
            startActivity(intent);
            SendMomentsActivity.this.finish();
        }
    };
}