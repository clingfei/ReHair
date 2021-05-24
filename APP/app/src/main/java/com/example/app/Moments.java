package com.example.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.app.DRVinterface.LoadMore;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Moments extends AppCompatActivity {

    private RecyclerView recyclerView;
    private StaticRvAdapter staticRvAdapter;

    List<DynamicRVModel> items = new ArrayList();
    DynamicRVAdapter dynamicRVAdapter;

    Button btn_send;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moments);
//recycler
        ArrayList<StaticRvModel> item = new ArrayList<>();
        item.add(new StaticRvModel(R.drawable.photo1,"test1"));
        item.add(new StaticRvModel(R.drawable.photo2,"test2"));
        item.add(new StaticRvModel(R.drawable.photo1,"test3"));
        item.add(new StaticRvModel(R.drawable.photo2,"test4"));
        item.add(new StaticRvModel(R.drawable.photo1,"test5"));
        item.add(new StaticRvModel(R.drawable.photo2,"test6"));

        recyclerView = findViewById(R.id.rv_moments);
        staticRvAdapter = new StaticRvAdapter(item);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        recyclerView.setAdapter(staticRvAdapter);

        items.add(new DynamicRVModel("photo"));
        items.add(new DynamicRVModel("photo"));
        items.add(new DynamicRVModel("photo"));
        items.add(new DynamicRVModel("photo"));
        items.add(new DynamicRVModel("photo"));
        items.add(new DynamicRVModel("photo"));
        items.add(new DynamicRVModel("photo"));
        items.add(new DynamicRVModel("photo"));
        items.add(new DynamicRVModel("photo"));

        RecyclerView drv = findViewById(R.id.rv_moments);
        drv.setLayoutManager(new LinearLayoutManager(this));
        dynamicRVAdapter = new DynamicRVAdapter(drv,this,items);
        drv.setAdapter(dynamicRVAdapter);

        dynamicRVAdapter.setLoadMore(new LoadMore() {
            @Override
            public void onLoadMore() {
                if(items.size()<=10){
                    item.add(null);
                    dynamicRVAdapter.notifyItemInserted(items.size()-1);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            items.remove(items.size()-1);
                            dynamicRVAdapter.notifyItemRemoved(items.size());

                            int index = items.size();
                            int end = index+10;
                            for(int i = index;i<end;i++){
                                String name = UUID.randomUUID().toString();
                                DynamicRVModel item =new DynamicRVModel(name);
                                items.add(item);
                            }
                            dynamicRVAdapter.notifyDataSetChanged();
                            dynamicRVAdapter.setLoded();
                        }
                    },4000);

                }else
                    Toast.makeText(Moments.this,"Data Completed",Toast.LENGTH_SHORT).show();
            }
        });


//navigation
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