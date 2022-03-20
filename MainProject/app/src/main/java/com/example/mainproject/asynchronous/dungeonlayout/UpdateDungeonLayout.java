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
    final String row0;
    final String row1;
    final String row2;
    final String row3;
    final String row4;
    final String row5;
    final String row6;
    final String row7;
    final String row8;
    final String row9;
    final String row10;
    final String row11;
    final String row12;
    final String row13;
    final String row14;
    final String row15;
    final String row16;
    final String row17;
    final String row18;
    final String row19;

    public UpdateDungeonLayout(AppDatabase db,
                               String name,
                               String row0,
                               String row1,
                               String row2,
                               String row3,
                               String row4,
                               String row5,
                               String row6,
                               String row7,
                               String row8,
                               String row9,
                               String row10,
                               String row11,
                               String row12,
                               String row13,
                               String row14,
                               String row15,
                               String row16,
                               String row17,
                               String row18,
                               String row19,
                               Consumer<Boolean> callback,
                               Consumer<Exception> errorCallback) {
        this.db = db;
        this.name = name;
        this.row0 = row0;
        this.row1 = row1;
        this.row2 = row2;
        this.row3 = row3;
        this.row4 = row4;
        this.row5 = row5;
        this.row6 = row6;
        this.row7 = row7;
        this.row8 = row8;
        this.row9 = row9;
        this.row10 = row10;
        this.row11 = row11;
        this.row12 = row12;
        this.row13 = row13;
        this.row14 = row14;
        this.row15 = row15;
        this.row16 = row16;
        this.row17 = row17;
        this.row18 = row18;
        this.row19 = row19;
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
        executorService.submit(new UpdateDungeonLayout(
                db,
                name,
                row0,
                row1,
                row2,
                row3,
                row4,
                row5,
                row6,
                row7,
                row8,
                row9,
                row10,
                row11,
                row12,
                row13,
                row14,
                row15,
                row16,
                row17,
                row18,
                row19,
                callback,
                errorCallback));
    }

    //void onPreExecute() {}

    void doInBackground() {
        DungeonLayoutDao dungeonLayoutDao = db.dungeonLayoutDao();

        try {
            dungeonLayoutDao.updateDungeonLayoutTask(
                    name,
                    row0,
                    row1,
                    row2,
                    row3,
                    row4,
                    row5,
                    row6,
                    row7,
                    row8,
                    row9,
                    row10,
                    row11,
                    row12,
                    row13,
                    row14,
                    row15,
                    row16,
                    row17,
                    row18,
                    row19);
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
