package com.example.mainproject.dungeon;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.mainproject.R;

import java.util.Objects;

public class DungeonFragment extends Fragment {
    final String EXTRA_DATA = "com.example.mainproject.dungeon";
    private String UserName;
    private Button changeLayoutButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        UserName = Objects.requireNonNull(args).getString(EXTRA_DATA);

        FragmentTransaction fragmentTransaction = Objects.requireNonNull(getFragmentManager()).beginTransaction();
        fragmentTransaction.replace(R.id.DungeonLayoutContainer, DungeonLayoutFragment.newInstance(UserName));
        fragmentTransaction.replace(R.id.ChangeLayoutContainer, DisplayFloorFragment.newInstance(UserName));
        fragmentTransaction.commit();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dungeon, container, false);
    }

    @SuppressLint({"ClickableViewAccessibility", "Range"})
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button createFlorButton = view.findViewById(R.id.createFlorButton);
        createFlorButton.setOnClickListener(v -> {
            ConfirmDPCostDialog confirmDPCostDialog = new ConfirmDPCostDialog(
                    1,
                    n->{});
            confirmDPCostDialog.show(Objects.requireNonNull(getFragmentManager()), "ConfirmCreateDungeonDialog");
        });
        changeLayoutButton = view.findViewById(R.id.changeLayout);
        changeLayoutButton.setOnClickListener(v -> {
            if (DungeonLayoutFragment.changeLayoutFlag) {
                DungeonLayoutFragment.changeLayoutFlag = false;
                changeLayoutButton.setText(getString(R.string.NotChangeLayout));
                FragmentTransaction fragmentTransaction = Objects.requireNonNull(getFragmentManager()).beginTransaction();
                fragmentTransaction.replace(R.id.ChangeLayoutContainer, DisplayFloorFragment.newInstance(UserName));
                fragmentTransaction.commit();
            } else {
                DungeonLayoutFragment.changeLayoutFlag = true;
                changeLayoutButton.setText(getString(R.string.saveLayout));
                FragmentTransaction fragmentTransaction = Objects.requireNonNull(getFragmentManager()).beginTransaction();
                fragmentTransaction.replace(R.id.ChangeLayoutContainer, ChangeLayoutMenuFragment.newInstance(UserName));
                fragmentTransaction.commit();
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        DungeonLayoutFragment.changeLayoutFlag = false;
        changeLayoutButton.setText(getString(R.string.NotChangeLayout));
    }

    @NonNull
    public static DungeonFragment newInstance(String str) {
        DungeonFragment fragment = new DungeonFragment();
        Bundle barg = new Bundle();
        barg.putString(fragment.EXTRA_DATA, str);
        fragment.setArguments(barg);
        return fragment;
    }
}