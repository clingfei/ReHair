package com.example.app;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Mypost extends AppCompatActivity {

    private ScrollView mScro;
    private LinearLayout mLinear;
    private TextView mTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypost);
        mScro = (ScrollView)findViewById(R.id.mScro);
        mLinear = (LinearLayout)findViewById(R.id.mLinear);
        mTv = (TextView)findViewById(R.id.mTv);
    }
    public void onClick(View view){
        switch (view.getId()){
            case R.id.mTv:
                int mScroHeight = mScro.getHeight();
                int mScroWidth = mScro.getWidth();
                mTv.setText("高度为:" + mScroHeight + "宽度为:" + mScroWidth);
                break;
            case R.id.mLinear:
//                mScro.scrollTo(0,1000);//滚动时候是瞬间滚动过去
//                mScro.scrollBy(0,1000);//滚动时候是瞬间的，设置偏移量，在当前的位置基础上偏移设置的数值
//                mScro.smoothScrollTo(0,1000);//滚动时候是平缓的而不是立即滚动到某处
//                mScro.smoothScrollBy(0,100);//滚动时候是平缓,在当前的位置基础上偏移设置的数值
                break;
        }

    }
}