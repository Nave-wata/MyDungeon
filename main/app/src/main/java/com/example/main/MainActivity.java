package com.example.main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.main.title.TitleActivity;

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
        LocalDateTime beforeTime = LocalDateTime.parse(beforeTimeStr);
        LocalDateTime afterTime = LocalDateTime.parse(afterTimeStr);

        long Day = ChronoUnit.SECONDS.between(beforeTime, afterTime);
    }
}
