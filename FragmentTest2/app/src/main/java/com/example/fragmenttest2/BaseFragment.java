package com.example.fragmenttest2;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.fragmenttest2.dungeon.DungeonFragment;
import com.example.fragmenttest2.home.MainFragment;
import com.example.fragmenttest2.monster.MonsterFragment;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;


public class BaseFragment extends Fragment {
    static boolean homeFlag = false;
    static boolean dungeonFlag = true;
    static boolean monsterFlag = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        assert getFragmentManager() != null;
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, MainFragment.newInstance("home"));
        fragmentTransaction.commit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_base, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        AssetManager assetManager = Objects.requireNonNull(getActivity()).getAssets();
        SetImage setImage = new SetImage(assetManager);
        onClickListener iBt = new onClickListener();

        ImageButton homeButton = view.findViewById(R.id.home_button);
        ImageButton dungeonButton = view.findViewById(R.id.dungeon_button);
        ImageButton monsterButton = view.findViewById(R.id.monster_button);

        setImage.setImageButtonBitmapFromAsset(homeButton, "base_menu/base_menu_home.png");
        setImage.setImageButtonBitmapFromAsset(dungeonButton, "base_menu/base_menu_dungeon.png");
        setImage.setImageButtonBitmapFromAsset(monsterButton, "base_menu/base_menu_monster.png");

        homeButton.setOnClickListener(iBt);
        dungeonButton.setOnClickListener(iBt);
        monsterButton.setOnClickListener(iBt);
    }

    private class onClickListener implements View.OnClickListener {
        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View v) {
            assert getFragmentManager() != null;
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

            switch (v.getId()) {
                case R.id.home_button:
                    if (homeFlag) {
                        new AsyncRunnable(
                                getContext(),
                                "https://google.com",
                                "読み込み中",
                                b->{
                                    Log.v("Connect", "OK!!");
                                },
                                e->{
                                    Log.v("Connect", "NO!!");
                                }
                        ).execute();
                        fragmentTransaction.replace(R.id.container, MainFragment.newInstance("home"));
                        fragmentTransaction.commit();
                        homeFlag = false;
                    }
                    dungeonFlag = true;
                    monsterFlag = true;
                    break;
                case R.id.dungeon_button:
                    if (dungeonFlag) {
                        fragmentTransaction.replace(R.id.container, DungeonFragment.newInstance("dungeon"));
                        fragmentTransaction.commit();
                        dungeonFlag = false;
                    }
                    homeFlag = true;
                    monsterFlag = true;
                    break;
                case R.id.monster_button:
                    if (monsterFlag) {
                        fragmentTransaction.replace(R.id.container, MonsterFragment.newInstance("monster"));
                        fragmentTransaction.commit();
                        monsterFlag = false;
                    }
                    homeFlag = true;
                    dungeonFlag = true;
                    break;
                default:
                    break;
            }
        }
    }


    public class AsyncRunnable implements Runnable {
        private Context context = null;
        private String url = null;
        private String message = null;
        private Consumer<byte[]> callback = null;
        private Consumer<Exception> errorCallback = null;
        private Exception exception = null;
        private ProgressDialog progressDialog = null;
        private byte[] response;
        Handler handler = new Handler(Looper.getMainLooper());

        /**
         　 * コンストラクタ
         　 * @param context コンテキスト
         　 * @param url 通信先URL
         　 * @param message 処理中メッセージ
         　 * @param callback 正常時のコールバック関数(Consumer<byte[]>)
         　 * @param errorCallback エラー時のコールバック関数(Consumer<Exception>)
         　 */
        public AsyncRunnable(Context context,
                             String url,
                             String message,
                             Consumer<byte[]> callback,
                             Consumer<Exception> errorCallback) {
            this.context = context;
            this.url = url;
            this.message = message;
            this.callback = callback;
            this.errorCallback = errorCallback;
        }


        @Override
        public void run() {
            response = doInBackground();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    onPostExecute(response);
                }
            });
        }

        public void execute() {
            onPreExecute();
            ExecutorService executorService  = Executors.newSingleThreadExecutor();
            executorService.submit(new AsyncRunnable(context, url, message, callback, errorCallback));
        }

        void onPreExecute() {
            // 砂時計表示
            this.progressDialog = new ProgressDialog(context);
            this.progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            this.progressDialog.setMessage(this.message);
            progressDialog.setCancelable(false);
            this.progressDialog.show();
        }

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
            // 砂時計解除
            this.progressDialog.dismiss();
        }
    }
}