package com.example.mainproject;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.mainproject.asynchronous.AppDatabase;
import com.example.mainproject.asynchronous.AppDatabaseSingleton;
import com.example.mainproject.asynchronous.possessioninfo.DataSave;
import com.example.mainproject.asynchronous.possessioninfo.GetLine;
import com.example.mainproject.asynchronous.possessioninfo.PossessionInfo;
import com.example.mainproject.title.TitleActivity;

import java.time.Duration;
import java.time.LocalDateTime;


public class MainActivity extends AppCompatActivity {
    private String UserName;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = this.getIntent();
        UserName = intent.getStringExtra(TitleActivity.EXTRA_DATA);
        final AppDatabase db = AppDatabaseSingleton.getInstance(getApplicationContext());

        new GetLine(
                db,
                UserName,
                b->{
                    LocalDateTime nowTime = LocalDateTime.now();
                    int nowYear = nowTime.getYear();
                    int nowMonth = nowTime.getMonthValue();
                    int nowDay = nowTime.getDayOfMonth();
                    int nowHour = nowTime.getHour();
                    int nowMinute = nowTime.getMinute();
                    int nowSecond = nowTime.getSecond();
                    int beforeYear = 0, beforeMonth = 0 , beforeDay = 0, beforeHour = 0, beforeMinute = 0, beforeSecond = 0;

                    for (PossessionInfo pi: b) {
                        beforeYear = pi.getYear();
                        beforeMonth = pi.getMonth();
                        beforeDay = pi.getDay();
                        beforeHour = pi.getHour();
                        beforeMinute = pi.getMinute();
                        beforeSecond = pi.getSecond();
                    }

                    LocalDateTime BeforeTime = LocalDateTime.of(beforeYear, beforeMonth, beforeDay, beforeHour, beforeMinute, beforeSecond);// 夏休みは終業式が終わったタイミングから
                    LocalDateTime NowTime = LocalDateTime.of(nowYear, nowMonth, nowDay, nowHour, nowMinute, nowSecond);// 始業式が始まるタイミングまで
                    Duration summerVacationDuration = Duration.between(BeforeTime, NowTime);// 期間分の時間を取得する
                    long diffSecond = summerVacationDuration.getSeconds();
                    Log.v("diffSecond", String.valueOf(diffSecond));

                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.BaseTransitionContainer, BaseTransitionFragment.newInstance(UserName));
                    fragmentTransaction.commit();
                },
                e->{}
        ).execute();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.BaseTransitionContainer, BaseTransitionFragment.newInstance(UserName));
        fragmentTransaction.add(R.id.BaseStatusContainer, BaseStatusFragment.newInstance(UserName));
        fragmentTransaction.commit();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onStop() {
        super.onStop();
        AppDatabase db = AppDatabaseSingleton.getInstance(getApplicationContext());
        LocalDateTime nowTime = LocalDateTime.now();
        int nowYear = nowTime.getYear();
        int nowMonth = nowTime.getMonthValue();
        int nowDay = nowTime.getDayOfMonth();
        int nowHour = nowTime.getHour();
        int nowMinute = nowTime.getMinute();
        int nowSecond = nowTime.getSecond();

        new DataSave(
                db,
                UserName,
                nowYear,
                nowMonth,
                nowDay,
                nowHour,
                nowMinute,
                nowSecond,
                b->{},
                e->{}
        ).execute();
    }
}
