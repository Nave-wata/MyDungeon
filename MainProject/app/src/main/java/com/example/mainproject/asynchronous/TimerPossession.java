package com.example.mainproject.asynchronous;

import android.os.Handler;
import android.os.Looper;

import com.example.mainproject.BaseStatusFragment;

public class TimerPossession {
    final android.os.Handler handler = new Handler(Looper.getMainLooper());
    final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            BaseStatusFragment baseStatusFragment = new BaseStatusFragment();
            baseStatusFragment.initDiffTime(1, BaseStatusFragment.DP, BaseStatusFragment.MONEY);
            handler.removeCallbacks(this);
            handler.postDelayed(this, 1000);
        }
    };

    public void Run() { handler.post(runnable); }

    public void Stop() { handler.removeCallbacks(runnable); }
}
