package com.example.app;

import android.os.Bundle;
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
            R.drawable.boy1, R.drawable.boy2, R.drawable.boy3, R.drawable.boy4, R.drawable.girl1, R.drawable.girl2, R.drawable.girl3, R.drawable.girl4
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