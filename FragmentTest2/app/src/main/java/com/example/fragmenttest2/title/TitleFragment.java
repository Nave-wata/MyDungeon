package com.example.fragmenttest2.title;

import android.annotation.SuppressLint;
import android.content.res.AssetManager;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.fragmenttest2.BaseFragment;
import com.example.fragmenttest2.R;
import com.example.fragmenttest2.SetImage;
import com.example.fragmenttest2.asynchronous.AsyncRunnable;

import java.util.Objects;

public class TitleFragment extends Fragment {
    public final String EXTRA_DATA = "com.example.fragmenttest2.title";
    public AssetManager assetManager;
    public SetImage setImage;

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_title, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        assetManager = Objects.requireNonNull(getActivity()).getAssets();
        setImage = new SetImage(assetManager);
        onClickListener clickListener = new onClickListener();

        ImageView imageView = view.findViewById(R.id.image_view1);
        ImageButton userSelectionButton = view.findViewById(R.id.userSelection_Button);
        ImageButton startButton = view.findViewById(R.id.Start_Button);

        setImage.setImageViewBitmapFromAsset(imageView, "title/title.png");
        setImage.setImageButtonBitmapFromAsset(userSelectionButton, "title/ic_user.png");
        setImage.setImageButtonBitmapFromAsset(startButton, "title/non_start.png");

        startButton.setOnClickListener(clickListener); // 押せるタイミング調整
        userSelectionButton.setOnClickListener(clickListener);
    }

    @NonNull
    public static TitleFragment newInstance(){
        TitleFragment fragment = new TitleFragment();
        Bundle barg = new Bundle();
        barg.putString(fragment.EXTRA_DATA, null);
        fragment.setArguments(barg);
        return fragment;
    }


    public class onClickListener implements ImageButton.OnClickListener {
        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(@NonNull View view) {
            int id = view.getId();
            TitleActivity activity = (TitleActivity) getActivity();

            switch (id){
                case R.id.Start_Button:
                    Objects.requireNonNull(activity).ChangeActivity();
                    break;
                case R.id.userSelection_Button:
                    UserDialog dialogFragment = new UserDialog();
                    dialogFragment.show(Objects.requireNonNull(getFragmentManager()), "user");
                    break;
                default:
                    break;
            }
        }
    }
}
