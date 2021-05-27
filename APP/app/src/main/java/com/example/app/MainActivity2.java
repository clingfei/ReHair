package com.example.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {


    private ListView lv_show;
    private List<ShowImage> imageList = new ArrayList<>();
    private ShowImageAdapter showImageAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private String[] shownum = {
            "No.1","No.2","No.3","No.4","No.5","No.6","No.7","No.8"
    };
    private int[] showimageid = {
            R.drawable.no1, R.drawable.no2, R.drawable.no3, R.drawable.no4, R.drawable.no5, R.drawable.no6, R.drawable.no7, R.drawable.no8
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initShow(shownum,showimageid);

        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.hide();
        }
        recyclerView=(RecyclerView) findViewById(R.id.recycler_view);
         linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
         showImageAdapter = new ShowImageAdapter(imageList);
        recyclerView.setAdapter(showImageAdapter);

    }
    private void initShow(String[] num, int[] images){
        ShowImage showImage;
        for(int i=0; i<num.length; i++){
            showImage = new ShowImage(num[i],images[i]);
            imageList.add(showImage);
        }

    }


}