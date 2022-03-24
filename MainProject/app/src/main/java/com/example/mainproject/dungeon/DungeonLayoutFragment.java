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
import com.example.mainproject.asynchronous.AppDatabase;
import com.example.mainproject.asynchronous.AppDatabaseSingleton;
import com.example.mainproject.asynchronous.dungeonlayout.DungeonLayout;
import com.example.mainproject.asynchronous.dungeonlayout.GetDungeonLayout;

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
    private int count = 0;
    public static final int widthNum = 20;
    public static final int heightNum = 20;
    public static final ImageView[][] dungeonPeaces = new ImageView[widthNum][heightNum];
    public static final int[][] dungeonInfo = new int[widthNum][heightNum];
    public static boolean changeLayoutFlag = false;
    public static boolean moveLayoutFlag = false;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        UserName = Objects.requireNonNull(args).getString(EXTRA_DATA);
    }

    @SuppressLint("ClickableViewAccessibility")
    @SuppressWarnings("ALL")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ConstraintLayout layout = new ConstraintLayout(Objects.requireNonNull(getContext()));
        View view = inflater.inflate(R.layout.fragment_dungeonlayout, container, false);
        AssetManager assetManager = Objects.requireNonNull(getActivity()).getAssets();
        setImage = new SetImage(assetManager);
        final AppDatabase db = AppDatabaseSingleton.getInstance(Objects.requireNonNull(getActivity()).getApplicationContext());

        dungeonPeace  = new ImageView(getContext());
        for (int i = 0; i < widthNum; i++ ) {
            for (int j = 0; j < heightNum; j++) {
                dungeonPeaces[i][j] = new ImageView(getContext());
            }
        }

        topContainer = view.findViewById(R.id.fragment_dungeonLayout);
        globalLayoutListener = () -> {
            int width = topContainer.getWidth();
            topContainer.getViewTreeObserver().removeOnGlobalLayoutListener(globalLayoutListener);
            oneSize = width / widthNum;
            maxSize = oneSize * widthNum;
            Log.v("My oneSize", "" + oneSize);
            Log.v("My maxSize", "" + maxSize);

            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(oneSize, oneSize);
            for (int i = 0; i < widthNum; i++) {
                for (int j = 0; j < heightNum; j++) {
                    dungeonPeaces[i][j].setLayoutParams(layoutParams);
                    dungeonPeaces[i][j].setX(oneSize * j);
                    dungeonPeaces[i][j].setY(oneSize * i);
                    setImage.setImageViewBitmapFromAsset(dungeonPeaces[i][j], "");
                }
            }

            dungeonPeace.setLayoutParams(layoutParams);
            dungeonPeace.setX(maxSize / 2);
            dungeonPeace.setY((int) (maxSize / 2));
            dungeonPeace.setOnTouchListener(new SetViewOnTouchListener(dungeonPeace, maxSize / 2, maxSize / 2));
        };
        topContainer.getViewTreeObserver().addOnGlobalLayoutListener(globalLayoutListener);

        new GetDungeonLayout(
                db,
                UserName,
                b->{
                    String[] dungeonLayouts = new String[widthNum * heightNum];
                    for (DungeonLayout dl: b) {
                        dungeonLayouts[0] = dl.getRow0();
                        dungeonLayouts[1] = dl.getRow1();
                        dungeonLayouts[2] = dl.getRow2();
                        dungeonLayouts[3] = dl.getRow3();
                        dungeonLayouts[4] = dl.getRow4();
                        dungeonLayouts[5] = dl.getRow5();
                        dungeonLayouts[6] = dl.getRow6();
                        dungeonLayouts[7] = dl.getRow7();
                        dungeonLayouts[8] = dl.getRow8();
                        dungeonLayouts[9] = dl.getRow9();
                        dungeonLayouts[10] = dl.getRow10();
                        dungeonLayouts[11] = dl.getRow11();
                        dungeonLayouts[12] = dl.getRow12();
                        dungeonLayouts[13] = dl.getRow13();
                        dungeonLayouts[14] = dl.getRow14();
                        dungeonLayouts[15] = dl.getRow15();
                        dungeonLayouts[16] = dl.getRow16();
                        dungeonLayouts[17] = dl.getRow17();
                        dungeonLayouts[18] = dl.getRow18();
                        dungeonLayouts[19] = dl.getRow19();
                    }

                    for (int i = 0; i < heightNum; i++) {
                        String[] strSplit = dungeonLayouts[i].split(",");
                        for (int j = 0; j < widthNum; j++) {
                            dungeonInfo[i][j] = Integer.parseInt(strSplit[j]);
                        }
                    }

                    for (int i = 0; i < widthNum; i++) {
                        for (int j = 0; j < heightNum; j++) {
                            if (dungeonInfo[i][j] == DungeonFragment.NOT_DUNGEON_WALL) {
                                setImage.setImageViewBitmapFromAsset(dungeonPeaces[i][j], "dungeon/wall.png");
                            } else if (dungeonInfo[i][j] == DungeonFragment.DUNGEON_I_DOOR) {
                                setImage.setImageViewBitmapFromAsset(dungeonPeaces[i][j], "dungeon/dungeon_I_Door.png");
                            } else if (dungeonInfo[i][j] == DungeonFragment.DUNGEON_O_DOOR) {
                                setImage.setImageViewBitmapFromAsset(dungeonPeaces[i][j], "dungeon/dungeon_O_Door.png");
                            } else if (dungeonInfo[i][j] == DungeonFragment.DUNGEON_DOOR) {
                                setImage.setImageViewBitmapFromAsset(dungeonPeaces[i][j], "dungeon/door.png");
                            } else if (dungeonInfo[i][j] == DungeonFragment.DUNGEON_WALL) {
                                setImage.setImageViewBitmapFromAsset(dungeonPeaces[i][j], "dungeon/dungeonWall.png");
                            } else if (dungeonInfo[i][j] == DungeonFragment.DUNGEON_TRAP1) {
                                setImage.setImageViewBitmapFromAsset(dungeonPeaces[i][j], "dungeon/dungeonTrap1.png");
                            }
                        }
                    }
                },
                e->{
                    Log.v("DungeonLayoutFragment[Exception]", "" + e);
                }
        ).execute();

        layout.addView(view);
        for (int i = 0; i < heightNum; i++) {
            for (int j = 0; j < widthNum; j++) {
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
                break;
            case DungeonFragment.DUNGEON_DOOR:
                setImage.setImageViewBitmapFromAsset(dungeonPeace, "dungeon/door.png");
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    public void setDungeonPeacesOnTouchListener() {
        for (int i = 0; i < heightNum; i++) {
            for (int j = 0; j < widthNum; j++) {
                dungeonPeaces[i][j].setOnTouchListener(new onTouchListener(i, j)); } }
    }

    @SuppressLint("ClickableViewAccessibility")
    public void resetDungeonPeacesOnTouchListener() {
        for (int i = 0; i < heightNum; i++) {
            for (int j = 0; j < widthNum; j++) {
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
                                    ShowConfirmDPCostDialog(dungeonPeaces[i][j], "deleteWall", i, j);
                                    break;
                                case DungeonFragment.DUNGEON_WALL:
                                    ShowConfirmDPCostDialog(dungeonPeaces[i][j], "deleteDungeonWall", i, j);
                                    break;
                                case DungeonFragment.DUNGEON_TRAP1:
                                    ShowConfirmDPCostDialog(dungeonPeaces[i][j], "deleteDungeonTrap1", i, j);
                                    break;
                                case DungeonFragment.DUNGEON_DOOR:
                                    ShowConfirmDPCostDialog(dungeonPeaces[i][j], "deleteDungeonDoor", i, j);
                            }
                        }
                        break;
                }
            }
            return true;
        }

        private void ShowConfirmDPCostDialog(ImageView dungeonPeace, String text, int i, int j) {
            ConfirmDPCostDialog confirmDPCostDialog = new ConfirmDPCostDialog(
                    text,
                    n -> {
                        if (n == ConfirmDPCostDialog.POSITIVE_BUTTON) {
                            setImage.setImageViewBitmapFromAsset(dungeonPeace, "");
                            DungeonLayoutFragment.dungeonInfo[i][j] = DungeonFragment.DUNGEON_NOTHING;
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
                    count++;
                    if (5 <= count) {
                        count = 0;
                        setImage.setImageViewBitmapFromAsset(dungeonPeace, "");
                        setDungeonPeacesOnTouchListener();
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    int j = (setX + dx) / oneSize;
                    int i = (setY + dy) / oneSize;
                    if (dungeonInfo[i][j] == DungeonFragment.DUNGEON_NOTHING) {

                        switch (dungeonPeaceType) {
                            case DungeonFragment.DUNGEON_WALL:
                                ShowSetDungeonPeaceDialog(i, j, "setDungeonWall");
                                break;
                            case DungeonFragment.DUNGEON_TRAP1:
                                ShowSetDungeonPeaceDialog(i, j, "setDungeonTrap1");
                                break;
                            case DungeonFragment.DUNGEON_DOOR:
                                ShowSetDungeonPeaceDialog(i, j, "setDungeonDoor");
                        }

                    }
                    break;
            }

            preDx = newDx;
            preDy = newDy;
            return true;
        }

        public void ShowSetDungeonPeaceDialog(int i, int j, String text) {
            SetDungeonPeaceDialog setDungeonPeaceDialog = new SetDungeonPeaceDialog(text, n->setDungeonLayout(n, i, j));
            setDungeonPeaceDialog.show(Objects.requireNonNull(getFragmentManager()), text);
        }

        public void setDungeonLayout(int n, int i, int j) {
            if (n == SetDungeonPeaceDialog.POSITIVE_BUTTON) {
                setImage.setImageViewBitmapFromAsset(dungeonPeace, "");
                setDungeonPeacesOnTouchListener();

                if (dungeonPeaceType == DungeonFragment.DUNGEON_WALL) {
                    setImage.setImageViewBitmapFromAsset(dungeonPeaces[i][j], "dungeon/dungeonWall.png");
                    dungeonInfo[i][j] = DungeonFragment.DUNGEON_WALL;
                } else if (dungeonPeaceType == DungeonFragment.DUNGEON_TRAP1) {
                    setImage.setImageViewBitmapFromAsset(dungeonPeaces[i][j], "dungeon/dungeonTrap1.png");
                    dungeonInfo[i][j] = DungeonFragment.DUNGEON_TRAP1;
                } else if (dungeonPeaceType == DungeonFragment.DUNGEON_DOOR) {
                    setImage.setImageViewBitmapFromAsset(dungeonPeaces[i][j], "dungeon/door.png");
                    dungeonInfo[i][j] = DungeonFragment.DUNGEON_DOOR;
                }

            } else {
                setImage.setImageViewBitmapFromAsset(dungeonPeace, "");
                setDungeonPeacesOnTouchListener();
            }
            moveLayoutFlag = false;
        }
    }
}
