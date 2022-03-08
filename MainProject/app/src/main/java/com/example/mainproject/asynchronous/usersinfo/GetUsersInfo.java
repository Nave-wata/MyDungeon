package com.example.mainproject.asynchronous.usersinfo;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;

import com.example.mainproject.asynchronous.AppDatabase;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class GetUsersInfo implements Runnable {
    final Handler handler = new Handler(Looper.getMainLooper());
    final Consumer<List<UsersInfo>> callback;
    final Consumer<Exception> errorCallback;
    private Exception exception;
    final AppDatabase db;
    final String name;
    private List<UsersInfo> data;

    public GetUsersInfo(final AppDatabase db,
                        final String name,
                        final Consumer<List<UsersInfo>> callback,
                        final Consumer<Exception> errorCallback) {
        this.db = db;
        this.name = name;
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
        executorService.submit(new GetUsersInfo(db, name, callback, errorCallback));
    }

    //void onPreExecute() {}

    void doInBackground() {
        UsersInfoDao usersInfoDao = db.usersInfoDao();

        try {
            data = usersInfoDao.getUserInfoTask(name);
        } catch (Exception e) {
            this.exception = e;
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