package com.example.fragmenttest2.title;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

import com.example.fragmenttest2.R;
import com.example.fragmenttest2.SetImage;
import com.example.fragmenttest2.asynchronous.AppDatabase;
import com.example.fragmenttest2.asynchronous.AppDatabaseSingleton;
import com.example.fragmenttest2.asynchronous.usersinfo.DataSave;
import com.example.fragmenttest2.asynchronous.usersinfo.GetLine;
import com.example.fragmenttest2.asynchronous.usersinfo.UsersInfo;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;


public class SignInDialog extends DialogFragment {
    boolean flagLook = true;
    public AssetManager assetManager;
    public SetImage setImage;
    ImageButton LookUnLook;

    @Override
    public Dialog onCreateDialog(@NonNull Bundle savedInstanceState) {
        View view = requireActivity().getLayoutInflater().inflate(R.layout.dialog_signin, null);

        EditText etName = view.findViewById(R.id.TitleSIUserName);
        EditText etPass = view.findViewById(R.id.TitleSIPassword);
        onClickListener clickListener = new onClickListener(etName, etPass);

        Button btn = view.findViewById(R.id.SignIn_button);
        LookUnLook = view.findViewById(R.id.SILook_unLook_button);
        setImage.setImageViewBitmapFromAsset(LookUnLook, "title/unlook.png");

        btn.setOnClickListener(clickListener);
        LookUnLook.setOnClickListener(clickListener);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()).setView(view);
        return builder.create();
    }


    private class onClickListener implements View.OnClickListener {
        EditText etName;
        EditText etPass;

        public onClickListener(EditText etName, EditText etPass) {
            this.etName = etName;
            this.etPass = etPass;
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.SignIn_button:
                    final String regex = "[0123456789abcdefghijklmnopqrstyvwxyzABCDEFGHIJKLMNOPQRSTYVWXYZ]";
                    final String name = etName.getText().toString();
                    final String password = etPass.getText().toString();
                    final String[] nameSplit = name.split("");
                    final String[] passwordSplit = password.split("");
                    boolean flag = true;
                    boolean nameFlag = true;
                    boolean passwordFlag = true;

                    if (name.length() == 0 && nameFlag) {
                        etName.setError(getString(R.string.errorNotInput));
                        flag = false;
                        nameFlag = false;
                    }
                    if (password.length() == 0 && passwordFlag) {
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
                        final AppDatabase db = AppDatabaseSingleton.getInstance(getActivity().getApplicationContext());
                        new GetLine(
                                db,
                                name,
                                password,
                                b-> {
                                    String salt = null;
                                    String hash = null;
                                    for (UsersInfo ui : b) {
                                        salt = ui.getSalt();
                                        hash = ui.getHash();
                                    }
                                    String result = getHash(password, salt);
                                    if (hash.equals(result)) {
                                        Context context = getActivity().getApplicationContext();
                                        Toast.makeText(context, "ログイン成功", Toast.LENGTH_LONG).show();
                                    } else {
                                        Context context = getActivity().getApplicationContext();
                                        Toast.makeText(context, "ログイン失敗", Toast.LENGTH_LONG).show();
                                    }
                                },
                                e->Log.v("Sign In", "NO")
                        ).execute();
                        dismiss();
                    }
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
        String hash = String.format("%04x", new BigInteger(1, sha512_result));
        return hash;
    }
}
