package com.example.fragmenttest2.asynchronous.usersinfo;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.example.fragmenttest2.asynchronous.AppDatabase;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GetLine implements Runnable {
    static {
        System.loadLibrary("fragmenttest2");
    }
    static native String HASH(String password, String salt);

    Handler handler = new Handler(Looper.getMainLooper());
    private AppDatabase db;
    private String name;
    private String password;
    private List<UsersInfo> data;

    public GetLine(AppDatabase db, String name, String password) {
        this.db = db;
        this.name = name;
        this.password = password;
    }

    @Override
    public void run() {
        doInBackground();
        handler.post(() -> onPostExecute());
    }

    public void execute() {
        //onPreExecute();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(new GetLine(db, name, password));
    }

    //void onPreExecute() {}

    void doInBackground() {
        UsersInfoDao usersInfoDao = db.usersInfoDao();
        data = usersInfoDao.getLine(name);
        usersInfoDao.deleteAll(); // 今は消すやつ実装してないから
    }

    void onPostExecute() {

    }
}
