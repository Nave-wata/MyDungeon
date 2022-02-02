package com.example.db_test4;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
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

        new DataStoreAsyncTask_getNames(db, this, adapter, users);

        bt.setOnClickListener(new Test(this, db, textOutYear, editName, editYear, editMonth, editDay));
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
            boolean[] flags = {true, true, true, true};

            flags[0] = editTextError(editName, 1);
            flags[1] = editTextError(editYear, 2);
            flags[2] = editTextError(editMonth, 2);
            flags[3] = editTextError(editDay, 2);

            for (boolean flag : flags) {
                if (flag) {
                } else {
                    textOutName.setText("NO");
                    return;
                }
            }
            new DataStoreAsyncTask(db, activity, textOutName, editName, editYear, editMonth, editDay).execute();
            textOutName.setText("Yes!");
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
        private TextView textOutName;
        private StringBuilder sb;
        private EditText editName;
        private EditText editYear;
        private EditText editMonth;
        private EditText editDay;

        public DataStoreAsyncTask(AppDatabase db, Activity activity, TextView textOutName, EditText editName, EditText editYear, EditText editMonth, EditText editDay) {
            this.db = db;
            weakActivity = new WeakReference<>(activity);
            this.textOutName = textOutName;
            this.editName = editName;
            this.editYear = editYear;
            this.editMonth = editMonth;
            this.editDay = editDay;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            UsersInfoDao usersInfoDao = db.usersInfoDao();
            sb = new StringBuilder();

            List<UsersInfo> ary = usersInfoDao.getAll();
            usersInfoDao.deleteUserInfo("Name");
            for (UsersInfo ui : ary) {
                sb.append(ui.getName());
            }

            return 0;
        }

        @Override
        protected void onPostExecute(Integer code) {
            Activity activity = weakActivity.get();
            if(activity == null) {
                return;
            }

            textOutName.setText(sb.toString());
        }
    }

    private static class DataStoreAsyncTask_getNames extends AsyncTask<Void, Void, Integer> {
        private WeakReference<Activity> weakActivity;
        private AppDatabase db;
        private ArrayAdapter<String> adapter;
        private Spinner users;
        private StringBuilder sb;

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
            List<UsersInfo> ary = usersInfoDao.getAll();

            for (UsersInfo ui : ary) {
                adapter.add(ui.getName());
                Log.v("Name", " = " + ui.getName());
            }

            return 0;
        }

        @Override
        protected void onPostExecute(Integer code) {
            Activity activity = weakActivity.get();

            if(activity == null) {
                return;
            }
            users.setAdapter(adapter);
        }
    }
}