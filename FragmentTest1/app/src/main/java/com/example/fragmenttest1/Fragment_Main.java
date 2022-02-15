package com.example.fragmenttest1;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class Fragment_Main extends Fragment {
    static { System.loadLibrary("fragmenttest1"); }
    public native String stringFromJNI();

    Button bt;
    SeekBar sb1;
    SeekBar sb2;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bt = view.findViewById(R.id.button);
        sb1 = view.findViewById(R.id.seekBar1);
        sb2 = view.findViewById(R.id.seekBar2);

        onSeekBarChangeListener seekBars = new onSeekBarChangeListener();

        sb1.setOnSeekBarChangeListener(seekBars);
        sb2.setOnSeekBarChangeListener(seekBars);
    }

    private class onSeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {
        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            String str = String.valueOf(i);
            bt.setText(str);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }
}
