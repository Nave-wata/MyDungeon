package com.example.mainproject.asynchronous.userspossessioninfo;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;

import com.example.mainproject.asynchronous.AppDatabase;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class GetUsersPossessionInfo implements Runnable {
    Handler handler = new Handler(Looper.getMainLooper());
    private final Consumer<List<UsersPossessionInfo>> callback;
    private final Consumer<Exception> errorCallback;
    private Exception exception;
    private final AppDatabase db;
    private final String name;
    private List<UsersPossessionInfo> data;

    public GetUsersPossessionInfo(AppDatabase db,
                                  String name,
                                  Consumer<List<UsersPossessionInfo>> callback,
                                  Consumer<Exception> errorCallback)
    {
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
        executorService.submit(new GetUsersPossessionInfo(db, name, callback, errorCallback));
    }

    //void onPreExecute() {}

    void doInBackground() {
        UsersPossessionInfoDao usersPossessionInfoDao = db.possessionInfoDao();

        try {
            data = usersPossessionInfoDao.getLineTask(name);
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
