package com.example.mainproject.asynchronous;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.ImageView;

import com.example.mainproject.DungeonsInfo;
import com.example.mainproject.SetImage;

import java.util.Random;

public class TimerPossession {
    static final android.os.Handler handler = new Handler(Looper.getMainLooper());
    static boolean flag = false;

    public static class CharacterPosition_Runnable {
        int bX;
        int bY;

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
                Log.v("My", "" + Y);

                try {
                    Random random = new Random();
                    int randomInt = random.nextInt(3);
                    int tmp = DungeonsInfo.characterInfo[Y][X];

                    if (X == 9 && Y == 0) {
                        DungeonsInfo.characterInfo[19][X] = tmp;
                    } else if (X == 9 && Y == 19) {
                        DungeonsInfo.characterInfo[18][X] = tmp;
                    } else if (DungeonsInfo.dungeonInfo[Y][X] == DungeonsInfo.DUNGEON_BOSE) {
                        flag = true;
                        handler.removeCallbacks(characterPosition_runnable);
                    } else if (X - bX < 0) {
                        if (randomInt == 0 && conditions1(X, Y + 1)) {
                            DungeonsInfo.characterInfo[Y + 1][X] = tmp;
                        } else if (randomInt == 1 && conditions1(X - 1, Y)) {
                            DungeonsInfo.characterInfo[Y][X - 1] = tmp;
                        } else if (randomInt == 2 && conditions1(X, Y - 1)) {
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
                        if (randomInt == 0 && conditions1(X, Y - 1)) {
                            DungeonsInfo.characterInfo[Y - 1][X] = tmp;
                        } else if (randomInt == 1 && conditions1(X + 1, Y)) {
                            DungeonsInfo.characterInfo[Y][X + 1] = tmp;
                        } else if (randomInt == 2 && conditions1(X - 1, Y)) {
                            DungeonsInfo.characterInfo[Y][X - 1] = tmp;
                        } else if (conditions1(X, Y - 1)) {
                            DungeonsInfo.characterInfo[Y - 1][X] = tmp;
                        } else {
                            DungeonsInfo.characterInfo[Y + 1][X] = tmp;
                        }
                    } else if (0 < Y - bY) {
                        if (randomInt == 0 && conditions1(X, Y + 1)) {
                            DungeonsInfo.characterInfo[Y + 1][X] = tmp;
                        } else if (randomInt == 1 && conditions1(X + 1, Y)) {
                            DungeonsInfo.characterInfo[Y][X + 1] = tmp;
                        } else if (randomInt == 2 && conditions1(X - 1, Y)) {
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
            return DungeonsInfo.dungeonInfo[Y][X] == DungeonsInfo.DUNGEON_NOTHING ||
                    DungeonsInfo.dungeonInfo[Y][X] == DungeonsInfo.DUNGEON_O_DOOR ||
                    DungeonsInfo.dungeonInfo[Y][X] == DungeonsInfo.DUNGEON_BOSE;
        }

        public void run() {
            handler.post(characterPosition_runnable);
        }

        public void stop() {
            handler.removeCallbacks(characterPosition_runnable);
        }
    }

    public static class SetCharacterImage_Runnable {
        SetImage setImage;
        ImageView imageView;
        int oneSize;

        public SetCharacterImage_Runnable(SetImage setImage, ImageView imageView, int oneSize) {
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
                    new setFightImage(setImage, imageView, oneSize).run();
                    handler.removeCallbacks(this);
                } else {
                    imageView.setX(X * oneSize);
                    imageView.setY(Y * oneSize);
                    handler.removeCallbacks(this);
                    handler.postDelayed(this, 1000);
                }
            }
        };

        public void run() {
            handler.post(setCharacterImage_runnable);
        }

        public void stop() {
            handler.removeCallbacks(setCharacterImage_runnable);
        }
    }

    public static class setFightImage {
        SetImage setImage;
        ImageView imageView;
        int oneSize;
        int imageFlag = 0;

        public setFightImage(SetImage setImage, ImageView imageView, int oneSize) {
            this.setImage = setImage;
            this.imageView = imageView;
            this.oneSize = oneSize;
        }

        final Runnable setFightImage_runnable = new Runnable() {
            @Override
            public void run() {
                if (imageFlag == 0) {
                    setImage.setImageViewBitmapFromAsset(imageView, "dungeon/fight1.png");
                    imageFlag = 1;
                    handler.removeCallbacks(this);
                    handler.postDelayed(this, 1000);
                } else if (imageFlag == 1) {
                    setImage.setImageViewBitmapFromAsset(imageView, "dungeon/fight2.png");
                    imageFlag = 2;
                    handler.removeCallbacks(this);
                    handler.postDelayed(this, 1000);
                } else if (imageFlag == 2) {
                    setImage.setImageViewBitmapFromAsset(imageView, "");
                    flag = false;

                    setImage.setImageViewBitmapFromAsset(imageView, "character/enemy/hito.png");
                    DungeonsInfo.characterInfo[19][9] = 1;
                    imageView.setX(9 * oneSize);
                    imageView.setY(19 * oneSize);
                    imageView.setZ(0);

                    CharacterPosition_Runnable characterPosition_runnable = new CharacterPosition_Runnable();
                    characterPosition_runnable.run();
                    SetCharacterImage_Runnable setCharacterImage_runnable = new SetCharacterImage_Runnable(setImage, imageView, oneSize);
                    setCharacterImage_runnable.run();

                    handler.removeCallbacks(this);
                }
            }
        };

        public void run() {
            handler.post(setFightImage_runnable);
        }
    }
}
