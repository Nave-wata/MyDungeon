package com.example.mainproject.asynchronous;

import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;

import com.example.mainproject.DungeonsInfo;
import com.example.mainproject.SetImage;
import com.example.mainproject.dungeon.DungeonFragment;

import java.util.Random;

public class TimerPossession {
    final android.os.Handler handler = new Handler(Looper.getMainLooper());
    static boolean flag = false;
    int bX;
    int bY;


    public TimerPossession() {}
    final Runnable characterPosition_runnable = new Runnable() {
        @Override
        public void run() {
            int X = 0;
            int Y = 0;
            for (int i = 0; i < DungeonsInfo.heightNum; i++) {
                for (int j = 0; j < DungeonsInfo.widthNum; j++) {
                    if (DungeonsInfo.characterInfo[i][j] == 1) {
                        X = j;
                        Y = i;
                    }
                }
            }

            try {
                Random random = new Random();
                int randomInt = random.nextInt(3);
                int tmp = DungeonsInfo.characterInfo[Y][X];

                if (X == 9 && Y == 0) {
                    DungeonsInfo.characterInfo[19][X] = tmp;
                } else if (X == 9 && Y == 19) {
                    DungeonsInfo.characterInfo[18][X] = tmp;
                } else if (DungeonsInfo.dungeonInfo[Y][X] == DungeonFragment.DUNGEON_BOSE) {
                    //DungeonsInfo.characterInfo[19][9] = tmp; // ボスとの戦闘画像に切り替えて
                    flag = true;
                    handler.removeCallbacks(characterPosition_runnable);

                } else if (X - bX < 0) {
                    if (randomInt == 0 && conditions1 (X, Y + 1)) {
                        DungeonsInfo.characterInfo[Y + 1][X] = tmp;
                    } else if (randomInt == 1 && conditions1 (X - 1, Y)) {
                        DungeonsInfo.characterInfo[Y][X - 1] = tmp;
                    } else if (randomInt == 2 && conditions1 (X, Y - 1)) {
                        DungeonsInfo.characterInfo[Y - 1][X] = tmp;
                    } else if (conditions1(X - 1, Y)) {
                        DungeonsInfo.characterInfo[Y][X - 1] = tmp;
                    } else {
                        DungeonsInfo.characterInfo[Y][X + 1] = tmp;
                    }
                } else if (0 < X - bX) {
                    if (randomInt == 0 && conditions1(X, Y + 1)) {
                        DungeonsInfo.characterInfo[Y + 1][X] = tmp;
                    } else if (randomInt == 1 && conditions1(X + 1, Y)) {
                        DungeonsInfo.characterInfo[Y][X + 1] = tmp;
                    } else if (randomInt == 2 && conditions1(X, Y - 1)) {
                        DungeonsInfo.characterInfo[Y - 1][X] = tmp;
                    } else if (conditions1(X + 1, Y)) {
                        DungeonsInfo.characterInfo[Y][X + 1] = tmp;
                    } else {
                        DungeonsInfo.characterInfo[Y][X - 1] = tmp;
                    }
                } else if (Y - bY < 0) {
                    if (randomInt == 0 && conditions1 (X, Y - 1)) {
                        DungeonsInfo.characterInfo[Y - 1][X] = tmp;
                    } else if (randomInt == 1 && conditions1 (X + 1, Y)) {
                        DungeonsInfo.characterInfo[Y][X + 1] = tmp;
                    } else if (randomInt == 2 && conditions1 (X - 1, Y)) {
                        DungeonsInfo.characterInfo[Y][X - 1] = tmp;
                    } else if (conditions1(X, Y - 1)) {
                        DungeonsInfo.characterInfo[Y - 1][X] = tmp;
                    } else {
                        DungeonsInfo.characterInfo[Y + 1][X] = tmp;
                    }
                } else if (0 < Y - bY) {
                    if (randomInt == 0 && conditions1(X, Y + 1)) {
                        DungeonsInfo.characterInfo[Y + 1][X] = tmp;
                    } else if (randomInt == 1 && conditions1 (X + 1, Y)) {
                        DungeonsInfo.characterInfo[Y][X + 1] = tmp;
                    } else if (randomInt == 2 && conditions1 (X - 1, Y)) {
                        DungeonsInfo.characterInfo[Y][X - 1] = tmp;
                    } else if (conditions1(X, Y + 1)) {
                        DungeonsInfo.characterInfo[Y + 1][X] = tmp;
                    } else {
                        DungeonsInfo.characterInfo[Y - 1][X] = tmp;
                    }
                }

                bX = X;
                bY = Y;
                DungeonsInfo.characterInfo[Y][X] = 0;
                handler.removeCallbacks(this);
                handler.postDelayed(this, 1000);
            } catch (Exception e) {
                //
            }
        }
    };
    public boolean conditions1(int X, int Y) {
        return  DungeonsInfo.dungeonInfo[Y][X] == DungeonFragment.DUNGEON_NOTHING ||
                DungeonsInfo.dungeonInfo[Y][X] == DungeonFragment.DUNGEON_O_DOOR ||
                DungeonsInfo.dungeonInfo[Y][X] == DungeonFragment.DUNGEON_BOSE; }

    public void characterPosition_runnable_Run() { handler.post(characterPosition_runnable); }
    public void characterPosition_runnable_Stop() { handler.removeCallbacks(characterPosition_runnable); }


    SetImage setImage;
    ImageView imageView;
    int oneSize;

    public TimerPossession(SetImage setImage, ImageView imageView, int oneSize) {
        this.setImage = setImage;
        this.imageView = imageView;
        this.oneSize = oneSize;
    }
    final Runnable setCharacterImage_runnable = new Runnable() {
        @Override
        public void run() {
            int X = -1;
            int Y = -1;
            for (int i = 0; i < DungeonsInfo.heightNum; i++) {
                for (int j = 0; j < DungeonsInfo.widthNum; j++) {
                    if (DungeonsInfo.characterInfo[i][j] == 1) {
                        X = j;
                        Y = i;
                    }
                }
            }
            if (flag) {
                imageView.setZ(1);
                setImage.setImageViewBitmapFromAsset(imageView, "dungeon/fight1.png");
            } else {
                imageView.setX(X * oneSize);
                imageView.setY(Y * oneSize);
            }

            handler.removeCallbacks(this);
            handler.postDelayed(this, 1000);
        }
    };
    public void setCharacterImage_runnable_Run() { handler.post(setCharacterImage_runnable); }
    public void setCharacterImage_runnable_Stop() { handler.removeCallbacks(setCharacterImage_runnable); }
}
