package com.example.main.title;

import android.annotation.SuppressLint;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.main.R;
import com.example.main.SetImage;

import java.util.Objects;

public class TitleFragment extends Fragment {
    public final String EXTRA_DATA = "com.example.fragmenttest2.title";
    private SetImage setImage;
    private ImageButton startButton;
    private TextView UserNameText;

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

        AssetManager assetManager = Objects.requireNonNull(getActivity()).getAssets();
        setImage = new SetImage(assetManager);
        onClickListener clickListener = new onClickListener();

        ImageView imageView = view.findViewById(R.id.image_view1);
        ImageButton userSelectionButton = view.findViewById(R.id.userSelection_Button);
        startButton = view.findViewById(R.id.Start_Button);
        UserNameText = view.findViewById(R.id.UserNameText);

        setImage.setImageViewBitmapFromAsset(imageView, "title/title.png");
        setImage.setImageButtonBitmapFromAsset(userSelectionButton, "title/ic_user.png");
        setImage.setImageButtonBitmapFromAsset(startButton, "title/non_start.png");

        userSelectionButton.setOnClickListener(clickListener);
    }

    public void setOnClickStartButton() {
        setImage.setImageViewBitmapFromAsset(startButton, "title/start.png");
        startButton.setOnClickListener(new onClickListener());
        UserNameText.setText(TitleActivity.UserName);
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
                    UserDialog dialogFragment = new UserDialog(b->setOnClickStartButton());
                    dialogFragment.show(Objects.requireNonNull(getFragmentManager()), "user");
                    break;
                default:
                    break;
            }
        }
    }
}
