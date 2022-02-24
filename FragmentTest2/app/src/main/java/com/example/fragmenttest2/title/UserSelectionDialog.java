package com.example.fragmenttest2.title;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.fragmenttest2.BaseFragment;
import com.example.fragmenttest2.R;
import com.example.fragmenttest2.SetImage;

import java.util.Objects;


public class UserSelectionDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = requireActivity().getLayoutInflater().inflate(R.layout.dialog_userselection, null);

        TextView tvTitle = view.findViewById(R.id.TextView_dialog_title);
        tvTitle.setText("Title");

        TextView tvMessage = view.findViewById(R.id.TextView_dialog_message);
        tvMessage.setText("Message");

        Button btn = view.findViewById(R.id.Button_dialog_positive);
        btn.setOnClickListener(new onClickListener(btn));

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()).setView(view);

        builder.setNegativeButton("Cancel", null);

        return builder.create();
    }

    public static UserSelectionDialog newInstance(String str){
        UserSelectionDialog fragment = new UserSelectionDialog();
        Bundle barg = new Bundle();
        barg.putString("Message", str);
        fragment.setArguments(barg);
        return fragment;
    }


    private class onClickListener implements View.OnClickListener {
        Button btn;

        private onClickListener(Button btn) {
            this.btn = btn;
        }

        @Override
        public void onClick(View v) {
            btn.setText("PUSH!");
        }
    }
}
