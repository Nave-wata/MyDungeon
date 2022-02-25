package com.example.fragmenttest2.asynchronous.usersinfo;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DateSave implements Runnable {
    Handler handler = new Handler(Looper.getMainLooper());

    @Override
    public void run() {
        doInBackground();
        handler.post(() -> onPostExecute());
    }

    public void execute() {
        onPreExecute();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(new DateSave());
    }

    void onPreExecute() {}

    void doInBackground() {}

    void onPostExecute() {}
}
