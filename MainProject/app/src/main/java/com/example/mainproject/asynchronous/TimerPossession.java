package com.example.mainproject.asynchronous;

import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;

import com.example.mainproject.DungeonsInfo;

public class TimerPossession {
    final android.os.Handler handler = new Handler(Looper.getMainLooper());


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
            if (0 < Y) {
                int tmp = DungeonsInfo.characterInfo[Y][X];
                DungeonsInfo.characterInfo[Y - 1][X] = tmp;
                DungeonsInfo.characterInfo[Y][X] = 0;
            } else {
                int tmp = DungeonsInfo.characterInfo[Y][X];
                DungeonsInfo.characterInfo[19][X] = tmp;
                DungeonsInfo.characterInfo[Y][X] = 0;
            }
            handler.removeCallbacks(this);
            handler.postDelayed(this, 1000);
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
