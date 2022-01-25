package com.example.my_app_test1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title);

        Button sendButton = findViewById(R.id.start_button);
        sendButton.setOnClickListener(v -> {
            Intent intent = new Intent(getApplication(), TitleActivity.class);
            startActivity(intent);
        });
    }
}