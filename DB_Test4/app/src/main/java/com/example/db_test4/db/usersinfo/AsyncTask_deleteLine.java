package com.example.db_test4.db.usersinfo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.db_test4.db.AppDatabase;

import java.lang.ref.WeakReference;
import java.util.List;

public class AsyncTask_deleteLine extends AsyncTask<Void, Void, Integer> {
    @SuppressLint("StaticFieldLeak")
    private Spinner users;
    @SuppressLint("StaticFieldLeak")
    private WeakReference<Activity> weakActivity;
    private AppDatabase db;
    private ArrayAdapter<String> adapter;
    private List<UsersInfo> ary;
    private String str;

    @SuppressWarnings("deprecation")
    public AsyncTask_deleteLine(AppDatabase db, Activity activity, ArrayAdapter<String> adapter, Spinner users) {
        this.db = db;
        weakActivity = new WeakReference<>(activity);
        this.adapter = adapter;
        this.users = users;
    }

    @Override
    protected Integer doInBackground(Void... params) {
        UsersInfoDao usersInfoDao = db.usersInfoDao();

        usersInfoDao.delete(new UsersInfo("Hello", 1, 1, 1));
        adapter.remove("Hello");
        ary = usersInfoDao.getAll();

        return 0;
    }

    @Override
    protected void onPostExecute(Integer code) {
        Activity activity = weakActivity.get();
        if (activity == null) {
            return;
        }
    }
}
