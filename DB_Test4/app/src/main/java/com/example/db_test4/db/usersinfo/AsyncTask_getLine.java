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

public class AsyncTask_getLine extends AsyncTask<Void, Void, Integer> {
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
    private List<UsersInfo> ary;
    private String item;

    @SuppressWarnings("deprecation")
    public AsyncTask_getLine(AppDatabase db, Activity activity, Spinner users, ArrayAdapter<String> adapter, TextView textOutYear, TextView textOutMonth, TextView textOutDay, String item) {
        this.db = db;
        this.users = users;
        this.adapter = adapter;
        weakActivity = new WeakReference<>(activity);
        this.textOutYear = textOutYear;
        this.textOutMonth = textOutMonth;
        this.textOutDay = textOutDay;
        this.item = item;
    }

    @Override
    protected Integer doInBackground(Void... params) {
        UsersInfoDao usersInfoDao = db.usersInfoDao();

        ary = usersInfoDao.getLine(item);

        return 0;
    }

    @Override
    protected void onPostExecute(Integer code) {
        Activity activity = weakActivity.get();

        if(activity == null) {
            return;
        }

        for (UsersInfo ui : ary) {
            textOutYear.setText(ui.getYear());
            textOutMonth.setText(ui.getMonth());
            textOutDay.setText(ui.getDay());
        }
    }
}