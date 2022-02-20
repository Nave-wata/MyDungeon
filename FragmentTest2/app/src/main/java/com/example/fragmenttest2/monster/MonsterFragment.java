package com.example.fragmenttest2.monster;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.fragmenttest2.R;

public class MonsterFragment extends Fragment {
    TextView mainText;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_monster, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mainText = view.findViewById(R.id.Monster_text);
    }

    public void setMonsterText(String str) {
        mainText.setText(str);
    }


    public static MonsterFragment newInstance(String str){
        MonsterFragment fragment = new MonsterFragment();
        Bundle barg = new Bundle();
        barg.putString("Message", str);
        fragment.setArguments(barg);
        return fragment;
    }
}
