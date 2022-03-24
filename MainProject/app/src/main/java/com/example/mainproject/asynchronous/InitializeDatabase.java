package com.example.mainproject.asynchronous;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.mainproject.asynchronous.dungeonlayout.DungeonLayout;
import com.example.mainproject.asynchronous.dungeonlayout.DungeonLayoutDao;
import com.example.mainproject.asynchronous.usersapptimes.UsersAppTimes;
import com.example.mainproject.asynchronous.usersapptimes.UsersAppTimesDao;
import com.example.mainproject.asynchronous.usersinfo.UsersInfo;
import com.example.mainproject.asynchronous.usersinfo.UsersInfoDao;
import com.example.mainproject.asynchronous.userspossession.UsersPossession;
import com.example.mainproject.asynchronous.userspossession.UsersPossessionDao;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class InitializeDatabase implements Runnable {
    final AppDatabase db;
    final Activity activity;

    public InitializeDatabase(AppDatabase db, Activity activity) {
        this.db = db;
        this.activity = activity;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    void doInBackground() {
        String NEXT_INFO = "NextInfo";
        String DS_Flag = "Flag";
        String DS_Name = "Name";
        String DS_Passwd = "Password";
        String name = "developer"; // 本番ではユーザー名の上限以上の長さで（なぜかある程度の長さのある名前が初期データにないと使えない）
        String password = "developer";
        String salt = "VKBykbmtkHNngaOzCd5bnVfqvgCXPZ7MXz4G5i4QKad8yNxCIxRpCwGw9A5SEbkTVab3MyKaEcMDJHB5Sm2zWnrLYPW0I0iT9M1sboKizv6DgsXl8iLzZmWk5yObQKZLpvPhWyMNVywIh0TEMZGeddIHoXI1aYJk3FblYoY2HESnvAFfyF9srvnGO4MdiEPynypc7eIqqlqitZ8CfKQAyX2VJYqQDxyLjVbH9MEZmE4Z71RcnYy7GWLyaRHf0ETYeNwDPLT9ZARwncg9hzSsnft3rTOPveCad7RQqP7NFyGGhIdxhNeag8YvYb4iCJdWjLyrkFNwCMfe7EIBmCDiKAeTkRS7BeaThkoj0rB96PixYpYrMKSZ9R0TB3be2VTiqeGSaq3EY83lrwpLaYwrYeDHXgYXK5e8L4f79Sx14N1PAtG7Kf3Zd15dsRacS31inhWOHhsFJaQZd2S7VbyvMSfycTKWgBY38Yg3IACVsLBcmEOaYiqZQkzmzb1R3wORv3e1D30RCwhXYbe";
        String hash = "133b3adcaaf48984475ebf2f357cf250579ca1862066f4e86e0c6f1de33e9bea415e67945e6c936cdae2da4ed8cc14afbf7e0f6816539b34419e2b402c9b9c53";
        String DP = "1000";
        String Money = "1000";
        LocalDateTime nowTime = LocalDateTime.now();
        int nowYear = nowTime.getYear();
        int nowMonth = nowTime.getMonthValue();
        int nowDay = nowTime.getDayOfMonth();
        int nowHour = nowTime.getHour();
        int nowMinute = nowTime.getMinute();
        int nowSecond = nowTime.getSecond();
        final String row0  = "0,0,0,0,0,0,0,0,0,3,0,0,0,0,0,0,0,0,0,0";
        final String row1  = "0,0,0,0,0,0,0,0,1,1,1,0,0,0,0,0,0,0,0,0";
        final String row2  = "0,0,0,0,0,0,0,0,1,4,1,0,0,0,0,0,0,0,0,0";
        final String row3  = "0,0,0,0,0,0,0,0,1,1,1,0,0,0,0,0,0,0,0,0";
        final String row4  = "0,0,0,0,0,0,0,0,0,5,0,0,0,0,0,0,0,0,0,0";
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

        UsersInfoDao usersInfoDao = db.usersInfoDao();
        UsersAppTimesDao usersAppTimesDao = db.usersAppTimesDao();
        UsersPossessionDao usersPossessionDao = db.usersPossessionDao();
        DungeonLayoutDao dungeonLayoutDao = db.dungeonLayoutDao();

        // ここに書いてるとチェックボックス外してもアプリ再起したら入力されてます
        SharedPreferences dataStore = activity.getSharedPreferences(NEXT_INFO, MODE_PRIVATE);
        SharedPreferences.Editor editor = dataStore.edit();
        editor.putBoolean(DS_Flag, true);
        editor.putString(DS_Name, name);
        editor.putString(DS_Passwd, password);
        editor.apply();

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
        } catch (Exception ignored) {}
    }

    //void onPostExecute() {}
}