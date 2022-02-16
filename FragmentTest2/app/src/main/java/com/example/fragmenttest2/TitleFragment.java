package com.example.fragmenttest2;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

public class TitleFragment extends Fragment {
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_title, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        AssetManager assetManager = getActivity().getAssets();
        SetImage setImage = new SetImage(assetManager);
        ImageView imageView = view.findViewById(R.id.image_view1);
        ImageButton imageButton = view.findViewById(R.id.image_button1);

        setImage.setImageViewBitmapFromAsset(imageView, "title/title.png");
        setImage.setImageButtonBitmapFromAsset(imageButton, "title/start.png");

        imageButton.setOnClickListener(new onClickListener());
    }

    public class onClickListener implements ImageButton.OnClickListener {
        @Override
        public void onClick(View view) {
            int id = view.getId();
            TitleActivity activity = (TitleActivity) getActivity();

            switch (id) {
                case R.id.image_button1:
                    activity.ChangeActivity();
                    break;
                default:
                    break;
            }
        }
    }
}