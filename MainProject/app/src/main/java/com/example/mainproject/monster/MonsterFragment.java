package com.example.mainproject.monster;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.mainproject.R;
import com.example.mainproject.asynchronous.AsyncRunnable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;


public class MonsterFragment extends Fragment {
    final String EXTRA_DATA = "com.example.mainproject.monster";
    private String UserName;
    final String URL = "https://zipcloud.ibsnet.co.jp/api/search?zipcode=0791143";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        UserName = Objects.requireNonNull(args).getString(EXTRA_DATA);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_monster, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView mainText = view.findViewById(R.id.Monster_text);

        new AsyncRunnable(
                URL,
                b->{
                    String response = new String(b);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("results");
                        String address1 = null;
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonData = jsonArray.getJSONObject(i);
                            address1 = jsonData.getString("address1");
                        }
                        mainText.setText(address1);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        mainText.setText(response);
                    }
                },
                e->{}
        ).execute();
    }

    @NonNull
    public static MonsterFragment newInstance(String str){
        MonsterFragment fragment = new MonsterFragment();
        Bundle barg = new Bundle();
        barg.putString(fragment.EXTRA_DATA, str);
        fragment.setArguments(barg);
        return fragment;
    }
}