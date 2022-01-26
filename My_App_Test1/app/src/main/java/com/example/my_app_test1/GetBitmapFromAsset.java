package com.example.my_app_test1;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

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

public class GetBitmapFromAsset extends AppCompatActivity {

    public void getImageViewBitmapFromAsset(String strName, String strID) {
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
}
