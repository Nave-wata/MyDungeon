package com.example.mainproject.dungeon;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.mainproject.R;
import com.example.mainproject.SetImage;

import java.util.Objects;

public class ChangeLayoutMenuFragment extends Fragment {
    final String EXTRA_DATA = "com.example.mainproject.dungeon";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_changelayoutmenu, container, false);
        AssetManager assetManager = Objects.requireNonNull(getActivity()).getAssets();
        SetImage setImage = new SetImage(assetManager);

        ImageView dungeonWall = view.findViewById(R.id.dungeonLayout);
        dungeonWall.setOnTouchListener(new DungeonFragment.onTouchListener(dungeonWall));
        setImage.setImageViewBitmapFromAsset(dungeonWall, "dungeon/dungeonWall.png");

        return view;
    }

    @NonNull
    public static ChangeLayoutMenuFragment newInstance(String str){
        ChangeLayoutMenuFragment fragment = new ChangeLayoutMenuFragment();
        Bundle barg = new Bundle();
        barg.putString(fragment.EXTRA_DATA, str);
        fragment.setArguments(barg);
        return fragment;
    }
}
