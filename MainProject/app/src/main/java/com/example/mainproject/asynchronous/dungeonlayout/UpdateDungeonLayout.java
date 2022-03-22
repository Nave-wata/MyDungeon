package com.example.mainproject.asynchronous.dungeonlayout;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;

import com.example.mainproject.asynchronous.AppDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class UpdateDungeonLayout implements Runnable {
    final android.os.Handler handler = new Handler(Looper.getMainLooper());
    final Consumer<Boolean> callback;
    final Consumer<Exception> errorCallback;
    private Exception exception;
    final AppDatabase db;
    final String name;
    final int[][] rows;
    private String[] data;

    public UpdateDungeonLayout(AppDatabase db,
                               String name,
                               int[][] rows,
                               Consumer<Boolean> callback,
                               Consumer<Exception> errorCallback) {
        this.db = db;
        this.name = name;
        this.rows = rows;
        this.callback = callback;
        this.errorCallback = errorCallback;
    }

    @Override
    public void run() {
        doInBackground();
        handler.post(this::onPostExecute);
    }

    public void execute() {
        onPreExecute();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(new UpdateDungeonLayout(
                db,
                name,
                rows,
                callback,
                errorCallback));
    }

    void onPreExecute() {
        data = new String[rows.length];

        for (int i = 0; i < rows.length; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < rows[i].length; j++) {
                sb.append(rows[i][j]);
                if (j != rows[i].length - 1) {
                    sb.append(",");
                }
            }
            data[i] = sb.toString();
        }
    }

    void doInBackground() {
        DungeonLayoutDao dungeonLayoutDao = db.dungeonLayoutDao();

        try {
            dungeonLayoutDao.updateDungeonLayoutTask(
                    name,
                    data[0],
                    data[1],
                    data[2],
                    data[3],
                    data[4],
                    data[5],
                    data[6],
                    data[7],
                    data[8],
                    data[9],
                    data[10],
                    data[11],
                    data[12],
                    data[13],
                    data[14],
                    data[15],
                    data[16],
                    data[17],
                    data[18],
                    data[19]);
        } catch (Exception e) {
            this.exception = e;
        }
    }

    @SuppressLint("NewAPI")
    void onPostExecute() {
        if (this.exception == null) {
            callback.accept(true);
        } else {
            errorCallback.accept(this.exception);
        }
    }
}
