package com.example.mainproject.asynchronous.userspossession;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;

import com.example.mainproject.BaseStatusFragment;
import com.example.mainproject.asynchronous.AppDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class UpdatePossession implements Runnable {
    final android.os.Handler handler = new Handler(Looper.getMainLooper());
    final Consumer<Boolean> callback;
    final Consumer<Exception> errorCallback;
    private Exception exception;
    final AppDatabase db;
    final String name;
    final byte[] _DP;
    final byte[] _MONEY;

    public UpdatePossession(AppDatabase db,
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
        executorService.submit(new com.example.mainproject.asynchronous.usersinfo.UpdatePossession(db, name, _DP, _MONEY, callback, errorCallback));
    }

    //void onPreExecute() {}

    void doInBackground() {
        UsersPossessionDao usersPossessionDao = db.usersPossessionDao();
        BaseStatusFragment BSF = new BaseStatusFragment();

        try {
            usersPossessionDao.updatePossessionTask(name, BSF.CastString(_DP), BSF.CastString(_MONEY));
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
