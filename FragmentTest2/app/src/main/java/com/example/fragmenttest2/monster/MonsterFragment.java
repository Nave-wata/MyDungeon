package com.example.fragmenttest2.monster;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.fragmenttest2.R;

import java.util.Objects;

public class MonsterFragment extends Fragment {
    public final String ARGS_NAME = "com.example.fragmenttest2.monster";
    TextView mainText;
    String str;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        str = Objects.requireNonNull(args).getString(ARGS_NAME);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_monster, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mainText = view.findViewById(R.id.Monster_text);

        mainText.setText(str);
    }

    public static MonsterFragment newInstance(String str){
        MonsterFragment fragment = new MonsterFragment();
        Bundle barg = new Bundle();
        barg.putString(fragment.ARGS_NAME, str);
        fragment.setArguments(barg);
        return fragment;
    }
}
