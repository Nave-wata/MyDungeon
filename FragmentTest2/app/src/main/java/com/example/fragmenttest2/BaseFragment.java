package com.example.fragmenttest2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BaseFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_base, container, false);
    }

    public static TitleFragment newInstance(String str){

        TitleFragment fragment = new TitleFragment ();

        Bundle barg = new Bundle();
        barg.putString("Message", str);
        fragment.setArguments(barg);

        return fragment;
    }
}