package com.example.mainproject;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.mainproject.asynchronous.AppDatabase;
import com.example.mainproject.asynchronous.AppDatabaseSingleton;
import com.example.mainproject.asynchronous.dungeonlayout.DungeonLayout;
import com.example.mainproject.asynchronous.dungeonlayout.GetDungeonLayout;
import com.example.mainproject.asynchronous.usersapptimes.UpdateAppTimes;
import com.example.mainproject.asynchronous.userspossession.UpdatePossession;
import com.example.mainproject.dungeon.DungeonFragment;
import com.example.mainproject.title.TitleActivity;

import java.time.LocalDateTime;


public class MainActivity extends AppCompatActivity {
    public static String UserName;
    public static boolean AppFirstFlag;
    public static final int widthNum = 20;
    public static final int heightNum = 20;
    public static final int[][] dungeonInfo = new int[widthNum][heightNum];
    public static final int[][] characterInfo = new int[widthNum][heightNum]/*[status_column]*/;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = this.getIntent();
        UserName = intent.getStringExtra(TitleActivity.EXTRA_DATA);
        AppFirstFlag = true;

        final AppDatabase db = AppDatabaseSingleton.getInstance(getApplicationContext());
        new GetDungeonLayout(
                db,
                UserName,
                b->{
                    String[] dungeonLayouts = new String[heightNum];
                    for (DungeonLayout dl: b) {
                        dungeonLayouts[0] = dl.getRow0();
                        dungeonLayouts[1] = dl.getRow1();
                        dungeonLayouts[2] = dl.getRow2();
                        dungeonLayouts[3] = dl.getRow3();
                        dungeonLayouts[4] = dl.getRow4();
                        dungeonLayouts[5] = dl.getRow5();
                        dungeonLayouts[6] = dl.getRow6();
                        dungeonLayouts[7] = dl.getRow7();
                        dungeonLayouts[8] = dl.getRow8();
                        dungeonLayouts[9] = dl.getRow9();
                        dungeonLayouts[10] = dl.getRow10();
                        dungeonLayouts[11] = dl.getRow11();
                        dungeonLayouts[12] = dl.getRow12();
                        dungeonLayouts[13] = dl.getRow13();
                        dungeonLayouts[14] = dl.getRow14();
                        dungeonLayouts[15] = dl.getRow15();
                        dungeonLayouts[16] = dl.getRow16();
                        dungeonLayouts[17] = dl.getRow17();
                        dungeonLayouts[18] = dl.getRow18();
                        dungeonLayouts[19] = dl.getRow19();
                    }

                    for (int i = 0; i < heightNum; i++) {
                        String[] strSplit = dungeonLayouts[i].split(",");
                        for (int j = 0; j < widthNum; j++) {
                            dungeonInfo[i][j] = Integer.parseInt(strSplit[j]);
                        }
                    }
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.BaseTransitionContainer, BaseTransitionFragment.newInstance(UserName));
                    fragmentTransaction.replace(R.id.BaseStatusContainer, BaseStatusFragment.newInstance(UserName));
                    fragmentTransaction.commit();

                    CharacterPositionsInfo();
                },
                e->{
                    Log.v("MainActivity[Exception]", "" + e);
                }
        ).execute();
    }

    private void CharacterPositionsInfo() {
        int X = 0;
        int Y = 0;
        for (int i = 0; i < widthNum; i++) {
            for (int j = 0; j < heightNum; j++) {
                if (dungeonInfo[i][j] == DungeonFragment.DUNGEON_I_DOOR) {
                    X = i;
                    Y = j;
                }
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onStop() {
        super.onStop();

        AppFirstFlag = false;
        LocalDateTime nowTime = LocalDateTime.now();
        int nowYear = nowTime.getYear();
        int nowMonth = nowTime.getMonthValue();
        int nowDay = nowTime.getDayOfMonth();
        int nowHour = nowTime.getHour();
        int nowMinute = nowTime.getMinute();
        int nowSecond = nowTime.getSecond();
        AppDatabase db = AppDatabaseSingleton.getInstance(getApplicationContext());

        new UpdateAppTimes(
                db,
                UserName,
                nowYear,
                nowMonth,
                nowDay,
                nowHour,
                nowMinute,
                nowSecond,
                b->{},
                e->{}
        ).execute();

        new UpdatePossession(
                db,
                UserName,
                BaseStatusFragment.DP,
                BaseStatusFragment.MONEY,
                b->{},
                e->{}
        ).execute();
    }
}
