package com.example.mainproject;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Objects;

public class BaseStatusFragment extends Fragment {
    final String UserName;
    final String EXTRA_DATA = "com.example.mainproject";
    public static ArrayList<Byte> DP = new ArrayList<Byte>();
    public static ArrayList<Byte> MONEY = new ArrayList<Byte>();

    private final Handler handler = new Handler(Looper.getMainLooper());

    public BaseStatusFragment(String UserName) {
        this.UserName = UserName;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_basestatus, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.v("BaseStatusFragment", "onViewCreated");

        AssetManager assetManager = Objects.requireNonNull(getActivity()).getAssets();
        SetImage setImage = new SetImage(assetManager);

        ImageView ic_DP = view.findViewById(R.id.ic_DP);
        ImageView ic_money = view.findViewById(R.id.ic_money);

        setImage.setImageViewBitmapFromAsset(ic_DP, "base_menu/dungeonpower.png");
        setImage.setImageViewBitmapFromAsset(ic_money, "base_menu/money.png");
    }

    @NonNull
    public static BaseStatusFragment newInstance(String UserName){
        BaseStatusFragment fragment = new BaseStatusFragment(UserName);
        Bundle barg = new Bundle();
        barg.putString(fragment.EXTRA_DATA, UserName);
        fragment.setArguments(barg);
        return fragment;
    }

    public static void initDiffTime(long diffTime) { // アプリ起動時にする計算処理
        Log.v("DiffTime", "" + diffTime);
        Log.v("DP.size", "" + DP.size());
    }
}
