package com.example.fragmenttest2;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.fragmenttest2.databinding.ActivityMainBinding;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    static {
        System.loadLibrary("fragmenttest2");
    }
    public native String stringFromJNI();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AssetManager assetManager = getAssets();
        SetImage setImage = new SetImage();
        ImageView imageView = findViewById(R.id.image_view1);
        ImageButton imageButton = findViewById(R.id.image_button1);

        setImage.setImageViewBitmapFromAsset(imageView, assetManager,"title/title.png");
        setImage.setImageButtonBitmapFromAsset(imageButton, assetManager, "title/tabi_start.png");
    }
}