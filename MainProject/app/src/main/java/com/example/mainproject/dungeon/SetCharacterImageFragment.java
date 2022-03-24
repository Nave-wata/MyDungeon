package com.example.mainproject.dungeon;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.mainproject.MainActivity;
import com.example.mainproject.R;

import java.util.Objects;

public class SetCharacterImageFragment extends Fragment {
    final String EXTRA_DATA = "com.example.mainproject.dungeon";
    private String UserName;
    private androidx.constraintlayout.widget.ConstraintLayout topContainer;
    private ViewTreeObserver.OnGlobalLayoutListener globalLayoutListener;
    private int preDx, preDy;
    private int oneSize;
    private int maxSize;
    final int widthNum = MainActivity.widthNum;
    final int heightNum = MainActivity.heightNum;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        UserName = Objects.requireNonNull(args).getString(EXTRA_DATA);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ConstraintLayout layout = new ConstraintLayout(Objects.requireNonNull(getContext()));
        View view = inflater.inflate(R.layout.fragment_setcharacterimage, container, false);

        topContainer = view.findViewById(R.id.fragment_setCharacterImage);
        globalLayoutListener = () -> {
            int width = topContainer.getWidth();
            topContainer.getViewTreeObserver().removeOnGlobalLayoutListener(globalLayoutListener);
            oneSize = width / widthNum;
            maxSize = oneSize * widthNum;
        };
        topContainer.getViewTreeObserver().addOnGlobalLayoutListener(globalLayoutListener);

        layout.addView(view);
        return  layout.getRootView();
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
