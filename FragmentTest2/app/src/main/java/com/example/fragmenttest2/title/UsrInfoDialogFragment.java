package com.example.fragmenttest2.title;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.fragmenttest2.BuildConfig;

public class UsrInfoDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(@NonNull Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Title")
                .setMessage("Message")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.v("Click", "DialogFragmentButton");
                    }
                })
                .setNegativeButton("Cancel", null)
                .setNeutralButton("After", null);

        return builder.create();
    }
}
