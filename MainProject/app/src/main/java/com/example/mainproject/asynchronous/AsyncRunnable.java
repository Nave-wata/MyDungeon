package com.example.mainproject.asynchronous;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;


public class AsyncRunnable implements Runnable {
    private final String url;
    private final Consumer<byte[]> callback;
    private final Consumer<Exception> errorCallback;
    private Exception exception;
    private byte[] response;
    Handler handler = new Handler(Looper.getMainLooper());

    /**
     　 * コンストラクタ
     　 * @param url 通信先URL
     　 * @param callback 正常時のコールバック関数(Consumer<byte[]>)
     　 * @param errorCallback エラー時のコールバック関数(Consumer<Exception>)
     　 */
    public AsyncRunnable(String url,
                         Consumer<byte[]> callback,
                         Consumer<Exception> errorCallback)
    {
        this.url = url;
        this.callback = callback;
        this.errorCallback = errorCallback;
    }


    @Override
    public void run() {
        response = doInBackground();
        handler.post(() -> onPostExecute(response));
    }

    public void execute() {
        //onPreExecute();
        ExecutorService executorService  = Executors.newSingleThreadExecutor();
        executorService.submit(new AsyncRunnable(url, callback, errorCallback));
    }

    //void onPreExecute() {}

    byte[] doInBackground() {
        HttpURLConnection con = null;
        try {
            // HTTPリクエスト
            final URL url = new URL(this.url);
            con = (HttpURLConnection) url.openConnection();
            con.connect();

            final int status = con.getResponseCode();
            if (status == HttpURLConnection.HTTP_OK) {
                final InputStream in = con.getInputStream();
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                byte [] buffer = new byte[1024];
                while(true) {
                    int len = in.read(buffer);
                    if(len < 0) {
                        break;
                    }
                    out.write(buffer, 0, len);
                }
                in.close();
                out.close();
                return out.toByteArray();
            } else {
                throw new IOException("HTTP status:" + status);
            }
        } catch (Exception e) {
            Log.e("ERROR", e.toString(), e);
            this.exception = e;
            return null;
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }
    }

    @SuppressLint("NewApi")
    void onPostExecute(byte[] response) {
        // コールバック処理実行
        if (this.exception == null) {
            callback.accept(response);
        } else {
            errorCallback.accept(this.exception);
        }
    }
}
