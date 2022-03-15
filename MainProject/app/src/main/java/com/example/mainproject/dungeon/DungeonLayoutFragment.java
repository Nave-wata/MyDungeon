package com.example.mainproject.dungeon;

import android.annotation.SuppressLint;
import android.content.res.AssetManager;
import android.os.Bundle;
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
    private SetImage setImage;
    private int preDx, preDy;
    private int oneSize;
    private int maxSize;
    private final int widthNum = 20;
    private final int heightNum = 20;
    private final ImageView[][] dungeonPeaces = new ImageView[widthNum][heightNum];
    public static boolean changeLayoutFlag = false;
    private boolean canMoveFlag = false;
    private int canMoveCount = 0;
    

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
        setImage = new SetImage(assetManager);

        for (int i = 0; i < widthNum; i++) {
            for (int j = 0; j < heightNum; j++) {
                dungeonPeaces[i][j] = new ImageView(getContext());
                setImage.setImageViewBitmapFromAsset(dungeonPeaces[i][j], "dungeon/wall.png");
            }
        }

        topContainer = view.findViewById(R.id.fragment_dungeonLayout);
        globalLayoutListener = () -> {
            int width = topContainer.getWidth();
            topContainer.getViewTreeObserver().removeOnGlobalLayoutListener(globalLayoutListener);
            oneSize = width / widthNum;
            maxSize = oneSize * widthNum;

            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(oneSize, oneSize);
            for (int i = 0; i < widthNum; i++) {
                for (int j = 0; j < heightNum; j++) {
                    dungeonPeaces[i][j].setLayoutParams(layoutParams);
                    dungeonPeaces[i][j].setX(oneSize * j);
                    dungeonPeaces[i][j].setY(oneSize * i);
                    dungeonPeaces[i][j].setOnTouchListener(new onTouchListener(i, j));
                }
            }
        };
        topContainer.getViewTreeObserver().addOnGlobalLayoutListener(globalLayoutListener);

        layout.addView(view);
        for (int i = 0; i < widthNum; i++) {
            for (int j = 0; j < heightNum; j++) {
                layout.addView(dungeonPeaces[i][j]);
            }
        }
        //layout.addView(dungeonPeaces[0][0]);
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
        final int i;
        final int j;
        final int minX;
        final int minY;

        public onTouchListener(int i, int j) {
            this.i = i;
            this.j = j;
            this.minX = oneSize * j;
            this.minY = oneSize * i;
        }

        @SuppressLint("ClickableViewAccessibility")
        @Override
        public boolean onTouch(View view, @NonNull MotionEvent motionEvent) {
            int newDx = (int) (motionEvent.getRawX() / oneSize) * oneSize;
            int newDy = (int) (motionEvent.getRawY() / oneSize) * oneSize;

            if (changeLayoutFlag) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        if (canMoveFlag) {
                            view.performClick();
                            int dx = dungeonPeaces[i][j].getLeft() + (newDx - preDx);
                            int dy = dungeonPeaces[i][j].getTop() + (newDy - preDy);
                            int imgW = dx + dungeonPeaces[i][j].getWidth();
                            int imgH = dy + dungeonPeaces[i][j].getHeight();
                            if (-minX <= dx && dx < maxSize - minX && -minY <= dy && dy < maxSize - minY) {
                                dungeonPeaces[i][j].layout(dx, dy, imgW, imgH);
                            }
                        }
                        break;
                    case MotionEvent.ACTION_DOWN: // 押されたとき
                        canMoveCount++;
                        if (canMoveCount == 2) {
                            canMoveFlag = true;
                            dungeonPeaces[i][j].setZ(1);
                            setImage.setImageViewBitmapFromAsset(dungeonPeaces[i][j], "dungeon/selectWall.png");
                        }
                        break;
                    case MotionEvent.ACTION_UP: // 離されたとき
                        if (canMoveCount == 2) {
                            canMoveCount = 0;
                            canMoveFlag = false;
                            dungeonPeaces[i][j].setZ(0);
                            setImage.setImageViewBitmapFromAsset(dungeonPeaces[i][j], "dungeon/wall.png");
                        }
                        break;
                }

                preDx = newDx;
                preDy = newDy;
            }
            return true;
        }
    }
}
