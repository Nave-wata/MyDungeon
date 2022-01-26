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

        // タイトル
        setScreenTitle();
    }


    private void getImageViewBitmapFromAsset(String strName, String strID) {
        int ID = getResources().getIdentifier(strID, "id", getPackageName());
        ImageView imageView = (ImageView)findViewById(ID);
        AssetManager assetManager = getAssets();
        InputStream istr = null;

        try {
            istr = assetManager.open(strName);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Bitmap bitmap = BitmapFactory.decodeStream(istr);
        imageView.setImageBitmap(bitmap);
    }

    private void setScreenTitle() {
        setContentView(R.layout.activity_title);

        // 画像
        getImageViewBitmapFromAsset("title/title.png", "image_view");

        // スタートボタン
        Button title_Button = findViewById(R.id.start_button);
        title_Button.setOnClickListener(v -> setScreenMain());
    }

    private void setScreenMain() {
        setContentView(R.layout.activity_home);
    }
}
