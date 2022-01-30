package com.example.db_test3;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView firstText = findViewById(R.id.firstText);
        AppDatabase db = AppDatabaseSingleton.getInstance(getApplicationContext());
        Test tes = new Test(this, db, firstText);
    }

    private class Test {
        private Activity activity;
        private AppDatabase db;
        private TextView firstText;

        private Test(Activity activity, AppDatabase db, TextView firstText) {
            this.activity = activity;
            this.db = db;
            this.firstText = firstText;

            new DataStoreAsyncTask(db, activity, firstText);
        }
    }

    private static class DataStoreAsyncTask extends AsyncTask<Void, Void, Integer> {
        private WeakReference<Activity> weakActivity;
        private AppDatabase db;
        private TextView firstText;
        private StringBuilder sb;

        public DataStoreAsyncTask(AppDatabase db, Activity activity, TextView firstText) {
            this.db = db;
            weakActivity = new WeakReference<>(activity);
            this.firstText = firstText;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            TextsDao textsDao = db.textsDao();
            textsDao.insert(new Texts("Hello"));

            sb = new StringBuilder();
            List<Texts> Text = textsDao.getAll();
            for (Texts ts: Text) {
                sb.append(ts.getText()).append("\n");
            }

            return 0;
        }

        @Override
        protected void onPostExecute(Integer code) {
            Activity activity = weakActivity.get();
            if(activity == null) {
                return;
            }
            firstText.setText(sb.toString());
        }
    }
}