package com.example.mainproject.asynchronous.usersinfo;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;

import com.example.mainproject.asynchronous.AppDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class UpdateTime implements Runnable {
    Handler handler = new Handler(Looper.getMainLooper());
    private final Consumer<Boolean> callback;
    private final Consumer<Exception> errorCallback;
    private Exception exception;
    private final AppDatabase db;
    private final String name;
    private final int nowYear;
    private final int nowMonth;
    private final int nowDay;
    private final int nowHour;
    private final int nowMinute;
    private final int nowSecond;

    public UpdateTime(
            AppDatabase db,
              String name,
              int nowYear,
              int nowMonth,
              int nowDay,
              int nowHour,
              int nowMinute,
              int nowSecond,
              Consumer<Boolean> callback,
              Consumer<Exception> errorCallback)
    {
        this.db = db;
        this.name = name;
        this.nowYear = nowYear;
        this.nowMonth = nowMonth;
        this.nowDay = nowDay;
        this.nowHour = nowHour;
        this.nowMinute = nowMinute;
        this.nowSecond  = nowSecond;
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
        executorService.submit(
                new UpdateTime(
                        db,
                        name,
                        nowYear,
                        nowMonth,
                        nowDay,
                        nowHour,
                        nowMinute,
                        nowSecond,
                        callback,
                        errorCallback));
    }

    //void onPreExecute() {}

    void doInBackground() {
        UsersInfoDao usersInfoDao = db.usersInfoDao();

        try {
            usersInfoDao.updateTimeTask(name, nowYear, nowMonth, nowDay, nowHour, nowMinute, nowSecond);
        } catch (Exception e) {
            this.exception = e;
        }
    }

    @SuppressLint("NewApi")
    void onPostExecute() {
        if(this.exception == null) {
            callback.accept(true);
        } else {
            errorCallback.accept(this.exception);
        }
    }
}
