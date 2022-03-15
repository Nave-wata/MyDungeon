package com.example.mainproject.dungeon;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.mainproject.R;
import com.example.mainproject.SetImage;

import java.util.Objects;

public class DeleteWallDialog extends DialogFragment {
    final int I;
    final int J;
    private SetImage setImage;

    DeleteWallDialog(int I, int J) {
        this.I = I;
        this.J = J;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = requireActivity().getLayoutInflater().inflate(R.layout.dialog_deletewall, null);
        AssetManager assetManager = Objects.requireNonNull(getActivity()).getAssets();
        setImage = new SetImage(assetManager);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()).setView(view);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                setImage.setImageViewBitmapFromAsset(DungeonLayoutFragment.dungeonPeaces[I][J], "");
                DungeonLayoutFragment.dungeonInfo[I][J] = 0;
            }
        });
        builder.setNeutralButton("キャンセル", null);
        return builder.create();
    }
}
