package com.example.app;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.view.View.OnClickListener;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity1 extends AppCompatActivity {
    private List<Friend> friendList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        initFriends();
        FriendAdapter adapter = new FriendAdapter(MainActivity1.this,R.layout.friend_item,friendList);
        ListView listview =(ListView) findViewById(R.id.list_view);
        listview.setAdapter(adapter);
        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.hide();
        }
        ImageButton tittleBack = (ImageButton) findViewById(R.id.tittle_back);
        ImageButton tittleAdd = (ImageButton) findViewById(R.id.tittle_add);
        tittleBack.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                ((Activity) getBaseContext()).finish();
            }

        });
        tittleAdd.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(MainActivity1.this, com.example.app.AddFriend.class);
                startActivity(intent);
            }

        });

    }

    private void initFriends() {
        for (int i = 0; i < 3; i++){
            Friend ana = new Friend("Ana", R.drawable.girl);
            friendList.add(ana);

            Friend bob = new Friend("Bob", R.drawable.boy);
            friendList.add(bob);

            Friend carl = new Friend("Carl", R.drawable.cake);
            friendList.add(carl);

            Friend mike = new Friend("Mike", R.drawable.boy);
            friendList.add(mike);

            Friend steve = new Friend("Steve", R.drawable.pizza);
            friendList.add(steve);

       }
    }





}


