package com.example.fragmenttest2.title;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.fragmenttest2.R;

public class SignInDialog extends DialogFragment {
    Button btn;

    @Override
    public Dialog onCreateDialog(@NonNull Bundle savedInstanceState) {
        View view = requireActivity().getLayoutInflater().inflate(R.layout.dialog_signin, null);

        btn = view.findViewById(R.id.SignIn_button);
        btn.setOnClickListener(new onClickListener());

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()).setView(view);
        return builder.create();
    }


    private class onClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            dismiss();
        }
    }
}
