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
import androidx.fragment.app.FragmentTransaction;

import com.example.fragmenttest2.BaseFragment;
import com.example.fragmenttest2.R;
import com.example.fragmenttest2.SetImage;

import java.util.Objects;


public class UserSelectionDialog extends DialogFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = requireActivity().getLayoutInflater().inflate(R.layout.dialog_userselection, null);

        AssetManager assetManager = Objects.requireNonNull(getActivity()).getAssets();
        SetImage setImage = new SetImage(assetManager);

        ImageButton imbtn = view.findViewById(R.id.cancel_button);
        setImage.setImageButtonBitmapFromAsset(imbtn, "title/cancel.png");

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
