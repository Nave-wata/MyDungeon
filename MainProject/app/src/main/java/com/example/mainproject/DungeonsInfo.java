package com.example.mainproject;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.mainproject.asynchronous.AppDatabase;
import com.example.mainproject.asynchronous.AppDatabaseSingleton;
import com.example.mainproject.asynchronous.TimerPossession;
import com.example.mainproject.asynchronous.dungeonlayout.DungeonLayout;
import com.example.mainproject.asynchronous.dungeonlayout.GetDungeonLayout;

import java.util.Objects;

public class DungeonsInfo extends Fragment {
    final String EXTRA_DATA = "com.example.mainproject";
    private String UserName;
    private TimerPossession.CharacterPosition_Runnable characterPosition_runnable;
    public static final int widthNum = 20;
    public static final int heightNum = 20;
    public static final int[][] dungeonInfo = new int[heightNum][widthNum];
    public static final int[][] characterInfo = new int[heightNum][widthNum]/*[status_column]*/;

    public static final int NOT_DUNGEON_WALL = 0;
    public static final int DUNGEON_NOTHING = 1;
    public static final int DUNGEON_I_DOOR = 2; // start
    public static final int DUNGEON_O_DOOR = 3; // end
    public static final int DUNGEON_BOSE = 4;
    public static final int DUNGEON_DOOR = 5;
    public static final int DUNGEON_WALL = 6;
    public static final int DUNGEON_TRAP1 = 7;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        UserName = Objects.requireNonNull(args).getString(EXTRA_DATA);

        final AppDatabase db = AppDatabaseSingleton.getInstance(getContext());
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

                    CharacterPositionsInfo();
                },
                e-> Log.v("MainActivity[Exception]", "" + e)
        ).execute();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dungeonsinfo, container, false);
    }

    private void CharacterPositionsInfo() {
        for (int i = 0; i < widthNum; i++) {
            for (int j = 0; j < heightNum; j++) {
                if (dungeonInfo[i][j] == DUNGEON_I_DOOR) {
                    characterInfo[i][j] = 1;
                }
            }
        }
        characterPosition_runnable = new TimerPossession.CharacterPosition_Runnable();
        characterPosition_runnable.run();
    }

    @Override
    public void onStop() {
        super.onStop();
        characterPosition_runnable.stop();
    }

    @NonNull
    public static DungeonsInfo newInstance(String str) {
        DungeonsInfo fragment = new DungeonsInfo();
        Bundle barg = new Bundle();
        barg.putString(fragment.EXTRA_DATA, str);
        fragment.setArguments(barg);
        return fragment;
    }
}
