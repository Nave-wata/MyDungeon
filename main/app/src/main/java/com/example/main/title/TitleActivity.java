package com.example.main.title;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.main.MainActivity;
import com.example.main.R;
import com.example.main.asynchronous.AppDatabaseSingleton;
import com.example.main.asynchronous.InitializeDatabase;

public class TitleActivity extends AppCompatActivity {
    public final static String EXTRA_DATA = "com.example.fragmenttest2.title";
    public static String UserName;

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
