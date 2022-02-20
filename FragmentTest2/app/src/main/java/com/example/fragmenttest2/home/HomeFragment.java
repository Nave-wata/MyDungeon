package com.example.fragmenttest2.home;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fragmenttest2.R;

public class HomeFragment extends Fragment {
    TextView mainText;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mainText = view.findViewById(R.id.Home_text);
    }

    public void setHomeText(String str) {
        mainText.setText(str);
    }


    public static HomeFragment newInstance(String str){
        HomeFragment fragment = new HomeFragment();
        Bundle barg = new Bundle();
        barg.putString("Message", str);
        fragment.setArguments(barg);
        return fragment;
    }
}
