package com.example.my_app_test1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Button start_button = findViewById(R.id.start_button);
        setContentView(R.layout.activity_title);
    }

    public void start_button(View view) {

    }
}