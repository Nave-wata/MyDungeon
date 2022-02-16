package com.example.fragmenttest2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    static {
        System.loadLibrary("fragmenttest2");
    }
    public native String stringFromJNI();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    protected void ChangeDungeonActivity() {
        Intent intent = new Intent(getApplication(), DungeonActivity.class);
        startActivity(intent);
        finish();
    }

    protected void ChangeMonsterActivity() {
        Intent intent = new Intent(getApplication(), MonsterActivity.class);
        startActivity(intent);
        finish();
    }
}