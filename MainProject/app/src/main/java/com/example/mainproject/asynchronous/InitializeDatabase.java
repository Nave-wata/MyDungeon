package com.example.mainproject.asynchronous;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.SharedPreferences;

import com.example.mainproject.asynchronous.usersinfo.UsersInfo;
import com.example.mainproject.asynchronous.usersinfo.UsersInfoDao;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class InitializeDatabase implements Runnable {
    private final AppDatabase db;
    private final Activity activity;

    public InitializeDatabase(AppDatabase db, Activity activity) {
        this.db = db;
        this.activity = activity;
    }

    @Override
    public void run() {
        doInBackground();
        //handler.post(() -> onPostExecute());
    }

    public void execute() {
        // onPreExecute();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(new InitializeDatabase(db, activity));
    }

    // void onPreExecute() {}

    void doInBackground() {
        String NEXT_INFO = "NextInfo";
        String DS_Flag = "Flag";
        String DS_Name = "Name";
        String DS_Passwd = "Password";
        String name = "hgnkHVbHZsSdyiArXL4MNiwnWYdFxaQEEuNstxG6";
        String password = "d099c99b3d444150d6b92c2197170f7263673bbaf80b8b03f437b287694e9eca36ce29dd32f24f03ff05fd088bba7d2322bf3310a800f5210f947a7d22365024";
        String salt = "aGY4GzyZnLaDvthtIwY";
        String hash = "6f715ba2cc56b5c32fee6d688d40ca33bcba9a18443eab40713ed08b0ecd49f230f6448cd97e63fc120f26e656adfa5396a86a259bec185e46e44ee151a8811";
        UsersInfoDao usersInfoDao = db.usersInfoDao();
        SharedPreferences dataStore = activity.getSharedPreferences(NEXT_INFO, MODE_PRIVATE);
        SharedPreferences.Editor editor = dataStore.edit();

        editor.putBoolean(DS_Flag, true);
        editor.putString(DS_Name, name);
        editor.putString(DS_Passwd, password);
        editor.apply();

        try {
            usersInfoDao.insert(new UsersInfo(name, salt, hash));
        } catch (Exception ignored) {
        }
    }

    //void onPostExecute() {}
}