package com.example.fragmenttest2.asynchronous.usersinfo;

import android.os.Handler;
import android.os.Looper;

import com.example.fragmenttest2.asynchronous.AppDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DateSave implements Runnable {
    Handler handler = new Handler(Looper.getMainLooper());
    private AppDatabase db;
    private String name;
    private String salt;
    private String hash;

    public DateSave(AppDatabase db, String name, String salt, String hash) {
        this.db = db;
        this.name = name;
        this.salt = salt;
        this.hash = hash + "A";
    }

    @Override
    public void run() {
        doInBackground();
        handler.post(() -> onPostExecute());
    }

    public void execute() {
        //onPreExecute();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(new DateSave(db, name, salt, hash));
    }

    //void onPreExecute() {}

    void doInBackground() {
        UsersInfoDao usersInfoDao = db.usersInfoDao();
        usersInfoDao.insert(new UsersInfo(name, salt, hash));
    }

    void onPostExecute() {}
}
