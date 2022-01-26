package com.example.my_app_test1;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title);

        // タイトル
        setScreenTitle();
    }

    private void setScreenTitle() {
        setContentView(R.layout.activity_title);

        // 画像
        GetBitmapFromAsset getBitmap = new GetBitmapFromAsset();
        getBitmap.getImageViewBitmapFromAsset("title/title.png", "image_view");

        // スタートボタン
        Button title_Button = findViewById(R.id.start_button);
        title_Button.setOnClickListener(v -> setScreenMain());
    }

    private void setScreenMain() {
        setContentView(R.layout.activity_home);
    }
}