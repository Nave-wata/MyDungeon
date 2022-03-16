package com.example.mainproject.dungeon;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
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
        @SuppressLint("InflateParams")
        View view = requireActivity().getLayoutInflater().inflate(R.layout.dialog_deletewall, null);
        AssetManager assetManager = Objects.requireNonNull(getActivity()).getAssets();
        setImage = new SetImage(assetManager);

        ImageView ic_DP = view.findViewById(R.id.deleteWall_ic_DP);
        ImageView ic_right = view.findViewById(R.id.deleteWall_right_ic);
        TextView before_DP_text = view.findViewById(R.id.deleteWall_before_DP);
        TextView after_DP_text = view.findViewById(R.id.deleteWall_after_DP);

        setImage.setImageViewBitmapFromAsset(ic_DP, "base_menu/dungeonpower.png");
        setImage.setImageViewBitmapFromAsset(ic_right, "dungeon/DP_change.png");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()).setView(view);
        builder.setPositiveButton(getString(R.string.DialogPositiveText), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                setImage.setImageViewBitmapFromAsset(DungeonLayoutFragment.dungeonPeaces[I][J], "");
                DungeonLayoutFragment.dungeonInfo[I][J] = 0;
            }
        });
        builder.setNeutralButton(getString(R.string.DialogNegativeText), null);
        return builder.create();
    }
}
