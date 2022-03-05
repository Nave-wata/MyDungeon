package com.example.mainproject;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.mainproject.title.TitleActivity;

import org.jetbrains.annotations.Contract;

import java.util.Objects;

public class BaseStatusFragment extends Fragment {
    final static String UserName = TitleActivity.UserName;
    final String EXTRA_DATA = "com.example.mainproject";
    public static final int SIZE = 18;
    private static TextView text_DP;
    private static TextView text_MONEY;
    public static byte[] DP = new byte[18];
    public static byte[] MONEY = new byte[18];

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
        text_DP = view.findViewById(R.id.Have_DP);
        text_MONEY = view.findViewById(R.id.Have_money);

        setImage.setImageViewBitmapFromAsset(ic_DP, "base_menu/dungeonpower.png");
        setImage.setImageViewBitmapFromAsset(ic_money, "base_menu/money.png");
    }

    @NonNull
    public static BaseStatusFragment newInstance(String UserName){
        BaseStatusFragment fragment = new BaseStatusFragment();
        Bundle barg = new Bundle();
        barg.putString(fragment.EXTRA_DATA, UserName);
        fragment.setArguments(barg);
        return fragment;
    }

    public void initDiffTime(long diffTime, byte[] _DP, byte[] _MONEY) {
        int num1 = 5; // DP test用 秒数にかける数
        int num2 = 10; // MONEY test用 秒数にかける数
        long carry1 = 0;
        long carry2 = 0;
        DP = _DP;
        MONEY = _MONEY;

        for (int i = 0; i < SIZE - 1; i++) {
            long tmp = DP[i] * (num1 * diffTime) + carry1;
            DP[i] = (byte) (tmp % 10);
            carry1 = tmp / 10;

            tmp = MONEY[i] * (num2 * diffTime) + carry2;
            MONEY[i] = (byte) (tmp % 10);
            carry2 = tmp / 10;
        }

        text_DP.setText(String.valueOf(CastLong(DP)));
        text_MONEY.setText(String.valueOf(CastLong(MONEY)));
        Log.v("BaseStatusFragment", "initDiffTime");
    }

    @NonNull
    @Contract(pure = true)
    public byte[] CastByte(long L1) {
        byte[] output = new byte[BaseStatusFragment.SIZE];
        for (int i = 0; i < output.length; i++) {
            output[i] = (byte) (L1 % 10);
            L1 /= 10;
        }
        return output;
    }

    @NonNull
    @Contract(pure = true)
    public long CastLong(byte[] ary) {
        long output = 0;
        for (int i = 0; i < 18; i++) {
            output += ary[i] * Math.pow(10, i);
        }
        return output;
    }
}
