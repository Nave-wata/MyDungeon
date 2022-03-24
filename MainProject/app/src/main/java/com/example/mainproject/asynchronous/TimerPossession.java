package com.example.mainproject.asynchronous;

import android.os.Handler;
import android.os.Looper;

public class TimerPossession {
    final android.os.Handler handler = new Handler(Looper.getMainLooper());
    final Runnable characterPosition_runnable = new Runnable() {
        @Override
        public void run() {
            // something
            handler.removeCallbacks(this);
            handler.postDelayed(this, 2000);
        }
    };

    public void Run() { handler.post(characterPosition_runnable); }

    public void Stop() { handler.removeCallbacks(characterPosition_runnable); }
}
