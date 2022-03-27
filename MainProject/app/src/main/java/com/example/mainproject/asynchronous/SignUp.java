package com.example.mainproject.asynchronous;


import android.annotation.SuppressLint;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Handler;
import android.os.Looper;

import com.example.mainproject.asynchronous.dungeonlayout.DungeonLayout;
import com.example.mainproject.asynchronous.dungeonlayout.DungeonLayoutDao;
import com.example.mainproject.asynchronous.usersapptimes.UsersAppTimes;
import com.example.mainproject.asynchronous.usersapptimes.UsersAppTimesDao;
import com.example.mainproject.asynchronous.usersinfo.UsersInfo;
import com.example.mainproject.asynchronous.usersinfo.UsersInfoDao;
import com.example.mainproject.asynchronous.userspossession.UsersPossession;
import com.example.mainproject.asynchronous.userspossession.UsersPossessionDao;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class SignUp implements Runnable {
    final Handler handler = new Handler(Looper.getMainLooper());
    final Consumer<Boolean> callback;
    final Consumer<SQLiteConstraintException> sqlErrorCallback;
    final Consumer<Exception> errorCallback;
    private Exception exception;
    private SQLiteConstraintException sqliteConstraintException;
    final AppDatabase db;
    final String name;
    final String salt;
    final String hash;
    final String DP = "1000";
    final String Money = "1000";
    final int nowYear;
    final int nowMonth;
    final int nowDay;
    final int nowHour;
    final int nowMinute;
    final int nowSecond;
    final String row0  = "0,0,0,0,0,0,0,0,0,3,0,0,0,0,0,0,0,0,0,0";
    final String row1  = "0,0,0,0,0,0,0,0,1,1,1,0,0,0,0,0,0,0,0,0";
    final String row2  = "0,0,0,0,0,0,0,0,1,4,1,0,0,0,0,0,0,0,0,0";
    final String row3  = "0,0,0,0,0,0,0,0,1,1,1,0,0,0,0,0,0,0,0,0";
    final String row4  = "0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0";
    final String row5  = "0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0";
    final String row6  = "0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0";
    final String row7  = "0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0";
    final String row8  = "0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0";
    final String row9  = "0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0";
    final String row10 = "0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0";
    final String row11 = "0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0";
    final String row12 = "0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0";
    final String row13 = "0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0";
    final String row14 = "0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0";
    final String row15 = "0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0";
    final String row16 = "0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0";
    final String row17 = "0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0";
    final String row18 = "0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0";
    final String row19 = "0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0";

    public SignUp(final AppDatabase db,
                  final String name,
                  final String salt,
                  final String hash,
                  final int nowYear,
                  final int nowMonth,
                  final int nowDay,
                  final int nowHour,
                  final int nowMinute,
                  final int nowSecond,
                  final Consumer<Boolean> callback,
                  final Consumer<SQLiteConstraintException> sqlErrorCallback,
                  final Consumer<Exception> errorCallback) {
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
        UsersAppTimesDao usersAppTimesDao = db.usersAppTimesDao();
        UsersPossessionDao usersPossessionDao = db.usersPossessionDao();
        DungeonLayoutDao dungeonLayoutDao = db.dungeonLayoutDao();

        try {
            usersInfoDao.signUpTask(new UsersInfo(name, salt, hash));
            usersAppTimesDao.signUpTask(new UsersAppTimes(name, nowYear, nowMonth, nowDay, nowHour, nowMinute, nowSecond));
            usersPossessionDao.signUpTask(new UsersPossession(name, DP, Money));
            dungeonLayoutDao.signUpTask(new DungeonLayout(
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
                    row19));
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