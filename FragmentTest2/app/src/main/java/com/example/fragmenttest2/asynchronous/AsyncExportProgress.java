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
        Uri.Builder uriBuilder = new Uri.Builder();
        uriBuilder.scheme("https");
        uriBuilder.authority("http://192.168.3.21:8000");
        uriBuilder.path("/now/");
        uriBuilder.appendQueryParameter("q", "夏目漱石");
        final String uriStr = uriBuilder.build().toString();

        try {
            URL url = new URL(uriStr);
            HttpURLConnection con = null;
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setDoInput(true);
            con.connect(); //HTTP接続

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

    public void execute() {
        onPreExecute();

        ExecutorService executorService  = Executors.newSingleThreadExecutor();
        executorService.submit(new AsyncRunnable());
    }

    void onPostExecute(String resulto) {
        Log.v("Time", "is: " + result);
    }
}