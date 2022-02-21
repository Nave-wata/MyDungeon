package com.example.fragmenttest2.title;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.fragmenttest2.R;

public class UserRegistrationFragment extends Fragment {
    public final String ARGS_NAME = "com.example.fragmenttest2.title";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_userregistration, container, false);
    }

    public static UserRegistrationFragment newInstance(String str){
        UserRegistrationFragment fragment = new UserRegistrationFragment();
        Bundle barg = new Bundle();
        barg.putString(fragment.ARGS_NAME, str);
        fragment.setArguments(barg);
        return fragment;
    }
}
