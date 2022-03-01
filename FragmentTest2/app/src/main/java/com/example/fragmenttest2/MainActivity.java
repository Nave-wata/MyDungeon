package com.example.fragmenttest2;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fragmenttest2.home.HomeFragment;
import com.example.fragmenttest2.title.TitleActivity;

import java.util.Objects;


public class MainActivity extends AppCompatActivity {
    public static String UserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = this.getIntent();
        UserName = intent.getStringExtra(TitleActivity.EXTRA_DATA);
        Log.v("UserName = ", UserName);

        FragmentTransaction fragmentTransaction = Objects.requireNonNull(getFragmentManager()).beginTransaction();
        fragmentTransaction.replace(R.id.baseContainer, BaseFragment.newInstance());
        fragmentTransaction.commit();
    }
}
