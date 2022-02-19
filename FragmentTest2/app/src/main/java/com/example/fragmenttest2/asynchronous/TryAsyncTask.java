package com.example.fragmenttest2.asynchronous;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;
import java.util.function.LongBinaryOperator;

public class TryAsyncTask extends AsyncTask<Void, Void, Integer> {
    @Override
    protected Integer doInBackground(Void... params) {
        return 0;
    }

    @Override
    protected void onPostExecute(Integer code) {
        if (isInternetAvailable()) {
            Log.v("OK!", "");
        } else {
            Log.v("NO~", "");
        }
    }

    public boolean isInternetAvailable() {
        try {
            InetAddress.getByName("https://zipcloud.ibsnet.co.jp/api/search?zipcode=7830060"); //You can replace it with your name
            Log.v("HOGE", "HOGE");
            if (true) {
                return false;
            } else {
                return true;
            }

        } catch (Exception e) {
            return false;
        }

    }

}
