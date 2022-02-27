package com.example.fragmenttest2.asynchronous.usersinfo;

import android.annotation.SuppressLint;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.example.fragmenttest2.asynchronous.AppDatabase;

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
    private boolean response;
    private AppDatabase db;
    private String name;
    private String salt;
    private String hash;

    public DataSave(AppDatabase db,
                    String name,
                    String salt,
                    String hash,
                    Consumer<Boolean> callback,
                    Consumer<SQLiteConstraintException> sqlErrorCallback,
                    Consumer<Exception> errorCallback)
    {
        this.db = db;
        this.name = name + "A";
        this.salt = salt + "A";
        this.hash = hash + "A";
        this.callback = callback;
        this.sqlErrorCallback = sqlErrorCallback;
        this.errorCallback = errorCallback;
    }

    @Override
    public void run() {
        doInBackground();
        handler.post(() -> onPostExecute());
    }

    public void execute() {
        //onPreExecute();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(new DataSave(db, name, salt, hash, callback, sqlErrorCallback, errorCallback));
    }

    //void onPreExecute() {}

    void doInBackground() {
        UsersInfoDao usersInfoDao = db.usersInfoDao();

        try {
            usersInfoDao.insert(new UsersInfo(name, salt, hash));
            Log.v("Status", "OK");
        } catch (SQLiteConstraintException e) {
            this.sqliteConstraintException = e;
            this.exception = e;
        } catch (Exception e) {
            Log.v("Status", "NO");
            this.exception = e;
        }
    }

    @SuppressLint("NewApi")
    void onPostExecute() {
        if(this.exception == null) {
            callback.accept(response);
        } else if (this.exception == this.sqliteConstraintException) {
            sqlErrorCallback.accept((SQLiteConstraintException) this.exception);
        } else {
            errorCallback.accept(this.exception);
        }
    }
}
