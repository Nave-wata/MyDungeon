package com.example.main;

import android.app.Application;
import android.util.Log;

public class ApplicationManager extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Log.v("MyApp", "Start");
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Log.v("MyApp", "End");
    }
}
