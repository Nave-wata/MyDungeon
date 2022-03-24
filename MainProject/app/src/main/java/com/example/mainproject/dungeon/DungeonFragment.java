package com.example.mainproject.dungeon;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.mainproject.DungeonsInfo;
import com.example.mainproject.R;
import com.example.mainproject.asynchronous.AppDatabase;
import com.example.mainproject.asynchronous.AppDatabaseSingleton;
import com.example.mainproject.asynchronous.dungeonlayout.UpdateDungeonLayout;

import java.util.Objects;


public class DungeonFragment extends Fragment {
    final String EXTRA_DATA = "com.example.mainproject.dungeon";
    private String UserName;
    private Button changeLayoutButton;

    public static final int NOT_DUNGEON_WALL = 0;
    public static final int DUNGEON_NOTHING = 1;
    public static final int DUNGEON_I_DOOR = 2; // start
    public static final int DUNGEON_O_DOOR = 3; // end
    public static final int DUNGEON_BOSE = 4;
    public static final int DUNGEON_DOOR = 5;
    public static final int DUNGEON_WALL = 6;
    public static final int DUNGEON_TRAP1 = 7;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        UserName = Objects.requireNonNull(args).getString(EXTRA_DATA);

        FragmentTransaction fragmentTransaction = Objects.requireNonNull(getFragmentManager()).beginTransaction();
        fragmentTransaction.replace(R.id.DungeonLayoutContainer, DungeonLayoutFragment.newInstance(UserName));
        fragmentTransaction.add(R.id.DungeonLayoutContainer, SetCharacterImageFragment.newInstance(UserName));
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
            //ConfirmDPCostDialog confirmDPCostDialog = new ConfirmDPCostDialog(
            //        "ConfirmCreateFloor",
            //        n->{});
            //confirmDPCostDialog.show(Objects.requireNonNull(getFragmentManager()), "ConfirmCreateDungeonDialog");
        });
        changeLayoutButton = view.findViewById(R.id.changeLayout);
        changeLayoutButton.setOnClickListener(v -> {
            if (DungeonLayoutFragment.changeLayoutFlag) {
                final AppDatabase db = AppDatabaseSingleton.getInstance(Objects.requireNonNull(getActivity()).getApplicationContext());
                new UpdateDungeonLayout(
                        db,
                        UserName,
                        DungeonsInfo.dungeonInfo,
                        b-> Log.v("My", "OK"),
                        e-> Log.v("My", "" + e)
                ).execute();
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
        final AppDatabase db = AppDatabaseSingleton.getInstance(Objects.requireNonNull(getActivity()).getApplicationContext());
        new UpdateDungeonLayout(
                db,
                UserName,
                DungeonsInfo.dungeonInfo,
                b-> Log.v("My", "OK"),
                e-> Log.v("My", "" + e)
        ).execute();
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