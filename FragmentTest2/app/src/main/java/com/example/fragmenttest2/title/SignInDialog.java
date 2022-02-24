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

public class SignInDialog extends DialogFragment {
    @Override
    public Dialog onCreateDialog(@NonNull Bundle savedInstanceState) {
        View view = requireActivity().getLayoutInflater().inflate(R.layout.dialog_signin, null);

        EditText ETName = view.findViewById(R.id.TitleETUserName);
        EditText ETPass = view.findViewById(R.id.TitleETPassword);
        String name = ETName.getText().toString();
        String pass = ETPass.getText().toString();
        Log.v("Name", name);
        Log.v("Password", pass);

        Button btn = view.findViewById(R.id.SignIn_button);
        btn.setOnClickListener(new onClickListener(name, pass));

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()).setView(view);
        return builder.create();
    }


    private class onClickListener implements View.OnClickListener {
        String name;
        String pass;

        public onClickListener(String name, String pass) {
            this.name = name;
            this.pass = pass;
        }

        @Override
        public void onClick(View v) {
            Log.v("Name", name);
            Log.v("Password", pass);
            dismiss();
        }
    }
}
