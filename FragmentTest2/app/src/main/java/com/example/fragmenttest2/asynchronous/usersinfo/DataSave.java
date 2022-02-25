package com.example.fragmenttest2.asynchronous.usersinfo;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.example.fragmenttest2.asynchronous.AppDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DataSave implements Runnable {
    Handler handler = new Handler(Looper.getMainLooper());
    private AppDatabase db;
    private String name;
    private String salt;
    private String hash;

    public DataSave(AppDatabase db, String name, String salt, String hash) {
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
        executorService.submit(new DataSave(db, name, salt, hash));
    }

    //void onPreExecute() {}

    void doInBackground() {
        UsersInfoDao usersInfoDao = db.usersInfoDao();
        usersInfoDao.insert(new UsersInfo(name, salt, hash));
        usersInfoDao.deleteAll(); // 今は消すやつ実装してないから
    }

    void onPostExecute() {
        Log.v("DataSave", "OK?");
    }
}
