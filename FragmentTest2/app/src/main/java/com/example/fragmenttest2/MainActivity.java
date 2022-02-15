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

        SetImage setImage = new SetImage();

        setImage.setImageViewBitmapFromAsset("title/title.png", "image_view1");
        setImage.setImageButtonBitmapFromAsset("title/tabi_start.png", "image_button1");
    }

    private class SetImage {
        private void setImageViewBitmapFromAsset(String strName, String strID) {
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

        private void setImageButtonBitmapFromAsset(String strName, String strID) {
            int ID = getResources().getIdentifier(strID, "id", getPackageName());
            ImageButton imageButton = (ImageButton) findViewById(ID);
            AssetManager assetManager = getAssets();
            InputStream istr = null;

            try {
                istr = assetManager.open(strName);
            } catch (IOException e) {
                e.printStackTrace();
            }

            Bitmap bitmap = BitmapFactory.decodeStream(istr);
            imageButton.setImageBitmap(bitmap);
        }
    }
}