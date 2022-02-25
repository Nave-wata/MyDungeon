package com.example.fragmenttest2.asynchronous.usersinfo;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.example.fragmenttest2.asynchronous.AppDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GetLine implements Runnable {
    Handler handler = new Handler(Looper.getMainLooper());
    private AppDatabase db;

    public GetLine(AppDatabase db, String name) {
        this.db = db;
    }

    @Override
    public void run() {
        doInBackground();
        handler.post(() -> onPostExecute());
    }

    public void execute() {
        //onPreExecute();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(new GetLine(db);
    }

    //void onPreExecute() {}

    void doInBackground() {
        UsersInfoDao usersInfoDao = db.usersInfoDao();
        usersInfoDao.getLine();
        usersInfoDao.deleteAll(); // 今は消すやつ実装してないから
    }

    void onPostExecute() {
        Log.v("DataSave", "OK?");
    }
}
