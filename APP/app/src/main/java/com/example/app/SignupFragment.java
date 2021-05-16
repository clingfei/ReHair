package com.example.app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class SignupFragment extends Fragment {
    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container, @NonNull Bundle saved) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.signup_fragment, container, false);
        return root;
    }
}
