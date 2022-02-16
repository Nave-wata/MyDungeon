package com.example.fragmenttest2;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_base, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        AssetManager assetManager = getActivity().getAssets();
        SetImage setImage = new SetImage(assetManager);
        ImageButton homeButton = view.findViewById(R.id.home_button);
        ImageButton dungeonButton;

        //setImage.setImageButtonBitmapFromAsset(imageButton, "title/start.png");
    }

    public static TitleFragment newInstance(String str){

        TitleFragment fragment = new TitleFragment ();

        Bundle barg = new Bundle();
        barg.putString("Message", str);
        fragment.setArguments(barg);

        return fragment;
    }
}