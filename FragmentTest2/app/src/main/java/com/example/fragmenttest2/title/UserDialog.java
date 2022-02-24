package com.example.fragmenttest2.title;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.fragmenttest2.R;

import java.util.Objects;


public class UserDialog extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = requireActivity().getLayoutInflater().inflate(R.layout.dialog_user, null);
        onClickListener clickListener = new onClickListener();

        view.findViewById(R.id.selectPage_button).setOnClickListener(clickListener);
        view.findViewById(R.id.addPage_button).setOnClickListener(clickListener);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()).setView(view);
        builder.setPositiveButton(R.string.DialogPositiveText, null);
        builder.setNeutralButton(R.string.DialogNegativeText, null);

        return builder.create();
    }


    private class onClickListener implements View.OnClickListener {
        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.selectPage_button:
                    UserSelectDialog dialogFragment = new UserSelectDialog();
                    dialogFragment.show(Objects.requireNonNull(getFragmentManager()), "userSelect");
                    break;
                case R.id.addPage_button:
                    break;
                default:
                    break;
            }
        }
    }
}
