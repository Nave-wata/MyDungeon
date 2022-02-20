package com.example.fragmenttest2.title.fragment;

import android.content.res.AssetManager;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
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

    public String[] URLs = new String[] {"https://zipcloud.ibsnet.co.jp/api/search?zipcode=7830060",
            "https://zipcloud.ibsnet.co.jp/api/search?zipcode=7830060",
            "https://zipcloud.ibsnet.co.jp/api/search?zipcode=7830060"};
    public String[] str = new String[URLs.length];
    public ImageButton imageButton;

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
        imageButton = view.findViewById(R.id.image_button1);

        setImage.setImageViewBitmapFromAsset(imageView, "title/title.png");
        setImage.setImageButtonBitmapFromAsset(imageButton, "title/start.png");

        tmp();
    }

    public class onClickListener implements ImageButton.OnClickListener {
        @Override
        public void onClick(View view) {
            int id = view.getId();
            TitleActivity activity = (TitleActivity) getActivity();

            if (id == R.id.image_button1) {
                assert activity != null;
                activity.ChangeActivity();
            }
        }
    }

    private void tmp() {
        new AsyncRunnable(
                URLs[0],
                b->{
                    str[0] = new String(b);
                    Log.v("str1 = ", str[0]);
                    imageButton.setOnClickListener(new onClickListener());
                },
                e->{
                    str[0] = "Not found";
                    imageButton.setOnClickListener(new onClickListener());
                }
        ).execute();

        new AsyncRunnable(
                URLs[1],
                b->{
                    str[1] = new String(b);
                    Log.v("str2 = ", str[1]);
                    imageButton.setOnClickListener(new onClickListener());
                },
                e->{
                    str[1] = "Not found";
                    imageButton.setOnClickListener(new onClickListener());
                }
        ).execute();

        new AsyncRunnable(
                URLs[2],
                b->{
                    str[2] = new String(b);
                    Log.v("str3 = ", str[2]);
                    imageButton.setOnClickListener(new onClickListener());
                },
                e->{
                    str[2] = "Not found";
                    imageButton.setOnClickListener(new onClickListener());
                }
        ).execute();
    }
}
