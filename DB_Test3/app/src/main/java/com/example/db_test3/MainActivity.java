package com.example.db_test3;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppDatabase db = AppDatabaseSingleton.getInstance(getApplicationContext());

        test(db);
    }

    private void test(AppDatabase db) {
        TextsDao textsDao = db.textsDao();

        textsDao.insert(new Texts("Hello World!"));
        Log.v("testsDao.insert", "OK!");

        List<Texts> texts = textsDao.getAll();
        for(Texts text: texts) {
            Log.v("textsDao.getAll()", "" + text);
        }
    }
}