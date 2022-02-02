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

public class AsyncTask_Save extends AsyncTask<Void, Void, Integer> {
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

    @SuppressWarnings("deprecation")
    public AsyncTask_Save(AppDatabase db, Activity activity, Spinner users, ArrayAdapter<String> adapter, TextView textOutYear, TextView textOutMonth, TextView textOutDay, EditText editName, EditText editYear, EditText editMonth, EditText editDay) {
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

        usersInfoDao.insert(new UsersInfo(name, year, month, day));

        return 0;
    }

    @Override
    protected void onPostExecute(Integer code) {
        Activity activity = weakActivity.get();

        if(activity == null) {
            return;
        }

        adapter.add(editName.getText().toString());
        users.setAdapter(adapter);
    }
}