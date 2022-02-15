package com.example.fragmenttest2;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;
import android.widget.ImageView;


public class MainActivity extends AppCompatActivity {
    static {
        System.loadLibrary("fragmenttest2");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AssetManager assetManager = getAssets();
        SetImage setImage = new SetImage(assetManager);
        ImageView imageView = findViewById(R.id.image_view1);
        ImageButton imageButton = findViewById(R.id.image_button1);

        setImage.setImageViewBitmapFromAsset(imageView, "title/title.png");
        setImage.setImageButtonBitmapFromAsset(imageButton, "title/tabi_start.png");
    }
}