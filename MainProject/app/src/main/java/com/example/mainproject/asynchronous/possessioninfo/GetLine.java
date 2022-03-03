package com.example.mainproject.asynchronous.possessioninfo;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.RequiresApi;

import com.example.mainproject.asynchronous.AppDatabase;
import com.example.mainproject.asynchronous.usersinfo.UsersInfo;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class GetLine implements Runnable {
    android.os.Handler handler = new Handler(Looper.getMainLooper());
    private final Consumer<List<UsersInfo>> callback;
    private final Consumer<Exception> errorCallback;
    private Exception exception;
    private final AppDatabase db;
    private final String name;
    private List<PossessionInfo> data;

    public GetLine(AppDatabase db,
                   String name,
                   Consumer<List<UsersInfo>> callback,
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
        executorService.submit(new GetLine(db, name, callback, errorCallback));
    }

    //void onPreExecute() {}

    void doInBackground() {
        PossessionInfoDao possessionInfoDao = db.possessionInfoDao();

        try {
            data = possessionInfoDao.getLine(name);
        } catch (Exception e) {
            this.exception = e;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    void onPostExecute() {
        if (this.exception == null) {
            callback.accept(data);
        } else {
            errorCallback.accept(this.exception);
        }
    }
 }
