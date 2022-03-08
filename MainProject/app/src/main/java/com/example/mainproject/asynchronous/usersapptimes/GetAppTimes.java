package com.example.mainproject.asynchronous.usersapptimes;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;

import com.example.mainproject.asynchronous.AppDatabase;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class GetAppTimes implements Runnable {
    final Handler handler = new Handler(Looper.getMainLooper());
    final Consumer<List<UsersAppTimes>> callback;
    final Consumer<Exception> errorCallback;
    private Exception exception;
    final AppDatabase db;
    final String name;
    private List<UsersAppTimes> data;

    public GetAppTimes(final AppDatabase db,
                       final String name,
                       final Consumer<List<UsersAppTimes>> callback,
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
        executorService.submit(new GetAppTimes(db, name, callback, errorCallback));
    }

    //void onPreExecute() {}

    void doInBackground() {
        UsersAppTimesDao usersAppTimesDao = db.usersAppTimesDao();

        try {
            data = usersAppTimesDao.getTimesTask(name);
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
