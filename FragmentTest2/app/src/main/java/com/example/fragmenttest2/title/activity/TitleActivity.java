package com.example.fragmenttest2.title.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fragmenttest2.MainActivity;
import com.example.fragmenttest2.R;

public class TitleActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_FragmentTest2);
        setContentView(R.layout.activity_title);
    }

    public void ChangeActivity() {
        Intent intent = new Intent(getApplication(), MainActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }
}