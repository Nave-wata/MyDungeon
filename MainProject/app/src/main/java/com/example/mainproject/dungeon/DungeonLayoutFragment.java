package com.example.mainproject.dungeon;

import android.annotation.SuppressLint;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.mainproject.R;
import com.example.mainproject.SetImage;

import java.util.Objects;

public class DungeonLayoutFragment extends Fragment {
    final String EXTRA_DATA = "com.example.mainproject.dungeon";
    private String UserName;
    private androidx.constraintlayout.widget.ConstraintLayout topContainer;
    private ViewTreeObserver.OnGlobalLayoutListener globalLayoutListener;
    private int maxX, maxY;
    private int preDx, preDy;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        UserName = Objects.requireNonNull(args).getString(EXTRA_DATA);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ConstraintLayout layout = new ConstraintLayout(Objects.requireNonNull(getContext()));
        View view = inflater.inflate(R.layout.fragment_dungeonlayout, container, false);
        AssetManager assetManager = Objects.requireNonNull(getActivity()).getAssets();
        SetImage setImage = new SetImage(assetManager);
        ImageView imageView = new ImageView(getContext());
        topContainer = view.findViewById(R.id.fragment_dungeonLayout);
        Log.i("", "MainActivityFragment#onCreateView() " +
                "Width = " + String.valueOf(topContainer.getWidth()) + ", " +
                "Height = " + String.valueOf(topContainer.getHeight()));

        globalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Log.i("", "MainActivityFragment#onCreateView() " +
                        "Width = " + String.valueOf(topContainer.getWidth()) + ", " +
                        "Height = " + String.valueOf(topContainer.getHeight()));
                maxX = topContainer.getWidth();
                maxY = topContainer.getHeight();
                Log.v("", "maxX = " + maxX);
                topContainer.getViewTreeObserver().removeOnGlobalLayoutListener(globalLayoutListener);

                int oneSize = maxX / 20;
                Log.v("oneSize", ""+ oneSize);
                ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(oneSize, oneSize);
                imageView.setLayoutParams(layoutParams);
                setImage.setImageViewBitmapFromAsset(imageView, "dungeon/wall.png");
            }
        };
        topContainer.getViewTreeObserver().addOnGlobalLayoutListener(globalLayoutListener);

        layout.addView(view);
        layout.addView(imageView);
        return layout.getRootView();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView textView = view.findViewById(R.id.textView);
        textView.setOnTouchListener((view1, motionEvent) -> {
            int newDx = (int) (motionEvent.getRawX() / 50) * 50;
            int newDy = (int) (motionEvent.getRawY() / 50) * 50;

            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_MOVE:
                    view1.performClick();
                    int dx = textView.getLeft() + (newDx - preDx);
                    int dy = textView.getTop() + (newDy - preDy);
                    int imgW = dx + textView.getWidth();
                    int imgH = dy + textView.getHeight();
                    if (-50 <= dx && dx < (maxX - 100) && 0 <= dy && dy < (maxY - 50)) {
                        textView.layout(dx, dy, imgW, imgH);
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
    public static DungeonLayoutFragment newInstance(String str){
        DungeonLayoutFragment fragment = new DungeonLayoutFragment();
        Bundle barg = new Bundle();
        barg.putString(fragment.EXTRA_DATA, str);
        fragment.setArguments(barg);
        return fragment;
    }
}
