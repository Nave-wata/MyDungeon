package com.example.db_test4.db.usersinfo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.db_test4.db.AppDatabase;

import java.lang.ref.WeakReference;
import java.util.List;

public class DataStoreAsyncTask_Main extends AsyncTask<Void, Void, Integer> {
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
    public DataStoreAsyncTask_Main(AppDatabase db, Activity activity, Spinner users, ArrayAdapter<String> adapter, TextView textOutYear, TextView textOutMonth, TextView textOutDay, EditText editName, EditText editYear, EditText editMonth, EditText editDay) {
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