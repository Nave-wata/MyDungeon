package com.example.mainproject.dungeon;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.mainproject.R;

import java.util.Objects;

public class DungeonFragment extends Fragment {
    final String EXTRA_DATA = "com.example.mainproject.dungeon";
    private String UserName;
    private Button changeLayoutButton;
    private static ImageView dungeonPeace;
    private static int preDx, preDy;

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

        dungeonPeace = view.findViewById(R.id.dungeonPeace);

        Button createFlorButton = view.findViewById(R.id.createFlorButton);
        createFlorButton.setOnClickListener(v -> {
            ConfirmCreateDungeonDialog confirmCreateDungeonDialog = new ConfirmCreateDungeonDialog();
            confirmCreateDungeonDialog.show(Objects.requireNonNull(getFragmentManager()), "ConfirmCreateDungeonDialog");
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

    public static class onTouchListener implements View.OnTouchListener {
        final ImageView wallImage;

        public onTouchListener(ImageView wallImage) {
            this.wallImage = wallImage;
            dungeonPeace = wallImage;
        }

        @Override
        public boolean onTouch(View view, @NonNull MotionEvent motionEvent) {
            int newDx = (int) motionEvent.getRawX();
            int newDy = (int) motionEvent.getRawY();

            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_MOVE:
                    view.performClick();
                    int dx = dungeonPeace.getLeft() + (newDx - preDx); // ここの0はsetXでかわってまう
                    int dy = dungeonPeace.getTop() + (newDy - preDy);  // ここの0はsetYでかわってまう
                    int imgW = dx + dungeonPeace.getWidth();
                    int imgH = dy + dungeonPeace.getHeight();
                    dungeonPeace.layout(dx, dy, imgW, imgH);
                    break;
                case MotionEvent.ACTION_DOWN:
                    // nothing to do
                    break;
                case MotionEvent.ACTION_UP:
                    // nothing to do
                    break;
            }

            preDx = newDx;
            preDy = newDy;
            return true;
        }
    }
}