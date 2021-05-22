package com.example.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class SignupFragment extends Fragment {

    EditText email,username,passwd,confirm;
    Button signup;
    float v = 0;
    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container, @NonNull Bundle saved) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.signup_fragment, container, false);

        username = root.findViewById(R.id.username_signup);
        passwd = root.findViewById(R.id.password_signup);
        email = root.findViewById(R.id.email_signup);
        confirm = root.findViewById(R.id.confirmpwd_signup);
        signup = root.findViewById(R.id.btn_signup);

        email.setTranslationY(800);
        username.setTranslationY(800);
        passwd.setTranslationY(800);
        confirm.setTranslationY(800);

        email.setAlpha(v);
        username.setAlpha(v);
        passwd.setAlpha(v);
        confirm.setAlpha(v);

        email.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(300).start();
        passwd.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(500).start();
        confirm.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(500).start();
        username.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(700).start();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),MainActivity.class);
                startActivity(intent);
            }
        });
        return root;
    }
}
