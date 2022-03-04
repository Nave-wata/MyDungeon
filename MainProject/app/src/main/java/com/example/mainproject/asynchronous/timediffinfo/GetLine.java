package com.example.mainproject.asynchronous.timediffinfo;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.RequiresApi;

import com.example.mainproject.asynchronous.AppDatabase;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class GetLine implements Runnable {
    android.os.Handler handler = new Handler(Looper.getMainLooper());
    private final Consumer<List<TimeDiffInfo>> callback;
    private final Consumer<Exception> errorCallback;
    private Exception exception;
    private final AppDatabase db;
    private final String name;
    private List<TimeDiffInfo> data;

    public GetLine(AppDatabase db,
                   String name,
                   Consumer<List<TimeDiffInfo>> callback,
                   Consumer<Exception> errorCallback)
    {
        this.db = db;
        this.name = name;
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
        executorService.submit(new GetLine(db, name, callback, errorCallback));
    }

    //void onPreExecute() {}

    void doInBackground() {
        TimeDiffInfoDao timeDiffInfoDao = db.timeDiffInfoDao();

        try {
            data = timeDiffInfoDao.getLine(name);
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
