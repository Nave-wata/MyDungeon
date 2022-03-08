package com.example.mainproject.asynchronous.userspossession;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;

import com.example.mainproject.asynchronous.AppDatabase;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class GetPossession implements Runnable {
    final android.os.Handler handler = new Handler(Looper.getMainLooper());
    final Consumer<List<UsersPossession>> callback;
    final Consumer<Exception> errorCallback;
    private Exception exception;
    final AppDatabase db;
    final String name;
    private List<UsersPossession> data;

    public GetPossession(final AppDatabase db,
                         final String name,
                         final Consumer<List<UsersPossession>> callback,
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
        executorService.submit(new GetPossession(db, name, callback, errorCallback));
    }

    //void onPreExecute() {}

    void doInBackground() {
        UsersPossessionDao usersPossessionDao = db.usersPossessionDao();

        try {
            data = usersPossessionDao.getPossessionTask(name);
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
