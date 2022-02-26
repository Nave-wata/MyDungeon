package com.example.fragmenttest2.asynchronous.usersinfo;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.example.fragmenttest2.asynchronous.AppDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class DataSave implements Runnable {
    Handler handler = new Handler(Looper.getMainLooper());
    private final Consumer<Boolean> callback;
    private final Consumer<Exception> errorCallback;
    private Exception exception;
    private AppDatabase db;
    private String name;
    private String salt;
    private String hash;
    private boolean response;

    public DataSave(AppDatabase db,
                    String name,
                    String salt,
                    String hash,
                    Consumer<Boolean> callback,
                    Consumer<Exception> errorCallback)
    {
        this.db = db;
        this.name = name;
        this.salt = salt;
        this.hash = hash + "A";
        this.callback = callback;
        this.errorCallback = errorCallback;
    }

    @Override
    public void run() {
        response = doInBackground();
        handler.post(() -> onPostExecute(response));
    }

    public void execute() {
        //onPreExecute();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(new DataSave(db, name, salt, hash, callback, errorCallback));
    }

    //void onPreExecute() {}

    boolean doInBackground() {
        UsersInfoDao usersInfoDao = db.usersInfoDao();
        try {
            usersInfoDao.insert(new UsersInfo(name, salt, hash));
            return true;
        } catch (Exception e) {
            this.exception = e;
            return false;
        } finally {
            usersInfoDao.deleteAll(); // 今は消すやつ実装してないから
        }
    }

    @SuppressLint("NewApi")
    void onPostExecute(boolean response) {
        if(this.exception == null) {
            callback.accept(response);
        } else {
            errorCallback.accept(this.exception);
        }
    }
}
