package com.example.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AccountinActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_zhihu)
    Toolbar mToolbar;
    @BindView(R.id.viewPager_zhihu)
    FragmentViewPager mViewpager;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.tab_layout_copy)
    TabLayout mTabLayoutCopy;
    @BindView(R.id.nestedScrollView)
    NestedScrollView mScrollView;
    @BindView(R.id.viewPager_layout)
    RelativeLayout mRelativeLayout;

    private String[] titles = {"Moments", "Notice"};
    private List<Fragment> fragmentList;
    private int width, height;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.TranslucentStatusBar(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initViews();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("ResourceAsColor")
    private void initViews() {
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);

        width = wm.getDefaultDisplay().getWidth();
        height = wm.getDefaultDisplay().getHeight();

        mToolbar.setTitle("");
        mToolbar.setNavigationIcon(R.drawable.ic_back);
        mToolbar.setTitle("Ninoric");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mTabLayoutCopy.setVisibility(View.GONE);

        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout)
                findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);

        mTabLayout.setupWithViewPager(mViewpager);
        mTabLayoutCopy.setupWithViewPager(mViewpager);

        fragmentList = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            ListFragment fragment = new ListFragment();
            fragmentList.add(fragment);

        }
        ZhiHuFragmentAdapter adapter = new ZhiHuFragmentAdapter(
                getSupportFragmentManager(), fragmentList, titles);

        mViewpager.setAdapter(adapter);

        mScrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View view, int i, int i1, int i2, int i3) {

                int[] location = new int[2];
                mTabLayout.getLocationInWindow(location);
                mTabLayout.getLocationOnScreen(location);

                if (location[1] < Utils.dp2px(AccountinActivity.this, 88)) {
                    mTabLayoutCopy.setVisibility(View.VISIBLE);
                    mTabLayout.setVisibility(View.INVISIBLE);
                } else {
                    mTabLayoutCopy.setVisibility(View.GONE);
                    mTabLayout.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        mViewpager.setFocusable(false);
        mRelativeLayout.setFocusable(true);
        mRelativeLayout.setFocusableInTouchMode(true);
        mRelativeLayout.requestFocus();
    }
}
