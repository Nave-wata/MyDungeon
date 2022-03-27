package com.example.mainproject.asynchronous;

import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;

import com.example.mainproject.DungeonsInfo;
import com.example.mainproject.dungeon.DungeonFragment;

import java.util.Random;

public class TimerPossession {
    final android.os.Handler handler = new Handler(Looper.getMainLooper());
    int bX;
    int bY;
    int aX;
    int aY;


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
                } else if (X - bX < 0) {
                    if (randomInt == 0 && (DungeonsInfo.dungeonInfo[Y + 1][X] == DungeonFragment.DUNGEON_NOTHING || DungeonsInfo.dungeonInfo[Y + 1][X] == DungeonFragment.DUNGEON_O_DOOR)) {
                        DungeonsInfo.characterInfo[Y + 1][X] = tmp;
                    } else if (randomInt == 1 && (DungeonsInfo.dungeonInfo[Y][X - 1] == DungeonFragment.DUNGEON_NOTHING || DungeonsInfo.dungeonInfo[Y][X - 1] == DungeonFragment.DUNGEON_O_DOOR)) {
                        DungeonsInfo.characterInfo[Y][X - 1] = tmp;
                    } else if (randomInt == 2 && (DungeonsInfo.dungeonInfo[Y - 1][X] == DungeonFragment.DUNGEON_NOTHING || DungeonsInfo.dungeonInfo[Y - 1][X] == DungeonFragment.DUNGEON_O_DOOR)) {
                        DungeonsInfo.characterInfo[Y - 1][X] = tmp;
                    } else if (DungeonsInfo.dungeonInfo[Y][X - 1] == DungeonFragment.DUNGEON_NOTHING || DungeonsInfo.dungeonInfo[Y][X - 1] == DungeonFragment.DUNGEON_O_DOOR) {
                        DungeonsInfo.characterInfo[Y][X - 1] = tmp;
                    } else {
                        DungeonsInfo.characterInfo[Y][X + 1] = tmp;
                    }
                } else if (0 < X - bX) {
                    if (randomInt == 0 && (DungeonsInfo.dungeonInfo[Y + 1][X] == DungeonFragment.DUNGEON_NOTHING || DungeonsInfo.dungeonInfo[Y + 1][X] == DungeonFragment.DUNGEON_O_DOOR)) {
                        DungeonsInfo.characterInfo[Y + 1][X] = tmp;
                    } else if (randomInt == 1 && (DungeonsInfo.dungeonInfo[Y][X + 1] == DungeonFragment.DUNGEON_NOTHING || DungeonsInfo.dungeonInfo[Y][X + 1] == DungeonFragment.DUNGEON_O_DOOR)) {
                        DungeonsInfo.characterInfo[Y][X + 1] = tmp;
                    } else if (randomInt == 2 && (DungeonsInfo.dungeonInfo[Y - 1][X] == DungeonFragment.DUNGEON_NOTHING || DungeonsInfo.dungeonInfo[Y - 1][X] == DungeonFragment.DUNGEON_O_DOOR)) {
                        DungeonsInfo.characterInfo[Y - 1][X] = tmp;
                    } else if (DungeonsInfo.dungeonInfo[Y][X + 1] == DungeonFragment.DUNGEON_NOTHING || DungeonsInfo.dungeonInfo[Y][X + 1] == DungeonFragment.DUNGEON_O_DOOR) {
                        DungeonsInfo.characterInfo[Y][X + 1] = tmp;
                    } else {
                        DungeonsInfo.characterInfo[Y][X - 1] = tmp;
                    }
                } else if (Y - bY < 0) {
                    if (randomInt == 0 && (DungeonsInfo.dungeonInfo[Y - 1][X] == DungeonFragment.DUNGEON_NOTHING || DungeonsInfo.dungeonInfo[Y - 1][X] == DungeonFragment.DUNGEON_O_DOOR)) {
                        DungeonsInfo.characterInfo[Y - 1][X] = tmp;
                    } else if (randomInt == 1 && (DungeonsInfo.dungeonInfo[Y][X + 1] == DungeonFragment.DUNGEON_NOTHING || DungeonsInfo.dungeonInfo[Y][X + 1] == DungeonFragment.DUNGEON_O_DOOR)) {
                        DungeonsInfo.characterInfo[Y][X + 1] = tmp;
                    } else if (randomInt == 2 && (DungeonsInfo.dungeonInfo[Y][X - 1] == DungeonFragment.DUNGEON_NOTHING || DungeonsInfo.dungeonInfo[Y][X - 1] == DungeonFragment.DUNGEON_O_DOOR)) {
                        DungeonsInfo.characterInfo[Y][X - 1] = tmp;
                    } else if (DungeonsInfo.dungeonInfo[Y - 1][X] == DungeonFragment.DUNGEON_NOTHING || DungeonsInfo.dungeonInfo[Y - 1][X] == DungeonFragment.DUNGEON_O_DOOR) {
                        DungeonsInfo.characterInfo[Y - 1][X] = tmp;
                    } else {
                        DungeonsInfo.characterInfo[Y + 1][X] = tmp;
                    }
                } else if (0 < Y - bY) {
                    if (randomInt == 0 && (DungeonsInfo.dungeonInfo[Y + 1][X] == DungeonFragment.DUNGEON_NOTHING || DungeonsInfo.dungeonInfo[Y + 1][X] == DungeonFragment.DUNGEON_O_DOOR)) {
                        DungeonsInfo.characterInfo[Y + 1][X] = tmp;
                    } else if (randomInt == 1 && (DungeonsInfo.dungeonInfo[Y][X + 1] == DungeonFragment.DUNGEON_NOTHING || DungeonsInfo.dungeonInfo[Y][X + 1] == DungeonFragment.DUNGEON_O_DOOR)) {
                        DungeonsInfo.characterInfo[Y][X + 1] = tmp;
                    } else if (randomInt == 2 && (DungeonsInfo.dungeonInfo[Y][X - 1] == DungeonFragment.DUNGEON_NOTHING || DungeonsInfo.dungeonInfo[Y][X - 1] == DungeonFragment.DUNGEON_O_DOOR)) {
                        DungeonsInfo.characterInfo[Y][X - 1] = tmp;
                    } else if (DungeonsInfo.dungeonInfo[Y + 1][X] == DungeonFragment.DUNGEON_NOTHING || DungeonsInfo.dungeonInfo[Y + 1][X] == DungeonFragment.DUNGEON_O_DOOR) {
                        DungeonsInfo.characterInfo[Y + 1][X] = tmp;
                    } else {
                        DungeonsInfo.characterInfo[Y - 1][X] = tmp;
                    }
                }
            } catch (Exception e) {
                //
            } finally {
                bX = X;
                bY = Y;
                DungeonsInfo.characterInfo[Y][X] = 0;
                handler.removeCallbacks(this);
                handler.postDelayed(this, 1000);
            }
        }
    };
    public void characterPosition_runnable_Run() { handler.post(characterPosition_runnable); }
    public void characterPosition_runnable_Stop() { handler.removeCallbacks(characterPosition_runnable); }


    ImageView imageView;
    int oneSize;

    public TimerPossession(ImageView imageView, int oneSize) {
        this.imageView = imageView;
        this.oneSize = oneSize;
    }
    final Runnable setCharacterImage_runnable = new Runnable() {
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
            imageView.setX(X * oneSize);
            imageView.setY(Y * oneSize);
            handler.removeCallbacks(this);
            handler.postDelayed(this, 1000);
        }
    };
    public void setCharacterImage_runnable_Run() { handler.post(setCharacterImage_runnable); }
    public void setCharacterImage_runnable_Stop() { handler.removeCallbacks(setCharacterImage_runnable); }
}
