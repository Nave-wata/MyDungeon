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

public class ConfirmDPCostDialog extends DialogFragment {
    final String text;
    final Consumer<Integer> callback;
    public final static int POSITIVE_BUTTON = 0;
    public final static int NEUTRAL_BUTTON = 1;

    public ConfirmDPCostDialog(String text, Consumer<Integer> callback) {
        this.text = text;
        this.callback = callback;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = requireActivity().getLayoutInflater().inflate(R.layout.dialog_confirmdpcost, null);
        AssetManager assetManager = Objects.requireNonNull(getActivity()).getAssets();
        SetImage setImage = new SetImage(assetManager);

        ImageView ic_DP = view.findViewById(R.id.ConfirmDPCost_ic_DP);
        ImageView ic_right = view.findViewById(R.id.ConfirmDPCost_right_ic);
        TextView title = view.findViewById(R.id.ConfirmDPCostTitle);
        TextView before_DP_text = view.findViewById(R.id.ConfirmDPCost_before_DP);
        TextView after_DP_text = view.findViewById(R.id.ConfirmDPCost_after_DP);

        switch (text) {
            case "deleteWall":
                title.setText(getString(R.string.deleteWall));
                break;
            case "ConfirmCreateFloor":
                title.setText(getString(R.string.ConfirmCreateFloor));
                break;
            case "deleteDungeonWall":
                title.setText(getString(R.string.deleteDungeonWall));
                break;
            case "deleteDungeonTrap1":
                title.setText(getString(R.string.deleteDungeonTrap1));
                break;
            case "deleteDungeonDoor":
                title.setText(getString(R.string.deleteDungeonDoor));
        }

        setImage.setImageViewBitmapFromAsset(ic_DP, "base_menu/dungeonpower.png");
        setImage.setImageViewBitmapFromAsset(ic_right, "dungeon/DP_change.png");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()).setView(view);
        builder.setPositiveButton(getString(R.string.DialogPositiveText), new onClickListener(POSITIVE_BUTTON));
        builder.setNeutralButton(getString(R.string.DialogNegativeText), new onClickListener(NEUTRAL_BUTTON));
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
                case NEUTRAL_BUTTON:
                    callback.accept(NEUTRAL_BUTTON);
                    break;
            }
        }
    }
}
