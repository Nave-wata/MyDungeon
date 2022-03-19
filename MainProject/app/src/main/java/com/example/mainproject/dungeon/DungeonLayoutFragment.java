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
    @SuppressLint("StaticFieldLeak")
    private static ImageView dungeonPeace;
    private static int dungeonPeaceType;
    private static SetImage setImage;
    private int preDx, preDy;
    private int oneSize;
    private int maxSize;
    private static final int widthNum = 20;
    private static final int heightNum = 20;
    public static final ImageView[][] dungeonPeaces = new ImageView[widthNum][heightNum];
    public static final int[][] dungeonInfo = new int[widthNum][heightNum]; // 0->dungeon.nothing, 1->notDungeon.wall, 2->dungeon.wall, 3->dungeon.trap1
    public static boolean changeLayoutFlag = false;
    public static boolean moveLayoutFlag = false;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        UserName = Objects.requireNonNull(args).getString(EXTRA_DATA);
        Log.v("MY", "" + DungeonFragment.DUNGEON_NOTHING);
        Log.v("MY", "" + DungeonFragment.NOT_DUNGEON_WALL);
        Log.v("MY", "" + DungeonFragment.DUNGEON_WALL);
        Log.v("MY", "" + DungeonFragment.DUNGEON_TRAP1);

        for (int i = 0; i < widthNum; i++) {
            for (int j = 0; j < heightNum; j++) {
                if (i == 1 || i == 2 || i == 3 || i == 4) {
                    if (j == 8 || j == 9 || j == 10 || j == 11) {
                        dungeonInfo[i][j] = DungeonFragment.DUNGEON_NOTHING;
                    } else {
                        dungeonInfo[i][j] = DungeonFragment.NOT_DUNGEON_WALL;
                    }
                } else if (j == 9 || j == 10) {
                    dungeonInfo[i][j] = DungeonFragment.DUNGEON_NOTHING;
                } else {
                    dungeonInfo[i][j] = DungeonFragment.NOT_DUNGEON_WALL;
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

        for (int i = 0; i < widthNum; i++) {
            for (int j = 0; j < heightNum; j++) {
                dungeonPeaces[i][j] = new ImageView(getContext());
                if (dungeonInfo[i][j] == DungeonFragment.NOT_DUNGEON_WALL) {
                    setImage.setImageViewBitmapFromAsset(dungeonPeaces[i][j], "dungeon/wall.png");
                }
            }
        }
        dungeonPeace  = new ImageView(getContext());

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
                }
            }

            dungeonPeace.setLayoutParams(layoutParams);
            dungeonPeace.setX((int) (maxSize / 2));
            dungeonPeace.setY((int) (maxSize / 2));
            dungeonPeace.setOnTouchListener(new SetViewOnTouchListener(dungeonPeace, maxSize / 2, maxSize / 2));
        };
        topContainer.getViewTreeObserver().addOnGlobalLayoutListener(globalLayoutListener);

        layout.addView(view);
        for (int i = 0; i < widthNum; i++) {
            for (int j = 0; j < heightNum; j++) {
                dungeonPeaces[i][j].setOnTouchListener(new onTouchListener(i, j));
                layout.addView(dungeonPeaces[i][j]);
            }
        }
        layout.addView(dungeonPeace);
        return layout.getRootView();
    }

    @SuppressLint("ClickableViewAccessibility")
    public void setDungeonPeace(int op) { // 引数でImage指定すればいい
        dungeonPeaceType = op;
        switch (op) {
            case DungeonFragment.DUNGEON_WALL:
                setImage.setImageViewBitmapFromAsset(dungeonPeace, "dungeon/dungeonWall.png");
                break;
            case DungeonFragment.DUNGEON_TRAP1:
                setImage.setImageViewBitmapFromAsset(dungeonPeace, "dungeon/dungeonTrap1.png");
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    public void setDungeonPeacesOnTouchListener() {
        for (int i = 0; i < widthNum; i++) {
            for (int j = 0; j < heightNum; j++) {
                dungeonPeaces[i][j].setOnTouchListener(new onTouchListener(i, j)); } }
    }

    @SuppressLint("ClickableViewAccessibility")
    public void resetDungeonPeacesOnTouchListener() {
        for (int i = 0; i < widthNum; i++) {
            for (int j = 0; j < heightNum; j++) {
                dungeonPeaces[i][j].setOnTouchListener(null); } }
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
            if (changeLayoutFlag) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        break;
                    case MotionEvent.ACTION_DOWN: // 押されたとき
                        break;
                    case MotionEvent.ACTION_UP: // 離されたとき
                        if (changeLayoutFlag) {
                            switch (dungeonInfo[i][j]) {
                                case DungeonFragment.NOT_DUNGEON_WALL:
                                    setConfirmDPCostDialog(dungeonPeaces[i][j], "deleteWall", i, j);
                                    break;
                                case DungeonFragment.DUNGEON_WALL:
                                    setConfirmDPCostDialog(dungeonPeaces[i][j], "deleteDungeonWall", i, j);
                                    break;
                                case DungeonFragment.DUNGEON_TRAP1:
                                    setConfirmDPCostDialog(dungeonPeaces[i][j], "deleteDungeonTrap1", i, j);
                                    break;
                            }
                        }
                        break;
                }
            }
            return true;
        }

        private void setConfirmDPCostDialog(ImageView dungeonPeace, String text, int i, int j) {
            ConfirmDPCostDialog confirmDPCostDialog = new ConfirmDPCostDialog(
                    text,
                    n -> {
                        if (n == 0) {
                            setImage.setImageViewBitmapFromAsset(dungeonPeace, "");
                            DungeonLayoutFragment.dungeonInfo[i][j] = 0;
                        }
                    });
            confirmDPCostDialog.show(Objects.requireNonNull(getFragmentManager()), text);
        }
    }


    private class SetViewOnTouchListener implements View.OnTouchListener {
        final ImageView wallImage;
        final int setX;
        final int setY;
        int dx;
        int dy;

        public SetViewOnTouchListener(ImageView wallImage, int setX, int setY) {
            this.wallImage = wallImage;
            this.setX = setX;
            this.setY = setY;
        }

        @Override
        public boolean onTouch(View view, @NonNull MotionEvent motionEvent) {
            if (!moveLayoutFlag) { return true; }

            int newDx = (int) (motionEvent.getRawX() / oneSize) * oneSize;
            int newDy = (int) (motionEvent.getRawY() / oneSize) * oneSize;
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_MOVE:
                    view.performClick();
                    dx = wallImage.getLeft() + (newDx - preDx);
                    dy = wallImage.getTop() + (newDy - preDy);
                    int imgW = dx + wallImage.getWidth();
                    int imgH = dy + wallImage.getHeight();
                    if (-setX <= dx && dx < maxSize - setX && -setY <= dy && dy < maxSize - setY) {
                        wallImage.layout(dx, dy, imgW, imgH);
                    }
                    break;
                case MotionEvent.ACTION_DOWN:
                    // nothing to do
                    break;
                case MotionEvent.ACTION_UP:
                    int j = (setX + dx) / oneSize;
                    int i = (setY + dy) / oneSize;
                    if (dungeonInfo[i][j] == DungeonFragment.DUNGEON_NOTHING) {
                        SetDungeonPeaceDialog setDungeonPeaceDialog = new SetDungeonPeaceDialog(b->setDungeonLayout(b, i, j));
                        setDungeonPeaceDialog.show(Objects.requireNonNull(getFragmentManager()), "SetDungeonWallDialog");
                    }
                    break;
            }

            preDx = newDx;
            preDy = newDy;
            return true;
        }

        public void setDungeonLayout(boolean b, int i, int j) {
            if (b) {
                setImage.setImageViewBitmapFromAsset(dungeonPeace, "");
                setDungeonPeacesOnTouchListener();

                if (dungeonPeaceType == DungeonFragment.DUNGEON_WALL) {
                    setImage.setImageViewBitmapFromAsset(dungeonPeaces[i][j], "dungeon/dungeonWall.png");
                    dungeonInfo[i][j] = DungeonFragment.DUNGEON_WALL;
                } else if (dungeonPeaceType == DungeonFragment.DUNGEON_TRAP1) {
                    setImage.setImageViewBitmapFromAsset(dungeonPeaces[i][j], "dungeon/dungeonTrap1.png");
                    dungeonInfo[i][j] = DungeonFragment.DUNGEON_TRAP1;
                }

            } else {
                setImage.setImageViewBitmapFromAsset(dungeonPeace, "");
                setDungeonPeacesOnTouchListener();
            }
            moveLayoutFlag = false;
        }
    }
}
