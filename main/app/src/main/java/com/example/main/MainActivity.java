package com.example.main;

import static java.time.LocalDateTime.parse;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.main.title.TitleActivity;

import java.time.Duration;
import java.time.LocalDateTime;


public class MainActivity extends AppCompatActivity {
    private String UserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = this.getIntent();
        UserName = intent.getStringExtra(TitleActivity.EXTRA_DATA);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.BaseContainer, BaseFragment.newInstance(UserName));
        fragmentTransaction.commit();
    }

    @SuppressWarnings("Since15")
    @RequiresApi(api = Build.VERSION_CODES.S)
    @Override
    public void onPause() {
        super.onPause();

        SharedPreferences dataStore = getSharedPreferences("Prev_TIme", MODE_PRIVATE);
        SharedPreferences.Editor editor = dataStore.edit();

        LocalDateTime nowTime = LocalDateTime.now();
        int nowYear = nowTime.getYear();
        int nowMonth = nowTime.getMonthValue();
        int nowDay = nowTime.getDayOfMonth();
        int nowHour = nowTime.getHour();
        int nowMinute = nowTime.getMinute();
        int nowSecond = nowTime.getSecond();

        int beforeYear = dataStore.getInt("Year", 0);
        int beforeMonth = dataStore.getInt("Month", 0);
        int beforeDay = dataStore.getInt("Day", 0);
        int beforeHour = dataStore.getInt("Hour", 0);
        int beforeMinute = dataStore.getInt("Minute", 0);
        int beforeSecond = dataStore.getInt("Second", 0);

        editor.putInt("Year", nowYear);
        editor.putInt("Month", nowMonth);
        editor.putInt("Day", nowDay);
        editor.putInt("Hour", nowHour);
        editor.putInt("Minute", nowMinute);
        editor.putInt("Second", nowSecond);
        editor.apply();

        LocalDateTime beforeTime = LocalDateTime.of(beforeYear, beforeMonth, beforeDay, beforeHour, beforeMinute);
        LocalDateTime nowTimes = LocalDateTime.of(nowYear, nowMonth, nowDay, nowHour, nowMinute);
        Duration duration = Duration.between(beforeTime, nowTimes);// 期間分の時間を取得する
        long diff_min = duration.toMinutes();
        long diff_sec = diff_min * 60 + (nowSecond - beforeSecond);
        Log.v("diff_Minute", " = " + diff_sec);
    }
}
