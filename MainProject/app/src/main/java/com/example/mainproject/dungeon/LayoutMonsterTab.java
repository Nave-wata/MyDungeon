package com.example.mainproject.dungeon;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class LayoutMonsterTab extends FragmentStateAdapter {
    private int NUM_PAGES = 2;

    public LayoutMonsterTab(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = null;
        
        switch (position) {
            case 1:
                fragment = new ChangeLayoutMenuFragment();
                break;
            case 2:
                fragment = new ChangeMonsterMenuFragment();
                break;
        }
        
        return fragment;
    }

    @Override
    public int getItemCount() {
        return NUM_PAGES;
    }
}