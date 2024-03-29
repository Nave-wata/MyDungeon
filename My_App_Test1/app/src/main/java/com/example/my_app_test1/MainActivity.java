package com.example.my_app_test1;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // タイトル
        setScreenTitle();
    }


    private void getImageViewBitmapFromAsset(View iv, String strName) {
        AssetManager assetManager = getAssets();
        InputStream istr = null;
        Image
        try {
            istr = assetManager.open(strName);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Bitmap bitmap = BitmapFactory.decodeStream(istr);
        iv.setImageBitmap(bitmap);
    }

    private void setScreenTitle() {
        setContentView(R.layout.activity_title);
        iv = findViewById(R.id.image_view);
        // 画像
        getImageViewBitmapFromAsset(iv, "title/title.png");

        // スタートボタン
        Button title_Button = findViewById(R.id.start_button);
        title_Button.setOnClickListener(v -> setScreenMain());
    }

    private void setScreenMain() {
        setContentView(R.layout.activity_home);

        EditText editText = findViewById(R.id.Save_Text);
        ImageButton saveButton = findViewById(R.id.save_Button);
        TextView Text1 = findViewById(R.id.textView1);
        TextView Text2 = findViewById(R.id.textView2);
        TextView Text3 = findViewById(R.id.textView3);

        saveButton.setOnClickListener(v -> {
            String text = editText.getText().toString();

            if (Text1.getText().toString() == getString(R.string.NullText)) {
                Text1.setText(text);
            } else if (Text2.getText().toString() == getString(R.string.NullText)) {
                Text2.setText(text);
            } else if (Text3.getText().toString() == getString(R.string.NullText)) {
                Text3.setText(text);
            }
        });

    }
}
