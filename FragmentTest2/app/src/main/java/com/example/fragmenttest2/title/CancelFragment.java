package com.example.fragmenttest2.title;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.fragmenttest2.BaseFragment;
import com.example.fragmenttest2.R;
import com.example.fragmenttest2.SetImage;
import com.example.fragmenttest2.home.HomeFragment;
import com.example.fragmenttest2.monster.MonsterFragment;

import java.util.Objects;

public class CancelFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FragmentTransaction fragmentTransaction = Objects.requireNonNull(getFragmentManager()).beginTransaction();
        fragmentTransaction.replace(R.id.UserSelectionDialog_Container, UserSelectionDialog.newInstance("null"));
        fragmentTransaction.commit();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cancel, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        AssetManager assetManager = Objects.requireNonNull(getActivity()).getAssets();
        SetImage setImage = new SetImage(assetManager);

        ImageButton btn = view.findViewById(R.id.cancel_button);
        setImage.setImageButtonBitmapFromAsset(btn, "title/cancel.png");

        btn.setOnClickListener(new onClickListener());
    }

    class onClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Log.v("Click", "Cancel_Button");
        }
    }
}
