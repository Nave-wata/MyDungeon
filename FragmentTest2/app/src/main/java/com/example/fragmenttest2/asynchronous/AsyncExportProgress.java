package com.example.fragmenttest2.asynchronous;

import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;
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

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        String result = "";
        String str = "";
        try {
            URL url = new URL("http://httpbin.org/ip");
            // 接続先URLへのコネクションを開く．まだ接続されていない
            urlConnection = (HttpURLConnection) url.openConnection();
            // 接続タイムアウトを設定
            urlConnection.setConnectTimeout(10000);
            // レスポンスデータの読み取りタイムアウトを設定
            urlConnection.setReadTimeout(10000);
            // ヘッダーにUser-Agentを設定
            urlConnection.addRequestProperty("User-Agent", "Android");
            // ヘッダーにAccept-Languageを設定
            urlConnection.addRequestProperty("Accept-Language", Locale.getDefault().toString());
            // HTTPメソッドを指定
            urlConnection.setRequestMethod("GET");
            //リクエストボディの送信を許可しない
            urlConnection.setDoOutput(false);
            //レスポンスボディの受信を許可する
            urlConnection.setDoInput(true);
            // 通信開始
            Log.v("Before", "");
            urlConnection.connect();
            Log.v("After", "");
            // レスポンスコードを取得
            int statusCode = urlConnection.getResponseCode();
            // レスポンスコード200は通信に成功したことを表す
            if (statusCode == 200){
                Log.v("Result", "OK!");
                inputStream = urlConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
                // 1行ずつレスポンス結果を取得しstrに追記
                result = bufferedReader.readLine();
                while (result != null){
                    str += result;
                    result = bufferedReader.readLine();
                }
                bufferedReader.close();
            }
        } catch (MalformedURLException e) {
            Log.v("Result", "NO1");
            e.printStackTrace();
        } catch (IOException e) {
            Log.v("Result", "NO2");
            e.printStackTrace();
        }
    }

    void onPostExecute(String resulto) {
        Log.v("Time", "is: " + result);
    }
}