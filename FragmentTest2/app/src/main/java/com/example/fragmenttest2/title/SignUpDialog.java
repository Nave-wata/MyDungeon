package com.example.fragmenttest2.title;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

import com.example.fragmenttest2.R;
import com.example.fragmenttest2.asynchronous.AppDatabase;
import com.example.fragmenttest2.asynchronous.AppDatabaseSingleton;
import com.example.fragmenttest2.asynchronous.usersinfo.DataSave;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SignUpDialog extends DialogFragment {
    static {
        System.loadLibrary("fragmenttest2");
    }
    static native String HASH(String password, String salt);


    @Override
    public Dialog onCreateDialog(@NonNull Bundle savedInstanceState) {
        View view = requireActivity().getLayoutInflater().inflate(R.layout.dialog_signup, null);

        EditText etName = view.findViewById(R.id.TitleSUUserName);
        EditText etPass = view.findViewById(R.id.TitleSUPassword);

        Button btn = view.findViewById(R.id.SignUp_button);
        btn.setOnClickListener(new SignUpDialog.onClickListener(etName, etPass));

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
            final String regex = "[0123456789abcdefghijklmnopqrstyvwxyzABCDEFGHIJKLMNOPQRSTYVWXYZ]";
            final String name = etName.getText().toString();
            final String password = etPass.getText().toString();
            final String[] nameSplit = name.split("");
            final String[] passwordSplit = password.split("");
            boolean flag = true;
            boolean nameFlag = true;
            boolean passwordFlag = true;

            if (name.length() == 0 && nameFlag) {
                etName.setError(getString(R.string.errorNotInput));
                flag = false;
                nameFlag = false;
            }
            if (password.length() == 0 && passwordFlag) {
                etPass.setError(getString(R.string.errorNotInput));
                flag = false;
                passwordFlag = false;
            }
            if (nameFlag) {
                for (String s : nameSplit) {
                    if (!s.matches(regex)) {
                        etName.setError(getString(R.string.errorNotInText));
                        flag = false;
                        break;
                    }
                }
            }
            if (passwordFlag) {
                for (String s : passwordSplit) {
                    if (!s.matches(regex)) {
                        etPass.setError(getString(R.string.errorNotInText));
                        flag = false;
                        break;
                    }
                }
            }

            if (flag) {
                LocalDateTime nowDateTime = LocalDateTime.now();
                final AppDatabase db = AppDatabaseSingleton.getInstance(getActivity().getApplicationContext());
                final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                //final String nowTime = nowDateTime.format(dateTimeFormatter);
                final String salt = getRandomString(15, 25);
                final String hash = HASH(etPass.getText().toString(), salt);
                new DataSave(db, name, salt, hash).execute();
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
