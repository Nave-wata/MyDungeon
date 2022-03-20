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
    final String text;
    final Consumer<Integer> callback;
    public final static int POSITIVE_BUTTON = 0;
    public final static int NEGATIVE_BUTTON = 1;

    public SetDungeonPeaceDialog(String text, Consumer<Integer> callback) {
        this.text = text;
        this.callback = callback;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = requireActivity().getLayoutInflater().inflate(R.layout.dialog_setdungeonpeace, null);
        AssetManager assetManager = Objects.requireNonNull(getActivity()).getAssets();
        SetImage setImage = new SetImage(assetManager);

        ImageView ic_DP = view.findViewById(R.id.setDungeonPeace_ic_DP);
        ImageView ic_right = view.findViewById(R.id.setDungeonPeace_right_ic);
        TextView title = view.findViewById(R.id.setDungeonPeaceTitle);
        TextView before_DP_text = view.findViewById(R.id.setDungeonPeace_before_DP);
        TextView after_DP_text = view.findViewById(R.id.setDungeonPeace_after_DP);

        switch (text) {
            case "setDungeonWall":
                title.setText(getString(R.string.setDungeonWall));
                break;
            case "setDungeonTrap1":
                title.setText(getString(R.string.setDungeonTrap1));
                break;
        }

        setImage.setImageViewBitmapFromAsset(ic_DP, "base_menu/dungeonpower.png");
        setImage.setImageViewBitmapFromAsset(ic_right, "dungeon/DP_change.png");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()).setView(view);
        builder.setPositiveButton(getString(R.string.DialogPositiveText), new onClickListener(POSITIVE_BUTTON));
        builder.setNegativeButton(getString(R.string.DialogNegativeText), new onClickListener(NEGATIVE_BUTTON));
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
                case POSITIVE_BUTTON:
                    callback.accept(POSITIVE_BUTTON);
                    break;
                case NEGATIVE_BUTTON:
                    callback.accept(NEGATIVE_BUTTON);
                    break;
            }
        }
    }
}
