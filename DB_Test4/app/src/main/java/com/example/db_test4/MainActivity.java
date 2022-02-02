package com.example.db_test4;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
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

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner users = findViewById(R.id.textOutName);

        new DataStoreAsyncTask_getNames(db, this, adapter, users, textOutYear);

        bt.setOnClickListener(new Test(this, db, users, adapter, textOutYear, textOutMonth, textOutDay, editName, editYear, editMonth, editDay));
    }

    private class Test implements View.OnClickListener {
        private final Activity activity;
        private final AppDatabase db;
        private final Spinner users;
        private final ArrayAdapter<String> adapter;
        private final TextView textOutYear;
        private final TextView textOutMonth;
        private final TextView textOutDay;
        private final EditText editName;
        private final EditText editYear;
        private final EditText editMonth;
        private final EditText editDay;

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

        @SuppressWarnings("StatementWithEmptyBody")
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
            String regex = "([1234567890])";
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
        private final WeakReference<Activity> weakActivity;
        private final AppDatabase db;
        @SuppressLint("StaticFieldLeak")
        private final Spinner users;
        private final ArrayAdapter<String> adapter;
        @SuppressLint("StaticFieldLeak")
        private final TextView textOutYear;
        @SuppressLint("StaticFieldLeak")
        private final TextView textOutMonth;
        @SuppressLint("StaticFieldLeak")
        private final TextView textOutDay;
        @SuppressLint("StaticFieldLeak")
        private final EditText editName;
        @SuppressLint("StaticFieldLeak")
        private final EditText editYear;
        @SuppressLint("StaticFieldLeak")
        private final EditText editMonth;
        @SuppressLint("StaticFieldLeak")
        private final EditText editDay;
        private List<UsersInfo> ary;

        @SuppressWarnings("deprecation")
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

            String name = editName.getText().toString();
            int year = Integer.parseInt(editYear.getText().toString());
            int month = Integer.parseInt(editMonth.getText().toString());
            int day = Integer.parseInt(editDay.getText().toString());

            usersInfoDao.deleteAll();
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
                String name = ui.getName();
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
        private final WeakReference<Activity> weakActivity;
        private final AppDatabase db;
        private final ArrayAdapter<String> adapter;
        @SuppressLint("StaticFieldLeak")
        private final Spinner users;
        @SuppressLint("StaticFieldLeak")
        private final TextView textOutYear;
        private List<UsersInfo> ary;

        @SuppressWarnings("deprecation")
        public DataStoreAsyncTask_getNames(AppDatabase db, Activity activity, ArrayAdapter<String> adapter, Spinner users, TextView textOutYear) {
            this.db = db;
            weakActivity = new WeakReference<>(activity);
            this.adapter = adapter;
            this.users = users;
            this.textOutYear = textOutYear;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            UsersInfoDao usersInfoDao = db.usersInfoDao();

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
                String name = ui.getName();
                adapter.add(name);
            }
            users.setAdapter(adapter);
        }

    }
}