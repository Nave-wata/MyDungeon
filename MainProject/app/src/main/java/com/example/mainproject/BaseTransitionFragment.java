package com.example.mainproject;

import android.annotation.SuppressLint;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.mainproject.dungeon.DungeonFragment;
import com.example.mainproject.home.HomeFragment;
import com.example.mainproject.monster.MonsterFragment;

import java.util.Objects;


public class BaseTransitionFragment extends Fragment {
    final String EXTRA_DATA = "com.example.fragmenttest2";
    private boolean homeFlag = false;
    private boolean dungeonFlag = true;
    private boolean monsterFlag = true;
    private final String UserName;

    public BaseTransitionFragment(String UserName) {
        this.UserName = UserName;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FragmentTransaction fragmentTransaction = Objects.requireNonNull(getFragmentManager()).beginTransaction();
        fragmentTransaction.replace(R.id.MainContainer, HomeFragment.newInstance(UserName));
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
        onClickListener iBt = new onClickListener(UserName);

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

    @NonNull
    public static BaseTransitionFragment newInstance(String UserName){
        BaseTransitionFragment fragment = new BaseTransitionFragment(UserName);
        Bundle barg = new Bundle();
        barg.putString(fragment.EXTRA_DATA, UserName);
        fragment.setArguments(barg);
        return fragment;
    }

    private class onClickListener implements View.OnClickListener {

        private final String UserName;

        public onClickListener(String UserName) {
            this.UserName = UserName;
        }

        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(@NonNull View v) {
            assert getFragmentManager() != null;
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

            switch (v.getId()) {
                case R.id.home_button:
                    if (homeFlag) {
                        fragmentTransaction.replace(R.id.MainContainer, HomeFragment.newInstance(UserName));
                        fragmentTransaction.commit();
                        homeFlag = false;
                    }
                    dungeonFlag = true;
                    monsterFlag = true;
                    break;
                case R.id.dungeon_button:
                    if (dungeonFlag) {
                        fragmentTransaction.replace(R.id.MainContainer, DungeonFragment.newInstance(UserName));
                        fragmentTransaction.commit();
                        dungeonFlag = false;
                    }
                    homeFlag = true;
                    monsterFlag = true;
                    break;
                case R.id.monster_button:
                    if (monsterFlag) {
                        fragmentTransaction.replace(R.id.MainContainer, MonsterFragment.newInstance(UserName));
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
