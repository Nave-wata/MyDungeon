package com.example.fragmenttest2;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

public class BaseFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_base, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        AssetManager assetManager = getActivity().getAssets();
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
        @Override
        public void onClick(View v) {
            int id = v.getId();

            switch (id) {
                case R.id.home_button:
                    Intent mainIntent = new Intent(getActivity(), MainActivity.class);
                    startActivity(mainIntent);
                    getActivity().finish();
                    break;
                case R.id.dungeon_button:
                    Intent dungeonIntent = new Intent(getActivity(), DungeonActivity.class);
                    startActivity(dungeonIntent);
                    getActivity().finish();
                    break;
                case R.id.monster_button:
                    Intent monsterIntent = new Intent(getActivity(), MonsterActivity.class);
                    startActivity(monsterIntent);
                    getActivity().finish();
                    break;
                default:
                    break;
            }
        }
    }
}