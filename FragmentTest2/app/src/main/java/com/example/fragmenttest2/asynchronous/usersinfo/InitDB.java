package com.example.fragmenttest2.asynchronous.usersinfo;

import android.os.Handler;
import android.os.Looper;

import com.example.fragmenttest2.asynchronous.AppDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class InitDB implements Runnable {
    Handler handler = new Handler(Looper.getMainLooper());
    private Exception exception;
    private AppDatabase db;

    public InitDB(AppDatabase db) {
        this.db = db;
    }

    @Override
    public void run() {
        doInBackground();
        //handler.post(() -> onPostExecute());
    }

    public void execute() {
        // onPreExecute();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(new InitDB(db));
    }

    // void onPreExecute() {}

    void doInBackground() {
        UsersInfoDao usersInfoDao = db.usersInfoDao();
        String name = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String salt = "A";
        String hash = "A";

        try {
            usersInfoDao.insert(new UsersInfo(name, salt, hash));
        } catch (Exception e) {
            this.exception = e;
        }
    }

    //void onPostExecute() {}
}
