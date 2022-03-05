package com.example.mainproject;

import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class ShowDiffTimeDialog extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = requireActivity().getLayoutInflater().inflate(R.layout.dialog_showdifftime, null);
        AssetManager assetManager = getActivity().getAssets();
        SetImage setImage = new SetImage(assetManager);

        ImageView ic_DP = view.findViewById(R.id.dialog_ic_DP);
        ImageView ic_money = view.findViewById(R.id.dialog_ic_MONEY);
        ImageView up_DP = view.findViewById(R.id.up_DP);
        ImageView up_MONEY = view.findViewById(R.id.up_MONEY);

        setImage.setImageViewBitmapFromAsset(ic_DP, "base_menu/dungeonpower.png");
        setImage.setImageViewBitmapFromAsset(ic_money, "base_menu/money.png");
        setImage.setImageViewBitmapFromAsset(up_DP, "base_menu/up_img.png");
        setImage.setImageViewBitmapFromAsset(up_MONEY, "base_menu/up_img.png");

        ObjectAnimator DP_objectAnimator = ObjectAnimator.ofFloat(up_DP, "translationY", 20f, -20f);
        DP_objectAnimator.setDuration(1000);
        DP_objectAnimator.setRepeatCount(ObjectAnimator.INFINITE);
        DP_objectAnimator.start();
        ObjectAnimator MONEY_objectAnimator = ObjectAnimator.ofFloat(up_MONEY, "translationY", 20f, -20f);
        MONEY_objectAnimator.setDuration(1000);
        MONEY_objectAnimator.setRepeatCount(ObjectAnimator.INFINITE);
        MONEY_objectAnimator.start();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()).setView(view);
        builder.setPositiveButton(R.string.DialogPositiveText, null);
        builder.setNeutralButton(R.string.DialogNegativeText, null);

        return builder.create();
    }
}
