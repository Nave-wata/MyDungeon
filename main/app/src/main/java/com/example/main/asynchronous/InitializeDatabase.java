package com.example.main.asynchronous;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.main.asynchronous.usersinfo.UsersInfo;
import com.example.main.asynchronous.usersinfo.UsersInfoDao;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class InitializeDatabase implements Runnable {
    private final AppDatabase db;
    private final Activity activity;

    public InitializeDatabase(AppDatabase db, Activity activity) {
        this.db = db;
        this.activity = activity;
    }

    @Override
    public void run() {
        doInBackground();
        //handler.post(() -> onPostExecute());
    }

    public void execute() {
        // onPreExecute();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(new InitializeDatabase(db, activity));
    }

    // void onPreExecute() {}

    void doInBackground() {
        String NEXT_INFO = "NextInfo";
        String DS_Flag = "Flag";
        String DS_Name = "Name";
        String DS_Passwd = "Password";
        String name = "hgnkHVbHZsSdyiArXL4MNiwnWYdFxaQEEuNstxG6";
        String password = "Y8jeDbJKJJ6r2uaa4i997d7LEB5i3dXCEKignuL4XN2RLrj2KeAxpHBeTzHFjJtg4ArC5zz3fjCkusfYUt8C3FrjtDn9e8A7dCxkkJ7Fnr93L2bcsLfGCA9XWcuyJif6";
        String salt = "WjfKR6s2DE7jh9Y";
        String hash = "ae4a8211a251c813ff09ebdbe3645dedce53dfb3c89b182a29af674945a0c454e98fcf2ed2df95948de436592df61896cb447ba4242bc11c57c8c8827c7bae93";
        UsersInfoDao usersInfoDao = db.usersInfoDao();
        SharedPreferences dataStore = activity.getSharedPreferences(NEXT_INFO, MODE_PRIVATE);
        SharedPreferences.Editor editor = dataStore.edit();

        editor.putBoolean(DS_Flag, true);
        editor.putString(DS_Name, name);
        editor.putString(DS_Passwd, password);

        try {
            usersInfoDao.insert(new UsersInfo(name, salt, hash));
        } catch (Exception ignored) {
        }
    }

    //void onPostExecute() {}
}
