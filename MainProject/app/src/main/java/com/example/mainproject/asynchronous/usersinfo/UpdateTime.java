package com.example.mainproject.asynchronous.usersinfo;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;

import com.example.mainproject.asynchronous.AppDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class UpdateTime implements Runnable {
    final Handler handler = new Handler(Looper.getMainLooper());
    final Consumer<Boolean> callback;
    final Consumer<Exception> errorCallback;
    private Exception exception;
    final AppDatabase db;
    final String name;
    final int nowYear;
    final int nowMonth;
    final int nowDay;
    final int nowHour;
    final int nowMinute;
    final int nowSecond;

    public UpdateTime(AppDatabase db,
                      String name,
                      int nowYear,
                      int nowMonth,
                      int nowDay,
                      int nowHour,
                      int nowMinute,
                      int nowSecond,
                      Consumer<Boolean> callback,
                      Consumer<Exception> errorCallback) {
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
