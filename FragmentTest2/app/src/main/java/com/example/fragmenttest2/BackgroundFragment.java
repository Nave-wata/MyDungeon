package com.example.fragmenttest2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.fragmenttest2.asynchronous.AsyncRunnable;

public class BackgroundFragment extends Fragment {
    public String[] URLs = new String[] {"http://192.168.3.21:8000/one",
            "http://192.168.3.21:8000/two",
            "http://192.168.3.21:8000/three"};
    public String[] str = new String[URLs.length];

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_background, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tmp();
    }

    private void tmp() {
        new AsyncRunnable(
                URLs[0],
                b->str[0] = new String(b),
                e->str[0] = "Not found"
        ).execute();

        new AsyncRunnable(
                URLs[1],
                b->str[1] = new String(b),
                e->str[1] = "Not found"
        ).execute();

        new AsyncRunnable(
                URLs[2],
                b->str[2] = new String(b),
                e->str[2] = "Not found"
        ).execute();
    }
}
