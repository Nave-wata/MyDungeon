package com.example.mainproject.asynchronous.userspossessioninfo;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;

import com.example.mainproject.BaseStatusFragment;
import com.example.mainproject.asynchronous.AppDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class UpdateUsersPossessionInfo implements Runnable {
    final Handler handler = new Handler(Looper.getMainLooper());
    final Consumer<Boolean> callback;
    final Consumer<Exception> errorCallback;
    private Exception exception;
    final AppDatabase db;
    final String name;
    final byte[] _DP;
    final byte[] _MONEY;

    public UpdateUsersPossessionInfo(AppDatabase db,
                                     String name,
                                     byte[] _DP,
                                     byte[] _MONEY,
                                     Consumer<Boolean> callback,
                                     Consumer<Exception> errorCallback) {
        this.db = db;
        this.name = name;
        this._DP = _DP;
        this._MONEY = _MONEY;
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
        executorService.submit(new UpdateUsersPossessionInfo(db, name, _DP, _MONEY, callback, errorCallback));
    }

    //void onPreExecute() {}

    void doInBackground() {
        UsersPossessionInfoDao usersPossessionInfoDao = db.possessionInfoDao();
        long DP_A_F = new BaseStatusFragment().CastLong(_DP);
        long MONEY_A_F = new BaseStatusFragment().CastLong(_MONEY);

        try {
            usersPossessionInfoDao.updateTask(name, DP_A_F, MONEY_A_F);
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
