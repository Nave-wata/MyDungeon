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

public class AsyncTask_setName extends AsyncTask<Void, Void, Integer> {
    private WeakReference<Activity> weakActivity;
    private AppDatabase db;
    private ArrayAdapter<String> adapter;
    @SuppressLint("StaticFieldLeak")
    private Spinner users;
    @SuppressLint("StaticFieldLeak")
    private TextView textOutYear;
    private List<UsersInfo> ary;

    @SuppressWarnings("deprecation")
    public AsyncTask_setName(AppDatabase db, Activity activity, ArrayAdapter<String> adapter, Spinner users, TextView textOutYear) {
        this.db = db;
        weakActivity = new WeakReference<>(activity);
        this.adapter = adapter;
        this.users = users;
        this.textOutYear = textOutYear;
    }

    public AsyncTask_setName() {
    }

    @Override
    protected Integer doInBackground(Void... params) {
        UsersInfoDao usersInfoDao = db.usersInfoDao();

        usersInfoDao.deleteAll();
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