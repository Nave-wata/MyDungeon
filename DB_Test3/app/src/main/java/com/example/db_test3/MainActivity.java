package com.example.db_test3;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView firstText = findViewById(R.id.textOutName);

        EditText editName = findViewById(R.id.editName);
        EditText editYear = findViewById(R.id.editYear);
        EditText editMonth = findViewById(R.id.editMonth);
        EditText editDay = findViewById(R.id.editDay);

        editName.setNextFocusDownId(R.id.editYear);
        editYear.setNextFocusDownId(R.id.editMonth);
        editMonth.setNextFocusDownId(R.id.editDay);

        Button bt = findViewById(R.id.saveButton);
        AppDatabase db = AppDatabaseSingleton.getInstance(getApplicationContext());

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        adapter.add("A");
        adapter.add("B");

        Spinner users = findViewById(R.id.users);
        users.setAdapter(adapter);

        bt.setOnClickListener(new Test(this, db, firstText, editName, editYear, editMonth, editDay));
    }

    private class Test implements View.OnClickListener {
        private Activity activity;
        private AppDatabase db;
        private TextView firstText;
        private EditText editName;
        private EditText editYear;
        private EditText editMonth;
        private EditText editDay;

        private Test(Activity activity, AppDatabase db, TextView firstText, EditText editName, EditText editYear, EditText editMonth, EditText editDay) {
            this.activity = activity;
            this.db = db;
            this.firstText = firstText;
            this.editName = editName;
            this.editYear = editYear;
            this.editMonth = editMonth;
            this.editDay = editDay;

            new DataStoreAsyncTask(db, activity, firstText, editName, editYear, editMonth, editDay);
        }

        @Override
        public void onClick(View view) {
            String Name = editName.getText().toString();
            if (Name.matches("(1|2|3|4|5|6|7|8|9|0)")) {
                editName.setError(getString(R.string.errorNotNum));
            } else {
                new DataStoreAsyncTask(db, activity, firstText, editName, editYear, editMonth, editDay).execute();
            }
        }
    }

    private static class DataStoreAsyncTask extends AsyncTask<Void, Void, Integer> {
        private WeakReference<Activity> weakActivity;
        private AppDatabase db;
        private TextView firstText;
        private StringBuilder sb;
        private EditText editName;
        private EditText editYear;
        private EditText editMonth;
        private EditText editDay;

        public DataStoreAsyncTask(AppDatabase db, Activity activity, TextView firstText, EditText editName, EditText editYear, EditText editMonth, EditText editDay) {
            this.db = db;
            weakActivity = new WeakReference<>(activity);
            this.firstText = firstText;
            this.editName = editName;
            this.editYear = editYear;
            this.editMonth = editMonth;
            this.editDay = editDay;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            TextsDao textsDao = db.textsDao();
            sb = new StringBuilder();

            String text = editName.getText().toString();
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
            //firstText.setText(sb.toString());
        }
    }
}