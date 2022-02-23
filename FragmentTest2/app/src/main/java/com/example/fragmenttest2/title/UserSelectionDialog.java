package com.example.fragmenttest2.title;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.fragmenttest2.BaseFragment;
import com.example.fragmenttest2.R;
import com.example.fragmenttest2.SetImage;

import java.util.Objects;


public class UserSelectionDialog extends DialogFragment {
    Button moveSelectUserPage_btn;
    Button moveUserAddPage_btn;

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        moveSelectUserPage_btn = view.findViewById(R.id.moveSelectUserPage_button);
        moveUserAddPage_btn =  view.findViewById(R.id.moveUserAddPage_button);

        moveSelectUserPage_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                moveSelectUserPage_btn.setText("push");
            }
        });
        moveUserAddPage_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                moveUserAddPage_btn.setText("push");
            }
        });
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.dialog_userselection, null));

        return builder.create();
    }
}
