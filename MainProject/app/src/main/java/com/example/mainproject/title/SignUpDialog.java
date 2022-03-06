package com.example.mainproject.title;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

import com.example.mainproject.R;
import com.example.mainproject.SetImage;
import com.example.mainproject.asynchronous.AppDatabase;
import com.example.mainproject.asynchronous.AppDatabaseSingleton;
import com.example.mainproject.asynchronous.usersinfo.SignUp;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.function.Consumer;

public class SignUpDialog extends DialogFragment {

    private final Consumer<Integer> callback;
    final String NEXT_INFO = "NextInfo";
    final String DS_Flag = "Flag";
    final String DS_Name = "Name";
    final String DS_Passwd = "Password";
    private SharedPreferences.Editor editor;
    private boolean flagLook = true;
    private boolean flagLook2 = true;
    private SetImage setImage;
    private ImageButton LookUnLook;
    private ImageButton LookUnLook2;

    public SignUpDialog(Consumer<Integer> callback) {
        this.callback = callback;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = requireActivity().getLayoutInflater().inflate(R.layout.dialog_signup, null);
        AssetManager assetManager = Objects.requireNonNull(getActivity()).getAssets();
        setImage = new SetImage(assetManager);
        SharedPreferences dataStore = getActivity().getSharedPreferences(NEXT_INFO, MODE_PRIVATE);
        editor = dataStore.edit();

        EditText etName = view.findViewById(R.id.TitleSUUserName);
        EditText etPass = view.findViewById(R.id.TitleSUPassword);
        EditText etPass2 = view.findViewById(R.id.TitleSUPassword2);
        CheckBox nextAutoIn = view.findViewById(R.id.SUnextAutoIn);
        Button btn = view.findViewById(R.id.SignUp_button);
        LookUnLook = view.findViewById(R.id.SULook_unLook_button);
        LookUnLook2 = view.findViewById(R.id.SULook_unLook_button2);

        setImage.setImageViewBitmapFromAsset(LookUnLook, "title/unlook.png");
        setImage.setImageViewBitmapFromAsset(LookUnLook2, "title/unlook.png");

        nextAutoIn.setChecked(false);

        onClickListener clickListener = new onClickListener(etName, etPass, etPass2, nextAutoIn);
        btn.setOnClickListener(clickListener);
        LookUnLook.setOnClickListener(clickListener);
        LookUnLook2.setOnClickListener(clickListener);
        nextAutoIn.setOnClickListener(clickListener);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()).setView(view);
        return builder.create();
    }


    private class onClickListener implements View.OnClickListener {
        EditText etName;
        EditText etPass;
        EditText etPass2;
        CheckBox nextAutoIn;

        public onClickListener(EditText etName, EditText etPass, EditText etPass2, CheckBox nextAutoIn) {
            this.etName = etName;
            this.etPass = etPass;
            this.etPass2 = etPass2;
            this.nextAutoIn = nextAutoIn;
        }

        @SuppressLint("NonConstantResourceId")
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onClick(@NonNull View v) {
            switch (v.getId()) {
                case R.id.SignUp_button:
                    final String regexName = "[0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_]";
                    final String regexPass = "[0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ]";
                    final String name = etName.getText().toString();
                    final String password = etPass.getText().toString();
                    final String password2 = etPass2.getText().toString();
                    final String[] nameSplit = name.split("");
                    final String[] passwordSplit = password.split("");
                    final String[] passwordSplit2 = password2.split("");
                    boolean flag = true;
                    boolean nameFlag = true;
                    boolean passwordFlag = true;
                    boolean passwordFlag2 = true;

                    if (name.length() == 0) {
                        etName.setError(getString(R.string.errorNotInput));
                        flag = false;
                        nameFlag = false;
                    }
                    if (password.length() == 0) {
                        etPass.setError(getString(R.string.errorNotInput));
                        flag = false;
                        passwordFlag = false;
                    }
                    if (password2.length() == 0) {
                        etPass2.setError(getString(R.string.errorNotInput));
                        flag = false;
                        passwordFlag2 = false;
                    }
                    if (nameFlag) {
                        for (String s : nameSplit) {
                            if (!s.matches(regexName)) {
                                etName.setError(getString(R.string.errorNotInText));
                                flag = false;
                                break;
                            }
                        }
                    }
                    if (passwordFlag) {
                        for (String s : passwordSplit) {
                            if (!s.matches(regexPass)) {
                                etPass.setError(getString(R.string.errorNotInText));
                                flag = false;
                                break;
                            }
                        }
                    }
                    if (passwordFlag2) {
                        for (String s : passwordSplit2) {
                            if (!s.matches(regexPass)) {
                                etPass2.setError(getString(R.string.errorNotInText));
                                flag = false;
                                passwordFlag2 = false;
                                break;
                            }
                        }
                    }
                    if (!password.equals(password2) && passwordFlag2) {
                        etPass2.setError(getString(R.string.NotMatch));
                        flag = false;
                    }

                    if (flag) {
                        final AppDatabase db = AppDatabaseSingleton.getInstance(Objects.requireNonNull(getActivity()).getApplicationContext());
                        final String salt = getRandomString();
                        final String hash = TitleActivity.HASH(name, password, salt);
                        final LocalDateTime nowTime = LocalDateTime.now();
                        final int nowYear = nowTime.getYear();
                        final int nowMonth = nowTime.getMonthValue();
                        final int nowDay = nowTime.getDayOfMonth();
                        final int nowHour = nowTime.getHour();
                        final int nowMinute = nowTime.getMinute();
                        final int nowSecond = nowTime.getSecond();
                        new SignUp(
                                db,
                                name,
                                salt,
                                hash,
                                nowYear,
                                nowMonth,
                                nowDay,
                                nowHour,
                                nowMinute,
                                nowSecond,
                                b -> {
                                    Context context = getActivity().getApplicationContext();
                                    Toast.makeText(context, "登録完了しました", Toast.LENGTH_SHORT).show();
                                    Log.v("name", name);
                                    Log.v("pass", password);
                                    Log.v("salt", salt);
                                    Log.v("hash", hash);
                                    TitleActivity.UserName = name;
                                    if (nextAutoIn.isChecked()) {
                                        editor.putBoolean(DS_Flag, true);
                                        editor.putString(DS_Name, name);
                                        editor.putString(DS_Passwd, password);
                                    } else {
                                        editor.putBoolean("Flag", false);
                                    }
                                    editor.apply();
                                    callback.accept(0);
                                },
                                sqlE -> {
                                    Context context = getActivity().getApplicationContext();
                                    Toast.makeText(context, "ユーザー名が重複しています", Toast.LENGTH_SHORT).show();
                                },
                                e -> {
                                    Context context = getActivity().getApplicationContext();
                                    Toast.makeText(context, "登録できませんでした", Toast.LENGTH_SHORT).show();
                                }
                        ).execute();
                        dismiss();
                    }
                    break;
                case R.id.SULook_unLook_button:
                    if (flagLook) {
                        etPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        setImage.setImageViewBitmapFromAsset(LookUnLook, "title/look.png");
                        flagLook = false;
                    } else {
                        etPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        setImage.setImageViewBitmapFromAsset(LookUnLook, "title/unlook.png");
                        flagLook = true;
                    }
                    break;
                case R.id.SULook_unLook_button2:
                    if (flagLook2) {
                        etPass2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        setImage.setImageViewBitmapFromAsset(LookUnLook2, "title/look.png");
                        flagLook2 = false;
                    } else {
                        etPass2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        setImage.setImageViewBitmapFromAsset(LookUnLook2, "title/unlook.png");
                        flagLook2 = true;
                    }
                default:
                    break;
            }
        }

        @NonNull
        String getRandomString() {
            String str = "0123456789abcdefghijklmnopqrstyvwxyzABCDEFGHIJKLMNOPQRSTYVWXYZ";
            StringBuilder builder = new StringBuilder();
            int randInt = (int) (Math.random() * 1000) + 24;

            for (int j = 0; j < randInt; j++) {
                int tmp = (int) (str.length() * Math.random());
                builder.append(str.charAt(tmp));
            }

            return builder.toString();
        }
    }
}
