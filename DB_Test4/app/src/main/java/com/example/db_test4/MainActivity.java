package com.example.db_test4;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.db_test4.db.AppDatabase;
import com.example.db_test4.db.AppDatabaseSingleton;
import com.example.db_test4.db.usersinfo.UsersInfo;
import com.example.db_test4.db.usersinfo.UsersInfoDao;

import java.lang.ref.WeakReference;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textOutYear = findViewById(R.id.textOutYear);
        TextView textOutMonth = findViewById(R.id.textOutMonth);
        TextView textOutDay = findViewById(R.id.textOutDay);

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
        Spinner users = findViewById(R.id.textOutName);

        //new DataStoreAsyncTask_getNames(db, this, adapter, users);

        bt.setOnClickListener(new Test(this, db, users, adapter, textOutYear, textOutMonth, textOutDay, editName, editYear, editMonth, editDay));
    }

    private class Test implements View.OnClickListener {
        private Activity activity;
        private AppDatabase db;
        private Spinner users;
        private ArrayAdapter<String> adapter;
        private TextView textOutYear;
        private TextView textOutMonth;
        private TextView textOutDay;
        private EditText editName;
        private EditText editYear;
        private EditText editMonth;
        private EditText editDay;

        private Test(Activity activity, AppDatabase db, Spinner users, ArrayAdapter<String> adapter, TextView textOutYear, TextView textOutMonth, TextView textOutDay, EditText editName, EditText editYear, EditText editMonth, EditText editDay) {
            this.activity = activity;
            this.db = db;
            this.users = users;
            this.adapter = adapter;
            this.textOutYear = textOutYear;
            this.textOutMonth = textOutMonth;
            this.textOutDay = textOutDay;
            this.editName = editName;
            this.editYear = editYear;
            this.editMonth = editMonth;
            this.editDay = editDay;
        }

        @Override
        public void onClick(View view) {
            boolean[] flags = {true, true, true, true};

            flags[0] = editTextError(editName, 1);
            flags[1] = editTextError(editYear, 2);
            flags[2] = editTextError(editMonth, 2);
            flags[3] = editTextError(editDay, 2);

            for (boolean flag : flags) {
                if (flag) {
                } else {
                    return;
                }
            }
            new DataStoreAsyncTask(db, activity, users, adapter, textOutYear, textOutMonth, textOutDay, editName, editYear, editMonth, editDay).execute();
        }

        public boolean editTextError(EditText editText, int error) {
            String regex = "(1|2|3|4|5|6|7|8|9|0)";
            String splitText = "";
            String text = editText.getText().toString();
            String[] str = text.split(splitText);

            if (text.length() == 0) {
                editText.setError(getString(R.string.errorNotInput));
                return false;
            }
            if (text.trim().isEmpty()) {
                editText.setError(getString(R.string.errorSpaceOnly));
                return  false;
            }
            if (str[0].trim().isEmpty() || str[str.length - 1].trim().isEmpty()) {
                editText.setError(getString(R.string.errorSpaceHeadLast));
                return false;
            }
            if (text.replaceFirst("^[\\h]+", "").replaceFirst("[\\h]+$", "").isEmpty()) {
                editText.setError(getString(R.string.errorSpaceOnly));
                return false;
            }
            if (str[0].replaceFirst("^[\\h]+", "").replaceFirst("[\\h]+$", "").isEmpty() ||
                    str[str.length - 1].replaceFirst("^[\\h]+", "").replaceFirst("[\\h]+$", "").isEmpty()) {
                editText.setError(getString(R.string.errorSpaceHeadLast));
                return false;
            }

            if (error == 1) {
                for (String s : str) {
                    if (!s.matches(regex)) {
                        return true;
                    }
                }
                editText.setError(getString(R.string.errorNotString));
                return false;
            } else if (error == 2) {
                for (String s : str) {
                    if (!s.matches(regex)) {
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
        private Spinner users;
        private ArrayAdapter<String> adapter;
        private TextView textOutYear;
        private TextView textOutMonth;
        private TextView textOutDay;
        private StringBuilder sb;
        private EditText editName;
        private EditText editYear;
        private EditText editMonth;
        private EditText editDay;
        private List<UsersInfo> ary;

        public DataStoreAsyncTask(AppDatabase db, Activity activity, Spinner users, ArrayAdapter<String> adapter, TextView textOutYear, TextView textOutMonth, TextView textOutDay, EditText editName, EditText editYear, EditText editMonth, EditText editDay) {
            this.db = db;
            this.users = users;
            this.adapter = adapter;
            weakActivity = new WeakReference<>(activity);
            this.textOutYear = textOutYear;
            this.textOutMonth = textOutMonth;
            this.textOutDay = textOutDay;
            this.editName = editName;
            this.editYear = editYear;
            this.editMonth = editMonth;
            this.editDay = editDay;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            UsersInfoDao usersInfoDao = db.usersInfoDao();
            sb = new StringBuilder();

            String name = editName.getText().toString();
            int year = Integer.parseInt(editYear.getText().toString());
            int month = Integer.parseInt(editMonth.getText().toString());
            int day = Integer.parseInt(editDay.getText().toString());

            usersInfoDao.insert(new UsersInfo(name, year, month, day));
            ary = usersInfoDao.getAll();

            return 0;
        }

        @Override
        protected void onPostExecute(Integer code) {
            Activity activity = weakActivity.get();
            if(activity == null) {
                return;
            }
            for (UsersInfo ui : ary) {
                String name = editName.getText().toString();
                String year = Integer.valueOf(ui.getYear()).toString();
                String month = Integer.valueOf(ui.getMonth()).toString();
                String day = Integer.valueOf(ui.getDay()).toString();
                adapter.add(name);
                textOutYear.setText(year);
                textOutMonth.setText(month);
                textOutDay.setText(day);
            }
            users.setAdapter(adapter);
        }
    }


    private static class DataStoreAsyncTask_getNames extends AsyncTask<Void, Void, Integer> {
        private WeakReference<Activity> weakActivity;
        private AppDatabase db;
        private ArrayAdapter<String> adapter;
        private Spinner users;
        private StringBuilder sb;
        private TextView textOutYear;
        private List<UsersInfo> ary;

        public DataStoreAsyncTask_getNames(AppDatabase db, Activity activity, ArrayAdapter<String> adapter, Spinner users) {
            this.db = db;
            weakActivity = new WeakReference<>(activity);
            this.adapter = adapter;
            this.users = users;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            UsersInfoDao usersInfoDao = db.usersInfoDao();
            sb = new StringBuilder();

            usersInfoDao.insert(new UsersInfo("Name", 2000, 1, 1));
            ary = usersInfoDao.getAll();

            return 0;
        }

        @Override
        protected void onPostExecute(Integer code) {
            Activity activity = weakActivity.get();
            if(activity == null) {
                return;
            }

            for (UsersInfo ui : ary) {
                String str = ui.getName();
                adapter.add(str);
                Log.v("Spinner", " = " + str);
            }
            users.setAdapter(adapter);
        }
    }
}