package com.example.mainproject.dungeon;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

import com.example.mainproject.R;
import com.example.mainproject.SetImage;

import java.util.Objects;
import java.util.function.Consumer;

public class SetDungeonPeaceDialog extends DialogFragment {
    final Consumer<Boolean> callback;

    public SetDungeonPeaceDialog(Consumer<Boolean> callback) {
        this.callback = callback;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = requireActivity().getLayoutInflater().inflate(R.layout.dialog_setdungeonwall, null);
        AssetManager assetManager = Objects.requireNonNull(getActivity()).getAssets();
        SetImage setImage = new SetImage(assetManager);

        ImageView ic_DP = view.findViewById(R.id.setDungeonWall_ic_DP);
        ImageView ic_right = view.findViewById(R.id.setDungeonWall_right_ic);
        TextView before_DP_text = view.findViewById(R.id.setDungeonWall_before_DP);
        TextView after_DP_text = view.findViewById(R.id.setDungeonWall_after_DP);

        setImage.setImageViewBitmapFromAsset(ic_DP, "base_menu/dungeonpower.png");
        setImage.setImageViewBitmapFromAsset(ic_right, "dungeon/DP_change.png");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()).setView(view);
        builder.setPositiveButton(getString(R.string.DialogPositiveText), new onClickListener(0));
        builder.setNegativeButton(getString(R.string.DialogNegativeText), new onClickListener(1));
        builder.setNeutralButton(getString(R.string.DialogNeutralText), null);
        return builder.create();
    }

    private class onClickListener implements DialogInterface.OnClickListener {
        final int Id;

        public onClickListener(int Id) {
            this.Id = Id;
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onClick(DialogInterface dialogInterface, int n) {
            switch (Id) {
                case 0:
                    callback.accept(true);
                    break;
                case 1:
                    callback.accept(false);
                    break;
            }
        }
    }
}
