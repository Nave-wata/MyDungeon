package com.example.mainproject.asynchronous.dungeonlayout;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

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
    String row0;
    String row1;
    String row2;
    String row3;
    String row4;
    String row5;
    String row6;
    String row7;
    String row8;
    String row9;
    String row10 ;
    String row11 ;
    String row12 ;
    String row13 ;
    String row14 ;
    String row15 ;
    String row16 ;
    String row17 ;
    String row18 ;
    String row19 ;

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
        row0 = data[0];
        row1 = data[1];
        row2 = data[2];
        row3 = data[3];
        row4 = data[4];
        row5 = data[5];
        row6 = data[6];
        row7 = data[7];
        row8 = data[8];
        row9 = data[9];
        row10 = data[10];
        row11 = data[11];
        row12 = data[12];
        row13 = data[13];
        row14 = data[14];
        row15 = data[15];
        row16 = data[16];
        row17 = data[17];
        row18 = data[18];
        row19 = data[19];
    }

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
            Log.v("My", "" + row0);
            Log.v("My", "" + row1);
            Log.v("My", "" + row2);
            Log.v("My", "" + row3);
            Log.v("My", "" + row4);
            Log.v("My", "" + row5);
            Log.v("My", "" + row6);
            Log.v("My", "" + row7);
            Log.v("My", "" + row8);
            Log.v("My", "" + row9);
            Log.v("My", "" + row10);
            Log.v("My", "" + row11);
            Log.v("My", "" + row12);
            Log.v("My", "" + row13);
            Log.v("My", "" + row14);
            Log.v("My", "" + row15);
            Log.v("My", "" + row16);
            Log.v("My", "" + row17);
            Log.v("My", "" + row18);
            Log.v("My", "" + row19);
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
