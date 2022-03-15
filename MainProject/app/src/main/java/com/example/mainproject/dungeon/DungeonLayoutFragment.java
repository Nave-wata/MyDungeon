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
    private static SetImage setImage;
    private static ImageView dungeonPeace;
    private int preDx, preDy;
    private int oneSize;
    private int maxSize;
    private static final int widthNum = 20;
    private static final int heightNum = 20;
    public static final ImageView[][] dungeonPeaces = new ImageView[widthNum][heightNum];
    public static final int[][] dungeonInfo = new int[widthNum][heightNum];
    public static boolean changeLayoutFlag = false;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        UserName = Objects.requireNonNull(args).getString(EXTRA_DATA);

        for (int i = 0; i < widthNum; i++) {
            for (int j = 0; j < heightNum; j++) {
                if (i == 1 || i == 2 || i == 3 || i == 4) {
                    if (j == 8 || j == 9 || j == 10 || j == 11) {
                        dungeonInfo[i][j] = 0;
                    } else {
                        dungeonInfo[i][j] = 1;
                    }
                } else if (j == 9 || j == 10) {
                    dungeonInfo[i][j] = 0;
                } else {
                    dungeonInfo[i][j] = 1;
                }
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ConstraintLayout layout = new ConstraintLayout(Objects.requireNonNull(getContext()));
        View view = inflater.inflate(R.layout.fragment_dungeonlayout, container, false);
        AssetManager assetManager = Objects.requireNonNull(getActivity()).getAssets();
        setImage = new SetImage(assetManager);

        dungeonPeace = view.findViewById(R.id.dungeonPeace);
        setImage.setImageViewBitmapFromAsset(dungeonPeace, "dungeon/dungeonWall.png");
        dungeonPeace.setOnTouchListener(new SetViewOnTouchListener(dungeonPeace));

        for (int i = 0; i < widthNum; i++) {
            for (int j = 0; j < heightNum; j++) {
                dungeonPeaces[i][j] = new ImageView(getContext());
                if (dungeonInfo[i][j] == 1) {
                    setImage.setImageViewBitmapFromAsset(dungeonPeaces[i][j], "dungeon/wall.png");
                }
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
        return layout.getRootView();
    }

    @SuppressLint("ClickableViewAccessibility")
    public void setDungeonPeace() { // 引数でImage指定すればいい
        setImage.setImageViewBitmapFromAsset(dungeonPeace, "dungeon/dungeonWall.png");
        dungeonPeace.setOnTouchListener(new SetViewOnTouchListener(dungeonPeace));
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

        public onTouchListener(int i, int j) {
            this.i = i;
            this.j = j;
        }

        @SuppressLint("ClickableViewAccessibility")
        @Override
        public boolean onTouch(View view, @NonNull MotionEvent motionEvent) {
            Log.v("My", "ABC");
            if (changeLayoutFlag) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        break;
                    case MotionEvent.ACTION_DOWN: // 押されたとき
                        break;
                    case MotionEvent.ACTION_UP: // 離されたとき
                        if (changeLayoutFlag) {
                            if (dungeonInfo[i][j] == 1) {
                                DeleteWallDialog deleteWallDialog = new DeleteWallDialog(i, j);
                                deleteWallDialog.show(Objects.requireNonNull(getFragmentManager()), "DeleteWallDialog");
                            }
                        }
                        break;
                }
            }
            return true;
        }
    }


    public class SetViewOnTouchListener implements View.OnTouchListener {
        final ImageView wallImage;

        public SetViewOnTouchListener(ImageView wallImage) {
            this.wallImage = wallImage;
            Log.v("My", "!!!!");
        }

        @Override
        public boolean onTouch(View view, @NonNull MotionEvent motionEvent) {
            int newDx = (int) (motionEvent.getRawX() / oneSize) * oneSize;
            int newDy = (int) (motionEvent.getRawY() / oneSize) * oneSize;
            Log.v("My", "ABCD");
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_MOVE:
                    Log.v("My", "~~~~");
                    view.performClick();
                    int dx = wallImage.getLeft() + (newDx - preDx); // ここの0はsetXでかわってまう
                    int dy = wallImage.getTop() + (newDy - preDy);  // ここの0はsetYでかわってまう
                    int imgW = dx + wallImage.getWidth();
                    int imgH = dy + wallImage.getHeight();
                    if (0 <= dx && dx < maxSize && 0 <= dy && dy < maxSize) {
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
