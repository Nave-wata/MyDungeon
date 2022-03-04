package com.example.mainproject.asynchronous;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.mainproject.asynchronous.usersinfo.UsersInfoDao;

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
        String name = "developer";
        String password = "developer";
        String salt = "v1Kd6L6KxmoT4B0tOoDoSQj4qMXOOYVtvSZCw74Ed32PYqMDw1bvsm5nkrmfI1YliF0vjRrytFrJMtboCjKEnNCAIw2q7ap6639f3nzOl2yyADY7Cifh9VN6a7nyCq0Mqb0HdFlqtG9GVSX9gP1gIzYftd3lEezR61PxeP81lM0HotXkR3qdDk9mSCL2MH30KPkKYTahD74vYeeYG2MdFrngHd5xh5a8gTEtgTXnXsNEyf2JLCoFt1m02jIVXtWyLDDVR6aSI9sVoEyvNyYtHqqGStPcdONACMhOzDxOfYCfKKAxrXfs7XAMmnTbo8GeRYvJzdfTnY2IBpByJTP1gN3sh6qPj3BaJp867gocRkDWkFGMSgl157LlXskB6tg5C191p6LSxlQHAyCq81KX1B";
        String hash = "110f1b084876e5b287535b83abf3ad92a5a98caee21a22b5ff20e41b13a2efd0f031ae32372c85cff08bd15b0e7f3f2b818d33f1a04c5dc42bf141f43290cebe";
        UsersInfoDao usersInfoDao = db.usersInfoDao();
        SharedPreferences dataStore = activity.getSharedPreferences(NEXT_INFO, MODE_PRIVATE);
        SharedPreferences.Editor editor = dataStore.edit();

        editor.putBoolean(DS_Flag, true);
        editor.putString(DS_Name, name);
        editor.putString(DS_Passwd, password);
        editor.apply();

        try {
            usersInfoDao.insertNames(name, salt, hash);
            Log.v("Database Init", "OK");
        } catch (Exception ignored) {
            Log.v("Database Init", "NO");
        }
    }

    //void onPostExecute() {}
}