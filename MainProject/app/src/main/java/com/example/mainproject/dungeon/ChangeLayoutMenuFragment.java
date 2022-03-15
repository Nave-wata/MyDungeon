package com.example.mainproject.dungeon;

import android.annotation.SuppressLint;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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

    @SuppressLint({"Range", "ClickableViewAccessibility"})
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_changelayoutmenu, container, false);
        AssetManager assetManager = Objects.requireNonNull(getActivity()).getAssets();
        SetImage setImage = new SetImage(assetManager);

        ImageView dungeonWall = view.findViewById(R.id.dungeonLayout);
        dungeonWall.setOnTouchListener(new onTouchListener());
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

    private static class onTouchListener implements View.OnTouchListener {

        @SuppressLint("ClickableViewAccessibility")
        @Override
        public boolean onTouch(View view, @NonNull MotionEvent motionEvent) {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_MOVE:
                    break;
                case MotionEvent.ACTION_DOWN: // 押されたとき
                    break;
                case MotionEvent.ACTION_UP: // 離されたとき
                    new DungeonLayoutFragment().setDungeonPeace();
                    break;
            }
            return true;
        }
    }
}
