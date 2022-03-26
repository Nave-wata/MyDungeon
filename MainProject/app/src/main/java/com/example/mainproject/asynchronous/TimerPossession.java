package com.example.mainproject.asynchronous;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.example.mainproject.DungeonsInfo;

public class TimerPossession {

    final android.os.Handler handler = new Handler(Looper.getMainLooper());
    final Runnable characterPosition_runnable = new Runnable() {
        @Override
        public void run() {
            int tmp = DungeonsInfo.dungeonInfo[DungeonsInfo.X][DungeonsInfo.Y];
            DungeonsInfo.dungeonInfo[DungeonsInfo.X][DungeonsInfo.Y] = 0;
            DungeonsInfo.dungeonInfo[DungeonsInfo.X][DungeonsInfo.Y] = tmp;
            if (0 < DungeonsInfo.Y) {
                DungeonsInfo.Y--;
            } else {
                DungeonsInfo.Y = DungeonsInfo.widthNum - 1;
            }
            Log.v("My Y", "" + DungeonsInfo.Y);
            handler.removeCallbacks(this);
            handler.postDelayed(this, 1000);
        }
    };

    public void characterPosition_runnable_Run() { handler.post(characterPosition_runnable); }

    public void characterPosition_runnable_Stop() { handler.removeCallbacks(characterPosition_runnable); }
}
