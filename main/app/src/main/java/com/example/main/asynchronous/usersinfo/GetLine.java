package com.example.main.asynchronous.usersinfo;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.example.main.asynchronous.AppDatabase;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class GetLine implements Runnable {
    Handler handler = new Handler(Looper.getMainLooper());
    private final Consumer<List<UsersInfo>> callback;
    private final Consumer<Exception> errorCallback;
    private Exception exception;
    private final AppDatabase db;
    private final String name;
    private final String password;
    private List<UsersInfo> data;

    public GetLine(AppDatabase db,
                   String name,
                   String password,
                   Consumer<List<UsersInfo>> callback,
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
        doInBackground();
        handler.post(this::onPostExecute);
    }

    public void execute() {
        //onPreExecute();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(new GetLine(db, name, password, callback, errorCallback));
    }

    //void onPreExecute() {}

    void doInBackground() {
        UsersInfoDao usersInfoDao = db.usersInfoDao();

        try {
            data = usersInfoDao.getLine(name);
        } catch (Exception e) {
            this.exception = e;
            Log.v("[Exception]", e.toString());
        }
    }

    @SuppressLint("NewAPI")
    void onPostExecute() {
        if (this.exception == null) {
            callback.accept(data);
        } else {
            errorCallback.accept(this.exception);
        }
    }
}
