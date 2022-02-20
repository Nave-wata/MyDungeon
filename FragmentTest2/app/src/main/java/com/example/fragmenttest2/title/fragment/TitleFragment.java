package com.example.fragmenttest2.title.fragment;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.fragmenttest2.R;
import com.example.fragmenttest2.SetImage;
import com.example.fragmenttest2.asynchronous.AsyncRunnable;
import com.example.fragmenttest2.title.activity.TitleActivity;

import java.util.Objects;

public class TitleFragment extends Fragment {
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_title, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        AssetManager assetManager = Objects.requireNonNull(getActivity()).getAssets();
        SetImage setImage = new SetImage(assetManager);
        ImageView imageView = view.findViewById(R.id.image_view1);
        ImageButton imageButton = view.findViewById(R.id.image_button1);

        setImage.setImageViewBitmapFromAsset(imageView, "title/title.png");
        setImage.setImageButtonBitmapFromAsset(imageButton, "title/start.png");

        imageButton.setOnClickListener(new onClickListener());
    }

    public class onClickListener implements ImageButton.OnClickListener {
        public String[] URLs = new String[] {"http://192.168.3.21:8000/one",
                "http://192.168.3.21:8000/two",
                "http://192.168.3.21:8000/three"};
        public String[] str = new String[URLs.length];

        @Override
        public void onClick(View view) {
            int id = view.getId();
            TitleActivity activity = (TitleActivity) getActivity();

            if (id == R.id.image_button1) {
                assert activity != null;
                activity.ChangeActivity();
            }
        }

        private void tmp() {
            new AsyncRunnable(
                    URLs[0],
                    b->str[0] = new String(b),
                    e->str[0] = "Not found"
            ).execute();

            new AsyncRunnable(
                    URLs[1],
                    b->str[1] = new String(b),
                    e->str[1] = "Not found"
            ).execute();

            new AsyncRunnable(
                    URLs[2],
                    b->str[2] = new String(b),
                    e->str[2] = "Not found"
            ).execute();
        }
    }
}
