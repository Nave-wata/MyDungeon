package com.example.main.asynchronous;

import android.util.Log;

import com.example.main.asynchronous.usersinfo.UsersInfo;
import com.example.main.asynchronous.usersinfo.UsersInfoDao;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class InitializeDatabase implements Runnable {
    private final AppDatabase db;

    public InitializeDatabase(AppDatabase db) {
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
        executorService.submit(new InitializeDatabase(db));
    }

    // void onPreExecute() {}

    void doInBackground() {
        UsersInfoDao usersInfoDao = db.usersInfoDao();
        String name = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String salt = "A";
        String hash = "A";

        try {
            usersInfoDao.insert(new UsersInfo(name, salt, hash));
            Log.v("MyOne", "NonTime");
        } catch (Exception ignored) {
            Log.v("MyOne", "Time");
        }
    }

    //void onPostExecute() {}
}
