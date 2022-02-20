package com.example.fragmenttest2.title.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fragmenttest2.MainActivity;
import com.example.fragmenttest2.R;
import com.example.fragmenttest2.asynchronous.AsyncRunnable;
import com.example.fragmenttest2.asynchronous.CallBacks;

public class TitleActivity extends AppCompatActivity {
    public static String EXTRA_DATA = "com.example.fragmenttest2.activity";
    public String[] URLs = new String[] {"http://192.168.3.21:8000/one",
                                         "http://192.168.3.21:8000/two",
                                         "http://192.168.3.21:8000/three"};
    public String[] str = new String[URLs.length];
    public int i;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_FragmentTest2);
        setContentView(R.layout.activity_title);

        str[0] = "1";
        str[1] = "2";
        str[2] = "3";
    }

    public void ChangeActivity() {
        Intent intent = new Intent(getApplication(), MainActivity.class);
        intent.putExtra(EXTRA_DATA, str);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }
}