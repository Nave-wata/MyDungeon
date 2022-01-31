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
import java.util.regex.*;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textOutName = findViewById(R.id.textOutName);

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

        bt.setOnClickListener(new Test(this, db, textOutName, editName, editYear, editMonth, editDay));
    }

    private class Test implements View.OnClickListener {
        private Activity activity;
        private AppDatabase db;
        private TextView textOutName;
        private EditText editName;
        private EditText editYear;
        private EditText editMonth;
        private EditText editDay;

        private Test(Activity activity, AppDatabase db, TextView textOutName, EditText editName, EditText editYear, EditText editMonth, EditText editDay) {
            this.activity = activity;
            this.db = db;
            this.textOutName = textOutName;
            this.editName = editName;
            this.editYear = editYear;
            this.editMonth = editMonth;
            this.editDay = editDay;

            new DataStoreAsyncTask(db, activity, textOutName, editName, editYear, editMonth, editDay);
        }

        @Override
        public void onClick(View view) {
            boolean flag = true;
            String regex = "(1|2|3|4|5|6|7|8|9|0)";
            String splitText = "";
            String name = editName.getText().toString();
            String year = editYear.getText().toString();
            String month = editMonth.getText().toString();
            String day = editDay.getText().toString();
            String[] Name = name.split(splitText);
            String[] Year = year.split(splitText);
            String[] Month = month.split(splitText);
            String[] Day = day.split(splitText);

            for (String str: Name) {
                if (str.matches(regex)) {
                    editName.setError(getString(R.string.errorNotString));
                    flag = false;
                    break;
                }
            }
            if (!year.matches(regex)) {
                editYear.setError(getString(R.string.errorNotNum));
                flag = false;
            }
            if (!month.matches(regex)) {
                editMonth.setError(getString(R.string.errorNotNum));
                flag = false;
            }
            if (!day.matches(regex)) {
                editDay.setError(getString(R.string.errorNotNum));
                flag = false;
            }
            if (flag) {
                new DataStoreAsyncTask(db, activity, textOutName, editName, editYear, editMonth, editDay).execute();
                textOutName.setText("Yes!");
            } else {
                textOutName.setText("NO");
            }
        }

        public boolean editTextError(EditText editText, int error) {
            String regex = "(1|2|3|4|5|6|7|8|9|0)";
            String splitText = "";
            String text = editText.getText().toString();
            String[] str = text.split(splitText);

            if (error == 1) {
                for (String s : str) {
                    if (s.matches(regex)) {
                        editText.setError(getString(R.string.errorNotString));
                        return false;
                    }
                }
            } else if (error == 2) {
                for (String s : str) {
                    if (s.matches(regex)) {
                        editText.setError(getString(R.string.errorNotNum));
                        return false;
                    }
                }
            }
            return true;
        }
    }

    private static class DataStoreAsyncTask extends AsyncTask<Void, Void, Integer> {
        private WeakReference<Activity> weakActivity;
        private AppDatabase db;
        private TextView textOutName;
        private StringBuilder sb;
        private EditText editName;
        private EditText editYear;
        private EditText editMonth;
        private EditText editDay;

        public DataStoreAsyncTask(AppDatabase db, Activity activity, TextView textOutNamem, EditText editName, EditText editYear, EditText editMonth, EditText editDay) {
            this.db = db;
            weakActivity = new WeakReference<>(activity);
            this.textOutName = textOutNamem;
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
        }
    }
}