package com.example.mainproject.dungeon;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.mainproject.R;

public class DisplayFloorFragment extends Fragment {
    final String EXTRA_DATA = "com.example.mainproject.dungeon";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_displayfloor, container, false);
    }

    @NonNull
    public static DisplayFloorFragment newInstance(String str) {
        DisplayFloorFragment fragment = new DisplayFloorFragment();
        Bundle barg = new Bundle();
        barg.putString(fragment.EXTRA_DATA, str);
        fragment.setArguments(barg);
        return fragment;
    }
}
