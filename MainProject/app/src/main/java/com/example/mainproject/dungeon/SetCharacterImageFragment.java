package com.example.mainproject.dungeon;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.mainproject.DungeonsInfo;
import com.example.mainproject.R;
import com.example.mainproject.SetImage;
import com.example.mainproject.asynchronous.TimerPossession;

import java.util.Objects;

public class SetCharacterImageFragment extends Fragment {
    final String EXTRA_DATA = "com.example.mainproject.dungeon";
    private String UserName;
    TimerPossession timerPossession;
    private androidx.constraintlayout.widget.ConstraintLayout topContainer;
    private ViewTreeObserver.OnGlobalLayoutListener globalLayoutListener;
    private int oneSize;
    private ImageView imageView;
    final int widthNum = DungeonsInfo.widthNum;
    final int heightNum = DungeonsInfo.heightNum;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        UserName = Objects.requireNonNull(args).getString(EXTRA_DATA);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ConstraintLayout layout = new ConstraintLayout(Objects.requireNonNull(getContext()));
        View view = inflater.inflate(R.layout.fragment_setcharacterimage, container, false);
        AssetManager assetManager = Objects.requireNonNull(getActivity()).getAssets();
        SetImage setImage = new SetImage(assetManager);

        imageView = new ImageView(getContext());

        topContainer = view.findViewById(R.id.fragment_setCharacterImage);
        globalLayoutListener = () -> {
            int width = topContainer.getWidth();
            topContainer.getViewTreeObserver().removeOnGlobalLayoutListener(globalLayoutListener);
            oneSize = width / widthNum;

            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(oneSize, oneSize);
            moveCharacterImage(imageView);
            imageView.setLayoutParams(layoutParams);
        };
        topContainer.getViewTreeObserver().addOnGlobalLayoutListener(globalLayoutListener);

        setImage.setImageViewBitmapFromAsset(imageView, "character/enemy/hito.png");

        layout.addView(view);
        layout.addView(imageView);

        return  layout.getRootView();
    }

    private void moveCharacterImage(ImageView imageView) {
        imageView.setX(9 * oneSize);
        imageView.setY(19 * oneSize);
        timerPossession = new TimerPossession(imageView, oneSize);
        timerPossession.setCharacterImage_runnable_Run();
    }

    @Override
    public void onStop() {
        super.onStop();
        timerPossession.setCharacterImage_runnable_Stop();
    }

    @NonNull
    public static SetCharacterImageFragment newInstance(String str){
        SetCharacterImageFragment fragment = new SetCharacterImageFragment();
        Bundle barg = new Bundle();
        barg.putString(fragment.EXTRA_DATA, str);
        fragment.setArguments(barg);
        return fragment;
    }
}