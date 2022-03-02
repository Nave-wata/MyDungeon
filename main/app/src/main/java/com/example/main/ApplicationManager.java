package com.example.main;

import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;

public class ApplicationManager extends Application implements Application.ActivityLifecycleCallbacks {
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle saveInstanceState) {}

    @Override
    public void onActivityStarted(Activity activity) {}

    @Override
    public void onActivityResumed(Activity activity) {}

    @Override
    public void onActivityPaused(@NonNull Activity activity) {}

    @Override
    public void onActivityStopped(Activity activity) {}

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle bundle) {}

    @Override
    public void onActivityDestroyed(Activity activity) {}

    class DestroyingService extends Service {
        @Override
        public IBinder onBind(Intent intent) {
            Log.v("MyApp", "End");
            return null;
        }
    }
}
