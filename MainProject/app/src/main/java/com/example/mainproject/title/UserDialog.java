package com.example.mainproject.title;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.mainproject.R;

import java.util.Objects;
import java.util.function.Consumer;


public class UserDialog extends DialogFragment {
    private final Consumer<Integer> callback;

    public UserDialog(Consumer<Integer> callback) {
        this.callback = callback;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = requireActivity().getLayoutInflater().inflate(R.layout.dialog_user, null);
        onClickListener clickListener = new onClickListener();

        view.findViewById(R.id.SignInPage_button).setOnClickListener(clickListener);
        view.findViewById(R.id.SignUpPage_button).setOnClickListener(clickListener);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()).setView(view);
        builder.setPositiveButton(R.string.DialogPositiveText, null);
        builder.setNeutralButton(R.string.DialogNegativeText, null);

        return builder.create();
    }


    private class onClickListener implements View.OnClickListener {
        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(@NonNull View v) {
            switch (v.getId()) {
                case R.id.SignInPage_button:
                    SignInDialog signInDialogFragment = new SignInDialog(callback);
                    signInDialogFragment.show(Objects.requireNonNull(getFragmentManager()), "userSelect");
                    dismiss();
                    break;
                case R.id.SignUpPage_button:
                    SignUpDialog signUpDialogFragment = new SignUpDialog(callback);
                    signUpDialogFragment.show(Objects.requireNonNull(getFragmentManager()), "userAdd");
                    dismiss();
                    break;
                default:
                    break;
            }
        }
    }
}
