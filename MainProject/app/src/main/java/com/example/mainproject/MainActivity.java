package com.example.mainproject;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.mainproject.asynchronous.AppDatabase;
import com.example.mainproject.asynchronous.AppDatabaseSingleton;
import com.example.mainproject.asynchronous.TimerPossession;
import com.example.mainproject.asynchronous.usersinfo.GetUsersInfo;
import com.example.mainproject.asynchronous.usersinfo.UpdateTime;
import com.example.mainproject.asynchronous.usersinfo.UsersInfo;
import com.example.mainproject.asynchronous.userspossessioninfo.GetUsersPossessionInfo;
import com.example.mainproject.asynchronous.userspossessioninfo.UpdateUsersPossessionInfo;
import com.example.mainproject.asynchronous.userspossessioninfo.UsersPossessionInfo;
import com.example.mainproject.title.TitleActivity;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    public static String UserName;
    public static TimerPossession timerPossession;
    public static TextView text_DP;
    public static TextView text_MONEY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = this.getIntent();
        UserName = intent.getStringExtra(TitleActivity.EXTRA_DATA);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onStart() {
        super.onStart();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.BaseTransitionContainer, BaseTransitionFragment.newInstance(UserName));
        fragmentTransaction.add(R.id.BaseStatusContainer, BaseStatusFragment.newInstance(UserName));
        fragmentTransaction.commit();

        final AppDatabase db = AppDatabaseSingleton.getInstance(getApplicationContext());
        new GetUsersInfo(
            db,
            UserName,
            b->{
                new GetUsersPossessionInfo(
                    db,
                    UserName,
                    c->{
                        long diffSecond = 0;
                        byte[] _DP = null;
                        byte[] _MONEY = null;
                        try {
                            diffSecond = getTimeDiff(b);
                            for (UsersPossessionInfo up: c) {
                                _DP = up.getDP();
                                _MONEY = up.getMoney();
                            }
                        } catch (Exception e) {
                            diffSecond = 0;
                        } finally {
                            new BaseStatusFragment().initDiffTime(diffSecond, _DP, _MONEY);
                        }
                    },
                    e->{}
                ).execute();
            },
            e->{}
        ).execute();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onStop() {
        super.onStop();

        text_DP.setText(null);
        text_MONEY.setText(null);
        text_DP = null;
        text_MONEY = null;
        timerPossession.Stop();
        timerPossession = null;
        AppDatabase db = AppDatabaseSingleton.getInstance(getApplicationContext());
        LocalDateTime nowTime = LocalDateTime.now();
        int nowYear = nowTime.getYear();
        int nowMonth = nowTime.getMonthValue();
        int nowDay = nowTime.getDayOfMonth();
        int nowHour = nowTime.getHour();
        int nowMinute = nowTime.getMinute();
        int nowSecond = nowTime.getSecond();

        new UpdateTime(
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

        new UpdateUsersPossessionInfo(
                db,
                UserName,
                BaseStatusFragment.DP,
                BaseStatusFragment.MONEY,
                b->{},
                e->{}
        ).execute();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public long getTimeDiff(List<UsersInfo> b) {
        long diffSecond = 0;

        try {
            LocalDateTime nowTime = LocalDateTime.now();
            int nowYear = nowTime.getYear();
            int nowMonth = nowTime.getMonthValue();
            int nowDay = nowTime.getDayOfMonth();
            int nowHour = nowTime.getHour();
            int nowMinute = nowTime.getMinute();
            int nowSecond = nowTime.getSecond();
            int beforeYear = 0, beforeMonth = 0 , beforeDay = 0, beforeHour = 0, beforeMinute = 0, beforeSecond = 0;

            for (UsersInfo pi: b) {
                beforeYear = pi.getYear();
                beforeMonth = pi.getMonth();
                beforeDay = pi.getDay();
                beforeHour = pi.getHour();
                beforeMinute = pi.getMinute();
                beforeSecond = pi.getSecond();
            }

            LocalDateTime BeforeTime = LocalDateTime.of(beforeYear, beforeMonth, beforeDay, beforeHour, beforeMinute, beforeSecond);
            LocalDateTime NowTime = LocalDateTime.of(nowYear, nowMonth, nowDay, nowHour, nowMinute, nowSecond);
            Duration duration= Duration.between(BeforeTime, NowTime);
            diffSecond = duration.getSeconds();
        } catch (Exception e) {
        } finally {
            return diffSecond;
        }
    }
}
