package com.example.fragmenttest2;

import android.annotation.SuppressLint;
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
import com.example.fragmenttest2.dungeon.DungeonFragment;
import com.example.fragmenttest2.home.HomeFragment;
import com.example.fragmenttest2.monster.MonsterFragment;

import java.util.Objects;


public class BaseFragment extends Fragment {
    static boolean homeFlag = false;
    static boolean dungeonFlag = true;
    static boolean monsterFlag = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        public void onClick(@NonNull View v) {
            assert getFragmentManager() != null;
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

            switch (v.getId()) {
                case R.id.home_button:
                    if (homeFlag) {
                        new AsyncRunnable(
                                "http://192.168.3.21:8000/one",
                                b->{
                                    TextView tv = getActivity().findViewById(R.id.Home_text);
                                    tv.setText(new String(b));
                                },
                                e->{
                                    Log.v("States", e.toString());
                                }
                        ).execute();
                        fragmentTransaction.replace(R.id.container, HomeFragment.newInstance("home"));
                        fragmentTransaction.commit();
                        homeFlag = false;
                    }
                    dungeonFlag = true;
                    monsterFlag = true;
                    break;
                case R.id.dungeon_button:
                    if (dungeonFlag) {
                        new AsyncRunnable(
                                "http://192.168.3.21:8000/two",
                                b->{
                                    test(new String(b));
                                },
                                e->{
                                    Log.v("States", e.toString());
                                }
                        ).execute();
                        fragmentTransaction.replace(R.id.container, DungeonFragment.newInstance("dungeon"));
                        fragmentTransaction.commit();
                        dungeonFlag = false;
                    }
                    homeFlag = true;
                    monsterFlag = true;
                    break;
                case R.id.monster_button:
                    if (monsterFlag) {
                        new AsyncRunnable(
                                "http://192.168.3.21:8000/three",
                                b->{
                                    TextView tv = getActivity().findViewById(R.id.Monster_text);
                                    tv.setText(new String(b));
                                },
                                e->{
                                    Log.v("States", e.toString());
                                }
                        ).execute();
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

        private void test(String str) {
            TextView tv = getActivity().findViewById(R.id.Dungeon_text);
            tv.setText(str);
        }
    }
}