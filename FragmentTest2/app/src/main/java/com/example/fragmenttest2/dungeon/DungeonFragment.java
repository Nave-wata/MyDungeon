package com.example.fragmenttest2.dungeon;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.fragmenttest2.R;

public class DungeonFragment extends Fragment {
    TextView mainText;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dungeon, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mainText = view.findViewById(R.id.Dungeon_text);
    }

    public void setDungeonText(String str) {
        mainText.setText(str);
    }

    public static DungeonFragment newInstance(String str){
        DungeonFragment fragment = new DungeonFragment();
        Bundle barg = new Bundle();
        barg.putString("Message", str);
        fragment.setArguments(barg);
        return fragment;
    }
}
