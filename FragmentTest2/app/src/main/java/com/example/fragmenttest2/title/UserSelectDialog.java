package com.example.fragmenttest2.title;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.fragmenttest2.R;

import org.w3c.dom.Text;

public class UserSelectDialog extends DialogFragment {
    @Override
    public Dialog onCreateDialog(@NonNull Bundle savedInstanceState) {
        View view = requireActivity().getLayoutInflater().inflate(R.layout.dialog_userselect, null);
        TextView textView = view.findViewById(R.id.textView1);

        String A = "A";
        String B = "B";
        String newLine = "\n";

        String text = A + newLine + B;

        textView.setText(text);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()).setView(view);
        return builder.create();
    }
}
