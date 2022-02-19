package com.example.fragmenttest2.asynchronous;

import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AsyncExportProgress {

    final StringBuilder result = new StringBuilder();

    private class AsyncRunnable implements Runnable {

        private String strResult;

        Handler handler = new Handler(Looper.getMainLooper());
        @Override
        public void run() {
            // ここにバックグラウンド処理を書く

            handler.post(new Runnable() {
                @Override
                public void run() {
                    onPostExecute(strResult);
                }
            });
        }
    }

    void onPreExecute() {
    }

    public void execute() {
        onPreExecute();

        ExecutorService executorService  = Executors.newSingleThreadExecutor();
        executorService.submit(new AsyncRunnable());

        Uri.Builder uriBuilder = new Uri.Builder();
        uriBuilder.scheme("https");
        uriBuilder.authority("www.google.com");
        uriBuilder.path("/");
        uriBuilder.appendQueryParameter("Now", "time");
        final String uriStr = uriBuilder.build().toString();

        try {
            URL url = new URL(uriStr);
            HttpURLConnection con = null;
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setDoInput(true);
            con.connect(); //HTTP接続
            Log.v("Afterrrrr", "");

            final InputStream in = con.getInputStream();
            final InputStreamReader inReader = new InputStreamReader(in);
            final BufferedReader bufReader = new BufferedReader(inReader);

            String line = null;
            while((line = bufReader.readLine()) != null) {
                result.append(line);
            }
            bufReader.close();
            inReader.close();
            in.close();
            Log.v("Result", "YEEEEEEES");
        }

        catch(Exception e) {
            Log.v("Result", "NOOOOOOOO");
        }
    }

    void onPostExecute(String resulto) {
        Log.v("Time", "is: " + result);
    }
}