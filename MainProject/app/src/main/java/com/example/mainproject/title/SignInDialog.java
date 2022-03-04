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
import com.example.mainproject.asynchronous.usersinfo.GetUsersInfo;
import com.example.mainproject.asynchronous.usersinfo.UsersInfo;

import java.util.Objects;
import java.util.function.Consumer;


public class SignInDialog extends DialogFragment {
    final Consumer<Integer> callback;
    final String NEXT_INFO = "NextInfo";
    final String DS_Flag = "Flag";
    final String DS_Name = "Name";
    final String DS_Passwd = "Password";
    private SharedPreferences.Editor editor;
    private boolean flagLook = true;
    private SetImage setImage;
    private ImageButton LookUnLook;

    public SignInDialog(Consumer<Integer> callback) {
        this.callback = callback;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = requireActivity().getLayoutInflater().inflate(R.layout.dialog_signin, null);
        AssetManager assetManager = Objects.requireNonNull(getActivity()).getAssets();
        setImage = new SetImage(assetManager);
        SharedPreferences dataStore = getActivity().getSharedPreferences(NEXT_INFO, MODE_PRIVATE);
        editor = dataStore.edit();

        EditText etName = view.findViewById(R.id.TitleSIUserName);
        EditText etPass = view.findViewById(R.id.TitleSIPassword);
        Button btn = view.findViewById(R.id.SignIn_button);
        LookUnLook = view.findViewById(R.id.SILook_unLook_button);
        CheckBox nextAutoIn = view.findViewById(R.id.SInextAutoIn);

        setImage.setImageViewBitmapFromAsset(LookUnLook, "title/unlook.png");

        if (dataStore.getBoolean(DS_Flag, false)) {
            nextAutoIn.setChecked(true);
            etName.setText(dataStore.getString(DS_Name, null));
            etPass.setText(dataStore.getString(DS_Passwd, null));
        } else {
            nextAutoIn.setChecked(false);
        }

        onClickListener clickListener = new onClickListener(etName, etPass, nextAutoIn);
        btn.setOnClickListener(clickListener);
        LookUnLook.setOnClickListener(clickListener);
        nextAutoIn.setOnClickListener(clickListener);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()).setView(view);
        return builder.create();
    }


    private class onClickListener implements View.OnClickListener {
        EditText etName;
        EditText etPass;
        CheckBox nextAutoIn;

        public onClickListener(EditText etName, EditText etPass, CheckBox nextAutoIn) {
            this.etName = etName;
            this.etPass = etPass;
            this.nextAutoIn = nextAutoIn;
        }

        @SuppressLint("NonConstantResourceId")
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onClick(@NonNull View v) {
            switch (v.getId()) {
                case R.id.SignIn_button:
                    final String regex = "[0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ]";
                    final String name = etName.getText().toString();
                    final String password = etPass.getText().toString();
                    final String[] nameSplit = name.split("");
                    final String[] passwordSplit = password.split("");
                    boolean flag = true;
                    boolean nameFlag = true;
                    boolean passwordFlag = true;

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
                    if (nameFlag) {
                        for (String s : nameSplit) {
                            if (!s.matches(regex)) {
                                etName.setError(getString(R.string.errorNotInText));
                                flag = false;
                                break;
                            }
                        }
                    }
                    if (passwordFlag) {
                        for (String s : passwordSplit) {
                            if (!s.matches(regex)) {
                                etPass.setError(getString(R.string.errorNotInText));
                                flag = false;
                                break;
                            }
                        }
                    }

                    if (flag) {
                        final AppDatabase db = AppDatabaseSingleton.getInstance(Objects.requireNonNull(getActivity()).getApplicationContext());
                        new GetUsersInfo(
                                db,
                                name,
                                b-> {
                                    String salt = null;
                                    String hash = null;
                                    Context context = getActivity().getApplicationContext();

                                    try {
                                        for (UsersInfo ui : b) {
                                            salt = ui.getSalt();
                                            hash = ui.getHash();
                                        }
                                        String result = TitleActivity.HASH(name, password, salt);
                                        if (Objects.requireNonNull(hash).equals(result)) {
                                            TitleActivity.UserName = name;
                                            callback.accept(0);
                                            Toast.makeText(context, "ログイン成功", Toast.LENGTH_LONG).show();

                                            if (nextAutoIn.isChecked()) {
                                                editor.putBoolean(DS_Flag, true);
                                                editor.putString(DS_Name, name);
                                                editor.putString(DS_Passwd, password);
                                            } else {
                                                editor.putBoolean("Flag", false);
                                            }
                                            editor.apply();
                                        } else {
                                            Toast.makeText(context, "パスワードが間違っています", Toast.LENGTH_LONG).show();
                                        }
                                    } catch (Exception e) {
                                        Toast.makeText(context, "ユーザー名が登録されていません", Toast.LENGTH_LONG).show();
                                    }
                                },
                                e-> {
                                    Context context = getActivity().getApplicationContext();
                                    Toast.makeText(context, "ログインできませんでした", Toast.LENGTH_LONG).show();
                                }
                        ).execute();
                        dismiss();
                    }
                    break;
                case R.id.SILook_unLook_button:
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
                default:
                    break;
            }
        }
    }
}
