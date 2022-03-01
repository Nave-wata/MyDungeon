package com.example.fragmenttest2;

import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fragmenttest2.title.TitleActivity;


public class MainActivity extends AppCompatActivity {
    public String UserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = this.getIntent();
        UserName = intent.getStringExtra(TitleActivity.EXTRA_DATA);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.BaseContainer, BaseFragment.newInstance(UserName));
        fragmentTransaction.commit();
    }
}
