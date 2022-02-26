package com.example.fragmenttest2.asynchronous.usersinfo;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.example.fragmenttest2.asynchronous.AppDatabase;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class GetLine implements Runnable {
    static { System.loadLibrary("fragmenttest2"); }
    static native String HASH(String password, String salt);

    Handler handler = new Handler(Looper.getMainLooper());
    private final Consumer<Boolean> callback;
    private final Consumer<Exception> errorCallback;
    private Exception exception;
    private boolean response;
    private AppDatabase db;
    private String name;
    private String password;
    private List<UsersInfo> data;

    public GetLine(AppDatabase db,
                   String name,
                   String password,
                   Consumer<Boolean> callback,
                   Consumer<Exception> errorCallback)
    {
        this.db = db;
        this.name = name;
        this.password = password;
        this.callback = callback;
        this.errorCallback = errorCallback;
    }

    @Override
    public void run() {
        response = doInBackground();
        handler.post(() -> onPostExecute());
    }

    public void execute() {
        //onPreExecute();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(new GetLine(db, name, password, callback, errorCallback));
    }

    //void onPreExecute() {}

    boolean doInBackground() {
        UsersInfoDao usersInfoDao = db.usersInfoDao();

        try {
            data = usersInfoDao.getLine(name);
            return true;
        } catch (Exception e) {
            this.exception = e;
            return false;
        } finally {
            usersInfoDao.deleteAll(); // 井濱消すやつ実装してないから
        }
    }

    @SuppressLint("NewAPI")
    void onPostExecute() {
        if (this.exception == null) {
            callback.accept(response);
        } else {
            errorCallback.accept(this.exception);
        }
    }
}
