package com.example.mainproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class BaseStatusFragment extends Fragment {
    final String UserName;

    public BaseStatusFragment(String UserName) {
        this.UserName = UserName;
    }

    final String EXTRA_DATA = "com.example.mainproject";
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_basestatus, container, false);
    }

    @NonNull
    public static BaseStatusFragment newInstance(String UserName){
        BaseStatusFragment fragment = new BaseStatusFragment(UserName);
        Bundle barg = new Bundle();
        barg.putString(fragment.EXTRA_DATA, UserName);
        fragment.setArguments(barg);
        return fragment;
    }
}
