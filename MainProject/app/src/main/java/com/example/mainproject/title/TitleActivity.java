package com.example.mainproject.title;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.mainproject.MainActivity;
import com.example.mainproject.R;
import com.example.mainproject.asynchronous.AppDatabaseSingleton;
import com.example.mainproject.asynchronous.InitializeDatabase;

public class TitleActivity extends AppCompatActivity {
    public final static String EXTRA_DATA = "com.example.mainproject.title";
    public static String UserName;
    static { System.loadLibrary("mainproject"); }
    public static native String HASH(String name, String password, String salt);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_main);
        setContentView(R.layout.activity_title);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.TitleContainer, TitleFragment.newInstance());
        fragmentTransaction.commit();

        new InitializeDatabase(AppDatabaseSingleton.getInstance(getApplicationContext()), this).execute(); // 他の方法での初期データの登録を考える
    }

    public void ChangeActivity() {
        Intent intent = new Intent(getApplication(), MainActivity.class);
        intent.putExtra(EXTRA_DATA, UserName);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }
}