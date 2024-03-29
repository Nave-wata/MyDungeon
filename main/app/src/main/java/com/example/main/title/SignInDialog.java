package com.example.main.title;

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
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

import com.example.main.R;
import com.example.main.SetImage;
import com.example.main.asynchronous.AppDatabase;
import com.example.main.asynchronous.AppDatabaseSingleton;
import com.example.main.asynchronous.usersinfo.GetLine;
import com.example.main.asynchronous.usersinfo.UsersInfo;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

    @Nullable
    public static String getHash(String password, String salt) {
        MessageDigest sha512;
        try {
            sha512 = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }

        byte[] sha512_result = sha512.digest(password.getBytes());
        byte[] Salt = salt.getBytes();
        for (int i = 0; i < 10000; i++) {
            if (i % password.length() == 0) {
                byte[] tmp = new byte[sha512_result.length + Salt.length];
                System.arraycopy(sha512_result, 0, tmp, 0, sha512_result.length);
                System.arraycopy(Salt, 0, tmp, sha512_result.length, Salt.length);
                sha512_result = sha512.digest(tmp);
            } else {
                sha512_result = sha512.digest(sha512_result);
            }
        }
        return String.format("%04x", new BigInteger(1, sha512_result));
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
                    final String regex = "[0123456789abcdefghijklmnopqrstuvwxzABCDEFGHIJKLMNOPQRSTUVWXYZ]";
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
                        new GetLine(
                                db,
                                name,
                                password,
                                b-> {
                                    String salt = null;
                                    String hash = null;
                                    Context context = getActivity().getApplicationContext();

                                    try {
                                        for (UsersInfo ui : b) {
                                            salt = ui.getSalt();
                                            hash = ui.getHash();
                                        }
                                        String result = getHash(password, salt);
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
