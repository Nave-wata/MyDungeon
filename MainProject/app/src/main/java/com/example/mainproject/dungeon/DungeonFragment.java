package com.example.mainproject.dungeon;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.mainproject.R;

import java.util.Objects;

public class DungeonFragment extends Fragment {
    final String EXTRA_DATA = "com.example.mainproject.dungeon";
    private String UserName;
    private androidx.constraintlayout.widget.ConstraintLayout topContainer;
    private ViewTreeObserver.OnGlobalLayoutListener globalLayoutListener;
    private int X, Y;
    private int preDx, preDy;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        UserName = Objects.requireNonNull(args).getString(EXTRA_DATA);

        FragmentTransaction fragmentTransaction = Objects.requireNonNull(getFragmentManager()).beginTransaction();
        fragmentTransaction.replace(R.id.DungeonLayoutContainer, DungeonLayoutFragment.newInstance(UserName));
        fragmentTransaction.commit();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dungeon, container, false);
        topContainer = view.findViewById(R.id.fragment_dungeon);
        Log.i("", "MainActivityFragment#onCreateView() " +
                "Width = " + String.valueOf(topContainer.getWidth()) + ", " +
                "Height = " + String.valueOf(topContainer.getHeight()));

        globalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Log.i("", "MainActivityFragment#onCreateView() " +
                        "Width = " + String.valueOf(topContainer.getWidth()) + ", " +
                        "Height = " + String.valueOf(topContainer.getHeight()));
                X = topContainer.getWidth();
                Y = topContainer.getHeight();
                topContainer.getViewTreeObserver().removeOnGlobalLayoutListener(globalLayoutListener);
            }
        };
        topContainer.getViewTreeObserver().addOnGlobalLayoutListener(globalLayoutListener);

        return view;
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
                    if (0 <= dx && dx < (X - 100) && 0 <= dy && dy < (Y - 100)) {
                        createFlorButton.layout(dx, dy, imgW, imgH);
                    }
                    break;
                case MotionEvent.ACTION_DOWN:
                    Log.v("", ""+ createFlorButton.getLeft());
                    Log.v("", "" + createFlorButton.getTop());
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