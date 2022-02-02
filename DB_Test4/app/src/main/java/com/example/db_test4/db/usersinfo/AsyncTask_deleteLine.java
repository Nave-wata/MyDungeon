package com.example.db_test4.db.usersinfo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.db_test4.db.AppDatabase;

import java.lang.ref.WeakReference;
import java.util.List;

public class AsyncTask_deleteLine extends AsyncTask<Void, Void, Integer> {
    @SuppressLint("StaticFieldLeak")
    private final Spinner users;
    @SuppressLint("StaticFieldLeak")
    private final WeakReference<Activity> weakActivity;
    private final AppDatabase db;
    private final ArrayAdapter<String> adapter;
    private List<UsersInfo> ary;
    private TextView textOutYear;
    private TextView textOutMonth;
    private TextView textOutDay;
    private String item;

    @SuppressWarnings("deprecation")
    public AsyncTask_deleteLine(AppDatabase db, Activity activity, ArrayAdapter<String> adapter, Spinner users, TextView textOutYear, TextView textOutMonth, TextView textOutDay, String item) {
        this.db = db;
        weakActivity = new WeakReference<>(activity);
        this.adapter = adapter;
        this.users = users;
        this.textOutYear = textOutYear;
        this.textOutMonth = textOutMonth;
        this.textOutDay = textOutDay;
        this.item = item;
    }

    @Override
    protected Integer doInBackground(Void... params) {
        UsersInfoDao usersInfoDao = db.usersInfoDao();

        usersInfoDao.deleteUserInfo(item);

        return 0;
    }

    @Override
    protected void onPostExecute(Integer code) {
        Activity activity = weakActivity.get();
        if (activity == null) {
            return;
        }

        textOutYear.setText(null);
        textOutMonth.setText(null);
        textOutDay.setText(null);
        adapter.remove(item);
        users.setAdapter(adapter);
    }
}
