package com.example.mainproject.asynchronous.possessioninfo;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.RequiresApi;

import com.example.mainproject.asynchronous.AppDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class DataSave implements Runnable {
    Handler handler = new Handler(Looper.getMainLooper());
    final Consumer<Boolean> callback;
    final Consumer<Exception> errorCallback;
    private Exception exception;
    final AppDatabase db;
    final String name;
    final int year;
    final int month;
    final int day;
    final int hour;
    final int minute;
    final int second;

    public DataSave(AppDatabase db,
                    String name,
                    int year,
                    int month,
                    int day,
                    int hour,
                    int minute,
                    int second,
                    Consumer<Boolean> callback,
                    Consumer<Exception> errorCallback)
    {
        this.db = db;
        this.name = name;
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.second = second;
        this.callback = callback;
        this.errorCallback = errorCallback;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void run() {
        doInBackground();
        handler.post(this::onPostExecute);
    }

    public void execute() {
        //onPreExecute();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(
                new DataSave(
                        db,
                        name,
                        year,
                        month,
                        day,
                        hour,
                        minute,
                        second,
                        callback,
                        errorCallback));
    }

    //void onPreExecute() {}

    void doInBackground() {
        PossessionInfoDao possessionInfoDao = db.possessionInfoDao();

        try {
            possessionInfoDao.insert(new PossessionInfo(name, year, month, day, hour, minute, second));
        } catch (Exception e) {
            try {
                possessionInfoDao.UpDateTime(name, year, month, day, hour, minute, second);
            } catch (Exception ie) {
                this.exception = ie;
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    void onPostExecute() {
        if (this.exception == null) {
            callback.accept(true);
        } else {
            errorCallback.accept(this.exception);
        }
    }
}
