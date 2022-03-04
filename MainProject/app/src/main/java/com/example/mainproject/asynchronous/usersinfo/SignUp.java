package com.example.mainproject.asynchronous.usersinfo;


import android.annotation.SuppressLint;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.example.mainproject.asynchronous.AppDatabase;
import com.example.mainproject.asynchronous.userspossessioninfo.UsersPossessionInfo;
import com.example.mainproject.asynchronous.userspossessioninfo.UsersPossessionInfoDao;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class SignUp implements Runnable {
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
    private final int nowYear;
    private final int nowMonth;
    private final int nowDay;
    private final int nowHour;
    private final int nowMinute;
    private final int nowSecond;

    public SignUp(AppDatabase db,
                  String name,
                  String salt,
                  String hash,
                  int nowYear,
                  int nowMonth,
                  int nowDay,
                  int nowHour,
                  int nowMinute,
                  int nowSecond,
                  Consumer<Boolean> callback,
                  Consumer<SQLiteConstraintException> sqlErrorCallback,
                  Consumer<Exception> errorCallback)
    {
        this.db = db;
        this.name = name;
        this.salt = salt;
        this.hash = hash;
        this.nowYear = nowYear;
        this.nowMonth = nowMonth;
        this.nowDay = nowDay;
        this.nowHour = nowHour;
        this.nowMinute = nowMinute;
        this.nowSecond  = nowSecond;
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
                new SignUp(
                        db,
                        name,
                        salt,
                        hash,
                        nowYear,
                        nowMonth,
                        nowDay,
                        nowHour,
                        nowMinute,
                        nowSecond,
                        callback,
                        sqlErrorCallback,
                        errorCallback));
    }

    //void onPreExecute() {}

    void doInBackground() {
        UsersInfoDao usersInfoDao = db.usersInfoDao();
        UsersPossessionInfoDao usersPossessionInfoDao = db.possessionInfoDao();

        try {
            usersInfoDao.signUpTask(name, salt, hash, nowYear, nowMonth, nowDay, nowHour, nowMinute, nowSecond);
            usersPossessionInfoDao.insetTask(new UsersPossessionInfo(name, 100, 100));
            Log.v("HOGE", "" );
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