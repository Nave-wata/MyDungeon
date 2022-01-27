package com.example.db_test1;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.sql.Timestamp;
import java.util.List;

// import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tv = findViewById(R.id.index);
        Button bt = findViewById(R.id.button);
        AppDatabase db = AppDatabaseSingleton.getInstance(getApplicationContext());

        bt.setOnClickListener(new ButtonClickListener(this, db, tv));

    }

    private static class ButtonClickListener implements View.OnClickListener {
        private final Activity activity;
        private final AppDatabase db;
        private final TextView tv;

        private ButtonClickListener(Activity activity, AppDatabase db, TextView tv) {
            this.activity = activity;
            this.db = db;
            this.tv = tv;
        }

        @Override
        public void onClick(View view) {
            new DataStoreAsyncTask(db, activity, tv).execute();

        }
    }

    private static class DataStoreAsyncTask extends AsyncTask<Void, Void, Integer> {
        private final WeakReference<Activity> weakActivity;
        private final AppDatabase db;
        @SuppressLint("StaticFieldLeak")
        private final TextView textView;
        private StringBuilder sb;

        public DataStoreAsyncTask(AppDatabase db, Activity activity, TextView textView) {
            this.db = db;
            weakActivity = new WeakReference<>(activity);
            this.textView = textView;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            AccessTimeDao accessTimeDao = db.accessTimeDao();
            accessTimeDao.insert(new AccessTime(new Timestamp(System.currentTimeMillis()).toString()));

            sb = new StringBuilder();
            List<AccessTime> atList = accessTimeDao.getAll();
            for (AccessTime at: atList) {
                sb.append(at.getAccessTime()).append("\n");
            }

            return 0;
        }

        @Override
        protected void onPostExecute(Integer code) {
            Activity activity = weakActivity.get();
            if(activity == null) {
                return;
            }

            textView.setText(sb.toString());

        }
    }

}