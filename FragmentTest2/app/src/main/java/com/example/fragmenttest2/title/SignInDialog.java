package com.example.fragmenttest2.title;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.fragmenttest2.R;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SignInDialog extends DialogFragment {
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

        @Override
        public void onClick(View v) {
            MessageDigest sha256 = null;
            try {
                sha256 = MessageDigest.getInstance("SHA-512");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            byte[] sha256_result = sha256.digest(etPass.getText().toString().getBytes());

            Log.v("Name", etName.getText().toString());
            Log.v("Password", String.format("%04x", new BigInteger(1, sha256_result)));
            dismiss();
        }
    }
}
