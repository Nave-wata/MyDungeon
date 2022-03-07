package com.example.mainproject;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.mainproject.asynchronous.AppDatabase;
import com.example.mainproject.asynchronous.AppDatabaseSingleton;
import com.example.mainproject.asynchronous.usersinfo.UpdatePossession;
import com.example.mainproject.asynchronous.usersinfo.UpdateTime;
import com.example.mainproject.title.TitleActivity;

import java.time.LocalDateTime;


public class MainActivity extends AppCompatActivity {
    public static String UserName;
    public static boolean AppFirstFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = this.getIntent();
        UserName = intent.getStringExtra(TitleActivity.EXTRA_DATA);
        AppFirstFlag = true;
    }

    @Override
    public void onStart() {
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

        AppFirstFlag = false;
        LocalDateTime nowTime = LocalDateTime.now();
        int nowYear = nowTime.getYear();
        int nowMonth = nowTime.getMonthValue();
        int nowDay = nowTime.getDayOfMonth();
        int nowHour = nowTime.getHour();
        int nowMinute = nowTime.getMinute();
        int nowSecond = nowTime.getSecond();
        AppDatabase db = AppDatabaseSingleton.getInstance(getApplicationContext());

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

        new UpdatePossession(
                db,
                UserName,
                BaseStatusFragment.DP,
                BaseStatusFragment.MONEY,
                b->{},
                e->{}
        ).execute();
    }
}
