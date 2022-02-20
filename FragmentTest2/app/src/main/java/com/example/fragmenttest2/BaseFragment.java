package com.example.fragmenttest2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.fragmenttest2.asynchronous.AsyncRunnable;
import com.example.fragmenttest2.asynchronous.CallBacks;
import com.example.fragmenttest2.dungeon.DungeonFragment;
import com.example.fragmenttest2.home.HomeFragment;
import com.example.fragmenttest2.monster.MonsterFragment;
import com.example.fragmenttest2.title.activity.TitleActivity;

import java.util.Objects;


public class BaseFragment extends Fragment {
    static boolean homeFlag = false;
    static boolean dungeonFlag = true;
    static boolean monsterFlag = true;
    String[] str;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getActivity().getIntent();
        str = intent.getStringArrayExtra(TitleActivity.EXTRA_DATA);

        for (int i = 0; i < str.length; i++) {
            Log.v("str = ", str[i]);
        }

        assert getFragmentManager() != null;
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, HomeFragment.newInstance("home"));
        fragmentTransaction.commit();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_base, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        AssetManager assetManager = Objects.requireNonNull(getActivity()).getAssets();
        SetImage setImage = new SetImage(assetManager);
        onClickListener iBt = new onClickListener(str);

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
        public String[] str;
        public String str1;
        public String str2;
        public String str3;

        public onClickListener(String[] str) {
            this.str = str;
            for (int i = 0; i < str.length; i++) {
                Log.v("this.str = ", str[i]);
            }

            str1 = str[0];
            str2 = str[1];
            str3 = str[2];

            Log.v("str1 = ", str1);
            Log.v("str2 = ", str2);
            Log.v("str3 = ", str3);
        }

        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(@NonNull View v) {
            assert getFragmentManager() != null;
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

            switch (v.getId()) {
                case R.id.home_button:
                    if (homeFlag) {
                        fragmentTransaction.replace(R.id.container, HomeFragment.newInstance("home"));
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
}