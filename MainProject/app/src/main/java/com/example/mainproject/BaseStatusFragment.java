package com.example.mainproject;

import android.annotation.SuppressLint;
import android.content.res.AssetManager;
import android.os.Build;
import android.os.Bundle;
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
import com.example.mainproject.asynchronous.usersapptimes.GetAppTimes;
import com.example.mainproject.asynchronous.usersapptimes.UsersAppTimes;
import com.example.mainproject.asynchronous.userspossession.GetPossession;
import com.example.mainproject.asynchronous.userspossession.UsersPossession;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class BaseStatusFragment extends Fragment {
    final String UserName = MainActivity.UserName;
    final String EXTRA_DATA = "com.example.mainproject";
    public static final int SIZE = 18;
    public static byte[] DP = new byte[18];
    public static byte[] MONEY = new byte[18];
    @SuppressLint("StaticFieldLeak")
    public static TextView text_DP;
    @SuppressLint("StaticFieldLeak")
    public static TextView text_MONEY;
    private final TimerPossession timerPossession = new TimerPossession();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_basestatus, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        AssetManager assetManager = Objects.requireNonNull(getActivity()).getAssets();
        SetImage setImage = new SetImage(assetManager);

        ImageView ic_DP = view.findViewById(R.id.ic_DP);
        ImageView ic_money = view.findViewById(R.id.ic_money);
        text_DP = view.findViewById(R.id.Have_DP);
        text_MONEY = view.findViewById(R.id.Have_money);

        setImage.setImageViewBitmapFromAsset(ic_DP, "base_menu/dungeonpower.png");
        setImage.setImageViewBitmapFromAsset(ic_money, "base_menu/money.png");
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onStart() {
        super.onStart();
        final AppDatabase db = AppDatabaseSingleton.getInstance(Objects.requireNonNull(getActivity()).getApplicationContext());
        new GetAppTimes(
                db,
                UserName,
                b-> new GetPossession(
                        db,
                        UserName,
                        c->{
                            long diffSecond;
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
                                for (UsersAppTimes uat: b) {
                                    beforeYear = uat.getYear();
                                    beforeMonth = uat.getMonth();
                                    beforeDay = uat.getDay();
                                    beforeHour = uat.getHour();
                                    beforeMinute = uat.getMinute();
                                    beforeSecond = uat.getSecond();
                                }
                                for (UsersPossession up: c) {
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
                                    showDiffTimeDialog.show(Objects.requireNonNull(getFragmentManager()), "showDiffTimeDialog");
                                }
                            } catch (Exception ignored) {}
                        },
                        e->{}
                ).execute(),
                e->{}
        ).execute();
        timerPossession.Run();
    }

    @Override
    public void onStop() {
        super.onStop();
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

        String DP_str = setStringNumber(CastString(DP));
        String MONEY_str = setStringNumber(CastString(MONEY));

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
    public byte[] CastByte(@NonNull String _DP) {
        final String[] _DPSplit = _DP.split("");
        byte[] output = new byte[BaseStatusFragment.SIZE];
        for (int i = 0; i < _DPSplit.length; i++) {
            output[i] = (byte) Integer.parseInt(_DPSplit[_DPSplit.length - 1 - i]);
        }
        return output;
    }

    @NonNull
    public String CastString(byte[] ary) {
        StringBuilder output = new StringBuilder();
        boolean flag = true;
        for (int i = BaseStatusFragment.SIZE -1; i >= 0; i--) {
            if (ary[i] == 0 && flag) {
                continue;
            } else {
                flag = false;
            }
            output.append(ary[i]);
        }
        return output.toString();
    }

    public String setStringNumber(@NonNull String str) {
        StringBuilder output = new StringBuilder();
        String[] strSplit = str.split("");
        for (int i = strSplit.length - 1; i >= 0; i--) {
            output.append(strSplit[i]);
            if ((strSplit.length - i) % 3 == 0 && i != 0) {
                output.append(",");
            }
        }
        output.reverse();
        return output.toString();
    }
}
