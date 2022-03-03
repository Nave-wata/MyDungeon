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
import com.example.mainproject.title.TitleActivity;

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
        fragmentTransaction.replace(R.id.BaseTransitionContainer, BaseTransitionFragment.newInstance(UserName));
        fragmentTransaction.commit();
        Log.v("MainActivity", "onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.v("MainActivity", "onStart");
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onStop() {
        super.onStop();
        Log.v("MainActivity", "onStop");
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
                b->finish(),
                e->{}
        ).execute();
    }
}
