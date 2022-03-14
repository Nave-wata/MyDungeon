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
    private int preDx, preDy;
    private int oneSize;
    private int maxSize;
    private final int widthNum = 20;
    private ImageView[] dungeonPeaces = new ImageView[widthNum * widthNum];

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        UserName = Objects.requireNonNull(args).getString(EXTRA_DATA);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ConstraintLayout layout = new ConstraintLayout(Objects.requireNonNull(getContext()));
        View view = inflater.inflate(R.layout.fragment_dungeonlayout, container, false);
        AssetManager assetManager = Objects.requireNonNull(getActivity()).getAssets();
        SetImage setImage = new SetImage(assetManager);

        for (int i = 0; i < widthNum; i++) {
            for (int j = 0; j < widthNum; j++) {
                Log.v("My", "" + (20 * i + j));
                dungeonPeaces[20 * i + j] = new ImageView(getContext());
                setImage.setImageViewBitmapFromAsset(dungeonPeaces[20 * i + j], "dungeon/wall.png");
            }
        }

        topContainer = view.findViewById(R.id.fragment_dungeonLayout);
        globalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int width = topContainer.getWidth();
                topContainer.getViewTreeObserver().removeOnGlobalLayoutListener(globalLayoutListener);
                oneSize = width / widthNum;
                maxSize = oneSize * widthNum;

                ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(oneSize, oneSize);
                for (int i = 0; i < widthNum; i++) {
                    for (int j = 0; j < widthNum; j++) {
                        dungeonPeaces[20 * i + j].setLayoutParams(layoutParams);
                        dungeonPeaces[20 * i + j].setX(oneSize * j);
                        dungeonPeaces[20 * i + j].setY(oneSize * i);
                        dungeonPeaces[20 * i + j].setOnTouchListener(new onTouchListener(dungeonPeaces[20 * i + j], oneSize * j, oneSize * i));
                    }
                }
            }
        };
        topContainer.getViewTreeObserver().addOnGlobalLayoutListener(globalLayoutListener);

        layout.addView(view);
        for (int i = 0; i < widthNum; i++) {
            for (int j = 0; j < widthNum; j++) {
                layout.addView(dungeonPeaces[20 * i + j]);
            }
        }
        return layout.getRootView();
    }

    @NonNull
    public static DungeonLayoutFragment newInstance(String str){
        DungeonLayoutFragment fragment = new DungeonLayoutFragment();
        Bundle barg = new Bundle();
        barg.putString(fragment.EXTRA_DATA, str);
        fragment.setArguments(barg);
        return fragment;
    }

    private class onTouchListener implements View.OnTouchListener {
        final ImageView wallImage;
        final int minX;
        final int minY;

        public onTouchListener(ImageView wallImage, int minX, int minY) {
            this.wallImage = wallImage;
            this.minX = minX;
            this.minY = minY;
        }

        @Override
        public boolean onTouch(View view, @NonNull MotionEvent motionEvent) {
            int newDx = (int) (motionEvent.getRawX() / oneSize) * oneSize;
            int newDy = (int) (motionEvent.getRawY() / oneSize) * oneSize;

            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_MOVE:
                    view.performClick();
                    int dx = wallImage.getLeft() + (newDx - preDx); // ここの0はsetXでかわってまう
                    int dy = wallImage.getTop() + (newDy - preDy);  // ここの0はsetYでかわってまう
                    int imgW = dx + wallImage.getWidth();
                    int imgH = dy + wallImage.getHeight();
                    if (-minX <= dx && dx < maxSize + minX && -minY <= dy && dy < maxSize + minY) {
                        wallImage.layout(dx, dy, imgW, imgH);
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
        }
    }
}
