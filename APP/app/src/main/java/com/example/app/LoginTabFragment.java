package com.example.app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.view.LayoutInflaterFactory;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

public class LoginTabFragment extends Fragment {
    public View onCreateView(@NonNull LayoutInflater inflater,@NonNull ViewGroup container,@NonNull Bundle saved) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.login_tab_fragment, container, false);
        return root;
    }

}
