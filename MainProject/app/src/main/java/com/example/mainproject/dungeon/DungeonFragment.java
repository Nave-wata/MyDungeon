package com.example.mainproject.dungeon;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.mainproject.R;

import java.util.Objects;

public class DungeonFragment extends Fragment {
    final String EXTRA_DATA = "com.example.mainproject.dungeon";
    private String UserName;
    private int fragment_w;
    private int fragment_h;
    private int preDx, preDy;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        UserName = Objects.requireNonNull(args).getString(EXTRA_DATA);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dungeon, container, false);
    }

    @SuppressLint({"ClickableViewAccessibility", "Range"})
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button createFlorButton = view.findViewById(R.id.createFlorButton);
        createFlorButton.setOnTouchListener((view1, motionEvent) -> {
            int newDx = (int) (motionEvent.getRawX() / 100) * 100;
            int newDy = (int) (motionEvent.getRawY() / 100) * 100;

            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_MOVE:
                    view1.performClick();
                    int dx = createFlorButton.getLeft() + (newDx - preDx);
                    int dy = createFlorButton.getTop() + (newDy - preDy);
                    int imgW = dx + createFlorButton.getWidth();
                    int imgH = dy + createFlorButton.getHeight();
                    if (500 > dx) {
                        createFlorButton.layout(dx, dy, imgW, imgH);
                    }
                    break;
                case MotionEvent.ACTION_DOWN:
                    // nothing to do
                    break;
                case MotionEvent.ACTION_UP:
                    // nothing to do
                    break;
            }
            preDx = newDx;
            preDy = newDy;

            return true;
        });
    }

    @NonNull
    public static DungeonFragment newInstance(String str){
        DungeonFragment fragment = new DungeonFragment();
        Bundle barg = new Bundle();
        barg.putString(fragment.EXTRA_DATA, str);
        fragment.setArguments(barg);
        return fragment;
    }
}