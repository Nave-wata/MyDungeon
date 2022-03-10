package com.example.mainproject.dungeon;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.mainproject.R;

public class DungeonLayoutFragment extends Fragment {
    final String EXTRA_DATA = "com.example.mainproject.dungeon";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dungeonlayout, container, false);
    }

    @NonNull
    public static DungeonLayoutFragment newInstance(String str){
        DungeonLayoutFragment fragment = new DungeonLayoutFragment();
        Bundle barg = new Bundle();
        barg.putString(fragment.EXTRA_DATA, str);
        fragment.setArguments(barg);
        return fragment;
    }
}
