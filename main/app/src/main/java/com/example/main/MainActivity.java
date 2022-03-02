package com.example.main;

import static java.time.LocalDateTime.parse;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.main.title.TitleActivity;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;


public class MainActivity extends AppCompatActivity {
    private String UserName;
    private String Prev_Time = "PrevTime";
    private String DS_Time = "Time";
    private SharedPreferences.Editor editor;
    private String beforeTimeStr;
    private String afterTimeStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences dataStore = getSharedPreferences(Prev_Time, MODE_PRIVATE);
        editor = dataStore.edit();

        beforeTimeStr = dataStore.getString(Prev_Time, null);

        Intent intent = this.getIntent();
        UserName = intent.getStringExtra(TitleActivity.EXTRA_DATA);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.BaseContainer, BaseFragment.newInstance(UserName));
        fragmentTransaction.commit();
    }

    @SuppressLint("NewApi")
    @Override
    public void onPause() {
        super.onPause();

        afterTimeStr = LocalDateTime.now().toString();
        Log.v("My_Time", afterTimeStr);
        //LocalDateTime beforeTime = LocalDateTime.parse(beforeTimeStr);
        //LocalDateTime afterTime = LocalDateTime.parse(afterTimeStr);

        //long Day = ChronoUnit.SECONDS.between(beforeTime, afterTime);
        LocalDateTime beginSummerVacation = LocalDateTime.of(2021, 7, 20, 11, 00);// 夏休みは終業式が終わったタイミングから
        LocalDateTime endSummerVacation = LocalDateTime.of(2021, 9, 1, 8, 00);// 始業式が始まるタイミングまで
        Duration summerVacationDuration = Duration.between(beginSummerVacation, endSummerVacation);// 期間分の時間を取得する
    }
}
