package com.example.mainproject;

import android.content.res.AssetManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.mainproject.asynchronous.AppDatabase;
import com.example.mainproject.asynchronous.AppDatabaseSingleton;
import com.example.mainproject.asynchronous.TimerPossession;
import com.example.mainproject.asynchronous.usersinfo.GetUsersInfo;
import com.example.mainproject.asynchronous.usersinfo.UsersInfo;
import com.example.mainproject.asynchronous.userspossessioninfo.GetUsersPossessionInfo;
import com.example.mainproject.asynchronous.userspossessioninfo.UsersPossessionInfo;

import org.jetbrains.annotations.Contract;

import java.text.NumberFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class BaseStatusFragment extends Fragment {
    final String UserName = MainActivity.UserName;
    final String EXTRA_DATA = "com.example.mainproject";
    public static final int SIZE = 18;
    public static byte[] DP = new byte[18];
    public static byte[] MONEY = new byte[18];
    public static TextView text_DP;
    public static TextView text_MONEY;
    public TimerPossession timerPossession;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_basestatus, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
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
        timerPossession = new TimerPossession();
        timerPossession.Run();

        setImage.setImageViewBitmapFromAsset(ic_DP, "base_menu/dungeonpower.png");
        setImage.setImageViewBitmapFromAsset(ic_money, "base_menu/money.png");

        final AppDatabase db = AppDatabaseSingleton.getInstance(getActivity().getApplicationContext());
        new GetUsersInfo(
                db,
                UserName,
                b->{
                    new GetUsersPossessionInfo(
                            db,
                            UserName,
                            c->{
                                long diffSecond = 0;
                                byte[] _DP = null;
                                byte[] _MONEY = null;
                                try {
                                    final LocalDateTime nowTime = LocalDateTime.now();
                                    final int nowYear = nowTime.getYear();
                                    final int nowMonth = nowTime.getMonthValue();
                                    final int nowDay = nowTime.getDayOfMonth();
                                    final int nowHour = nowTime.getHour();
                                    final int nowMinute = nowTime.getMinute();
                                    final int nowSecond = nowTime.getSecond();
                                    int beforeYear = 0, beforeMonth = 0 , beforeDay = 0, beforeHour = 0, beforeMinute = 0, beforeSecond = 0;

                                    for (UsersInfo pi: b) {
                                        beforeYear = pi.getYear();
                                        beforeMonth = pi.getMonth();
                                        beforeDay = pi.getDay();
                                        beforeHour = pi.getHour();
                                        beforeMinute = pi.getMinute();
                                        beforeSecond = pi.getSecond();
                                    }
                                    for (UsersPossessionInfo up: c) {
                                        _DP = up.getDP();
                                        _MONEY = up.getMoney();
                                    }

                                    LocalDateTime BeforeTime = LocalDateTime.of(beforeYear, beforeMonth, beforeDay, beforeHour, beforeMinute, beforeSecond);
                                    LocalDateTime NowTime = LocalDateTime.of(nowYear, nowMonth, nowDay, nowHour, nowMinute, nowSecond);
                                    Duration duration= Duration.between(BeforeTime, NowTime);
                                    diffSecond = duration.getSeconds();
                                    new BaseStatusFragment().initDiffTime(diffSecond, _DP, _MONEY);
                                    if (MainActivity.AppFirstFlag) {
                                        ShowDiffTimeDialog showDiffTimeDialog = new ShowDiffTimeDialog(diffSecond);
                                        showDiffTimeDialog.show(getFragmentManager(), "showDiffTimeDialog");
                                    }
                                } catch (Exception e) {}
                            },
                            e->{}
                    ).execute();
                },
                e->{}
        ).execute();
    }

    @Override
    public void onStop() {
        super.onStop();
        text_DP.setText(null);
        text_MONEY.setText(null);
        timerPossession.Stop();
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
        long num1 = 1; // DP test用 秒数にかける数
        long num2 = 2; // MONEY test用 秒数にかける数
        byte[] Base_DP = {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        byte[] Base_MONEY = {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

        DP = Add(_DP, mul(Base_DP, num1 * diffTime));
        MONEY = Add(_MONEY, mul(Base_MONEY, num2 * diffTime));

        String DP_str = NumberFormat.getNumberInstance().format(CastLong(DP));
        String MONEY_str = NumberFormat.getNumberInstance().format(CastLong(MONEY));

        text_DP.setText(DP_str);
        text_MONEY.setText(MONEY_str);
    }

    public byte[] mul(byte[] ary, long a) {
        long carry = 0;
        for (int i = 0; i < SIZE - 1; i++) {
            long tmp = ary[i] * a + carry;
            ary[i] = (byte) (tmp % 10);
            carry = tmp /10;
        }
        return ary;
    }

    public byte[] Add(byte[] ary1, byte[] ary2) {
        for (int i = 0; i < SIZE - 1; i++) {
            byte tmp = (byte) (ary1[i] + ary2[i]);
            if (tmp < 10) {
                ary1[i] = tmp;
            } else {
                ary1[i] = (byte) (tmp - 10);
                ary1[i + 1] += 1;
            }
        }
        return ary1;
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
