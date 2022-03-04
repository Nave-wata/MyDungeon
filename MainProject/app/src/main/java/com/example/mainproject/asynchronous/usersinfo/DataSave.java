package com.example.mainproject.asynchronous.usersinfo;


import android.annotation.SuppressLint;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Handler;
import android.os.Looper;

import com.example.mainproject.asynchronous.AppDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class DataSave implements Runnable {
    Handler handler = new Handler(Looper.getMainLooper());
    private final Consumer<Boolean> callback;
    private final Consumer<SQLiteConstraintException> sqlErrorCallback;
    private final Consumer<Exception> errorCallback;
    private Exception exception;
    private SQLiteConstraintException sqliteConstraintException;
    private final AppDatabase db;
    private final String name;
    private final String salt;
    private final String hash;

    public DataSave(AppDatabase db,
                    String name,
                    String salt,
                    String hash,
                    Consumer<Boolean> callback,
                    Consumer<SQLiteConstraintException> sqlErrorCallback,
                    Consumer<Exception> errorCallback)
    {
        this.db = db;
        this.name = name;
        this.salt = salt;
        this.hash = hash;
        this.callback = callback;
        this.sqlErrorCallback = sqlErrorCallback;
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
                new DataSave(
                        db,
                        name,
                        salt,
                        hash,
                        callback,
                        sqlErrorCallback,
                        errorCallback));
    }

    //void onPreExecute() {}

    void doInBackground() {
        UsersInfoDao usersInfoDao = db.usersInfoDao();

        try {
            usersInfoDao.insertNames(name, salt, hash);
        } catch (SQLiteConstraintException e) {
            this.sqliteConstraintException = e;
            this.exception = e;
        } catch (Exception e) {
            this.exception = e;
        }
    }

    @SuppressLint("NewApi")
    void onPostExecute() {
        if(this.exception == null) {
            callback.accept(true);
        } else if (this.exception == this.sqliteConstraintException) {
            sqlErrorCallback.accept((SQLiteConstraintException) this.exception);
        } else {
            errorCallback.accept(this.exception);
        }
    }
}