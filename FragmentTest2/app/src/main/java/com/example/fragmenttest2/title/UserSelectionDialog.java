package com.example.fragmenttest2.title;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.os.Bundle;
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

        onClickListener bt = new onClickListener();

        moveSelectUserPage_btn.setOnClickListener(bt);
        moveUserAddPage_btn.setOnClickListener(bt);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.dialog_userselection, null));

        return builder.create();
    }

    public class onClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.moveSelectUserPage_button:
                    moveSelectUserPage_btn.setText("Push");
                    break;
                case R.id.moveUserAddPage_button:
                    moveUserAddPage_btn.setText("Push");
                    break;
                default:
                    break;
            }
        }
    }
}
