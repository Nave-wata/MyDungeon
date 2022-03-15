package com.example.mainproject.dungeon;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.mainproject.R;
import com.example.mainproject.SetImage;

import java.util.Objects;

public class ConfirmCreateDungeonDialog extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = requireActivity().getLayoutInflater().inflate(R.layout.dialog_confirmcreatedungeon, null);
        AssetManager assetManager = Objects.requireNonNull(getActivity()).getAssets();
        SetImage setImage = new SetImage(assetManager);

        ImageView ic_DP = view.findViewById(R.id.ConfirmCreateDungeonDialog_ic_DP);
        ImageView ic_right = view.findViewById(R.id.ConfirmCreateDungeonDialog_right_ic);
        TextView before_DP_text = view.findViewById(R.id.ConfirmCreateDungeonDialog_before_DP);
        TextView after_DP_text = view.findViewById(R.id.ConfirmCreateDungeonDialog_after_DP);

        setImage.setImageViewBitmapFromAsset(ic_DP, "base_menu/dungeonpower.png");
        setImage.setImageViewBitmapFromAsset(ic_right, "dungeon/DP_change.png");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()).setView(view);
        builder.setPositiveButton(getString(R.string.DialogPositiveText), null);
        builder.setNeutralButton(getString(R.string.DialogNegativeText), null);
        return builder.create();
    }
}
