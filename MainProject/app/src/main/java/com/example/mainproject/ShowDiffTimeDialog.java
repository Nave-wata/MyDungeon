package com.example.mainproject;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import java.util.Objects;

public class ShowDiffTimeDialog extends DialogFragment {
    private final long diffTime;

    public ShowDiffTimeDialog(long diffTime) {
        this.diffTime = diffTime;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = requireActivity().getLayoutInflater().inflate(R.layout.dialog_showdifftime, null);
        AssetManager assetManager = Objects.requireNonNull(getActivity()).getAssets();
        SetImage setImage = new SetImage(assetManager);
        long num1 = 1; // DP test用 秒数にかける数
        long num2 = 2; // MONEY test用 秒数にかける数
        byte[] Base_DP = {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        byte[] Base_MONEY = {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

        ImageView ic_DP = view.findViewById(R.id.dialog_ic_DP);
        ImageView ic_money = view.findViewById(R.id.dialog_ic_MONEY);
        ImageView up_DP = view.findViewById(R.id.up_DP);
        ImageView up_MONEY = view.findViewById(R.id.up_MONEY);
        TextView up_diffDP = view.findViewById(R.id.dialog_Have_DP);
        TextView up_diffMONEY = view.findViewById(R.id.dialog_Have_money);
        view.findViewById(R.id.dialog_ShowDiffTime_close).setOnClickListener(view1 -> dismiss());

        setImage.setImageViewBitmapFromAsset(ic_DP, "base_menu/dungeonpower.png");
        setImage.setImageViewBitmapFromAsset(ic_money, "base_menu/money.png");
        setImage.setImageViewBitmapFromAsset(up_DP, "base_menu/up_img.png");
        setImage.setImageViewBitmapFromAsset(up_MONEY, "base_menu/up_img.png");

        PropertyValuesHolder inFlatX = PropertyValuesHolder.ofFloat(View.SCALE_X, 0.5f, 1.5f);
        PropertyValuesHolder inFlatY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 0.5f, 1.5f);
        PropertyValuesHolder moveMode = PropertyValuesHolder.ofFloat("translationY", 20f, -1f);
        ObjectAnimator DP_objectAnimator = ObjectAnimator.ofPropertyValuesHolder(up_DP, inFlatX, inFlatY, moveMode);
        DP_objectAnimator.setDuration(1000);
        DP_objectAnimator.setRepeatCount(ObjectAnimator.INFINITE);
        DP_objectAnimator.start();
        ObjectAnimator MONEY_objectAnimator = ObjectAnimator.ofPropertyValuesHolder(up_MONEY, inFlatX, inFlatY, moveMode);
        MONEY_objectAnimator.setDuration(1000);
        MONEY_objectAnimator.setRepeatCount(ObjectAnimator.INFINITE);
        MONEY_objectAnimator.setRepeatMode(ObjectAnimator.RESTART);
        MONEY_objectAnimator.start();

        BaseStatusFragment bsf = new BaseStatusFragment();
        byte[] _DP = bsf.mul(Base_DP, num1 * diffTime);
        byte[] _MONEY = bsf.mul(Base_MONEY, num2 * diffTime);
        String DP_str = bsf.CastString(_DP);
        String MONEY_str = bsf.CastString(_MONEY);

        up_diffDP.setText(DP_str);
        up_diffMONEY.setText(MONEY_str);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()).setView(view);

        return builder.create();
    }
}
