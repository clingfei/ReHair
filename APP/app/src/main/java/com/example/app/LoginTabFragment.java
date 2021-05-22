package com.example.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.core.view.LayoutInflaterFactory;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

public class LoginTabFragment extends Fragment {
    EditText username,passwd;
    Button login;
    float v=0;
    public View onCreateView(@NonNull LayoutInflater inflater,@NonNull ViewGroup container,@NonNull Bundle saved) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.login_tab_fragment, container, false);

        username = root.findViewById(R.id.username);
        passwd = root.findViewById(R.id.password);
        login = root.findViewById(R.id.btn_login);

        login.setTranslationY(800);
        username.setTranslationY(800);
        passwd.setTranslationY(800);

        login.setAlpha(v);
        username.setAlpha(v);
        passwd.setAlpha(v);

        login.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(300).start();
        passwd.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(500).start();
        username.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(500).start();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),NavigationActivity.class);
                startActivity(intent);
            }
        });
        return root;
    }

}
