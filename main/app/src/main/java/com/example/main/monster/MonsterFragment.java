package com.example.main.monster;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.main.R;

import java.util.Objects;

public class MonsterFragment extends Fragment {
    final String EXTRA_DATA = "com.example.fragmenttest2.monster";
    private String str;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        str = Objects.requireNonNull(args).getString(EXTRA_DATA);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_monster, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView mainText = view.findViewById(R.id.Monster_text);

        mainText.setText(str);
    }

    @NonNull
    public static MonsterFragment newInstance(String str){
        MonsterFragment fragment = new MonsterFragment();
        Bundle barg = new Bundle();
        barg.putString(fragment.EXTRA_DATA, str);
        fragment.setArguments(barg);
        return fragment;
    }
}
