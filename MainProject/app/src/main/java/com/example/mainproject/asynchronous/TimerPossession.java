package com.example.mainproject.asynchronous;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.ImageView;

import com.example.mainproject.DungeonsInfo;

public class TimerPossession {
    final android.os.Handler handler = new Handler(Looper.getMainLooper());

    public TimerPossession() {}
    final Runnable characterPosition_runnable = new Runnable() {
        @Override
        public void run() {
            Log.v("My TimerPossession", "" + DungeonsInfo.Y);
            int tmp = DungeonsInfo.dungeonInfo[DungeonsInfo.X][DungeonsInfo.Y];
            DungeonsInfo.dungeonInfo[DungeonsInfo.X][DungeonsInfo.Y] = 0;
            DungeonsInfo.dungeonInfo[DungeonsInfo.X][DungeonsInfo.Y] = tmp;
            if (0 < DungeonsInfo.Y) {
                DungeonsInfo.Y--;
            } else {
                DungeonsInfo.Y = DungeonsInfo.widthNum - 1;
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
            Log.v("My TimerPossession", "" + DungeonsInfo.Y);
            imageView.setX(DungeonsInfo.X * oneSize);
            imageView.setY(DungeonsInfo.Y * oneSize);
            handler.removeCallbacks(this);
            handler.postDelayed(this, 1000);
        }
    };
    public void setCharacterImage_runnable_Run() { handler.post(setCharacterImage_runnable); }
    public void setCharacterImage_runnable_Stop() { handler.removeCallbacks(setCharacterImage_runnable); }
}
