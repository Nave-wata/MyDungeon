package com.example.fragmenttest2.title;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

import com.example.fragmenttest2.R;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class SignInDialog extends DialogFragment {
    static {
        System.loadLibrary("fragmenttest2");
    }
    static native String HASH(String password, String salt);

    @Override
    public Dialog onCreateDialog(@NonNull Bundle savedInstanceState) {
        View view = requireActivity().getLayoutInflater().inflate(R.layout.dialog_signin, null);

        EditText etName = view.findViewById(R.id.TitleETUserName);
        EditText etPass = view.findViewById(R.id.TitleETPassword);

        Button btn = view.findViewById(R.id.SignIn_button);
        btn.setOnClickListener(new onClickListener(etName, etPass));

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()).setView(view);
        return builder.create();
    }


    private class onClickListener implements View.OnClickListener {
        EditText etName;
        EditText etPass;

        public onClickListener(EditText etName, EditText etPass) {
            this.etName = etName;
            this.etPass = etPass;
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onClick(View v) {
            final String name = etName.getText().toString();
            final String password = etPass.getText().toString();

            if (name.length() != 0) {
                etName.setError(getString(R.string.errorNotInput));
            } else if (password.length() != 0) {
                etPass.setError(getString(R.string.errorNotInput));
            } else if (false) {

            } else {
                LocalDateTime nowDateTime = LocalDateTime.now();
                final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                final String nowTime = nowDateTime.format(dateTimeFormatter);
                final String randStr = getRandomString(15, 25);
                final String hash = HASH(etPass.getText().toString(), randStr);
                dismiss();
            }
        }

        @NonNull
        String getRandomString(int min, int max) {
            String str = "0123456789abcdefghijklmnopqrstyvwxyzABCDEFGHIJKLMNOPQRSTYVWXYZ";
            StringBuilder builder = new StringBuilder();
            int randInt = (int) (Math.random() * (max - min)) + min;

            for (int j = 0; j < randInt; j++) {
                int tmp = (int) (str.length() * Math.random());
                builder.append(str.charAt(tmp));
            }

            return builder.toString();
        }
    }
}
