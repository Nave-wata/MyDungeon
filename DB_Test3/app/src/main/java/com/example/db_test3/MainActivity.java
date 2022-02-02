package com.example.db_test3;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.db_test3.db.AppDatabase;
import com.example.db_test3.db.AppDatabaseSingleton;
import com.example.db_test3.db.texts.Texts;
import com.example.db_test3.db.texts.TextsDao;
import com.example.db_test3.db.usersinfo.UsersInfoDao;

import java.lang.ref.WeakReference;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView textOutName;
    private TextView textOutYear;
    private TextView textOutMonth;
    private TextView textOutDay;

    private EditText editName;
    private EditText editYear;
    private EditText editMonth;
    private EditText editDay;

    private Button saveButton;
    private Button deleteButton;
    private AppDatabase db;

    private Spinner users;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textOutName = findViewById(R.id.textOutName);
        textOutYear = findViewById(R.id.textOutYear);
        textOutMonth = findViewById(R.id.textOutMonth);
        textOutDay = findViewById(R.id.textOutDay);

        editName = findViewById(R.id.editName);
        editYear = findViewById(R.id.editYear);
        editMonth = findViewById(R.id.editMonth);
        editDay = findViewById(R.id.editDay);

        editName.setNextFocusDownId(R.id.editYear);
        editYear.setNextFocusDownId(R.id.editMonth);
        editMonth.setNextFocusDownId(R.id.editDay);

        saveButton = findViewById(R.id.saveButton);
        deleteButton = findViewById(R.id.deleteButton);

        saveButton.setOnClickListener(this);
        deleteButton.setOnClickListener(this);

        db = AppDatabaseSingleton.getInstance(getApplicationContext());

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        adapter.add("A");
        adapter.add("B");

        users = findViewById(R.id.users);
        users.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        if (view != null) {
            switch (view.getId()) {
                case R.id.saveButton:
                    onClickSaveButton();
                    break;

                case R.id.deleteButton:
                    break;

                default:
                    break;
            }
        }
    }

    public void onClickSaveButton (){
        boolean[] flags = {true, true, true, true};

        flags[0] = editTextError(editName, 1);
        flags[1] = editTextError(editYear, 2);
        flags[2] = editTextError(editMonth, 2);
        flags[3] = editTextError(editDay, 2);

        for (boolean flag : flags) {
            if (flag) {
            } else {
                textOutName.setText("NO");
                return;
            }
        }
        new DataStoreAsyncTask(db, this, textOutName, editName, editYear, editMonth, editDay).execute();
        textOutName.setText("Yes!");
    }

    public boolean editTextError(EditText editText, int error) {
        String regex = "(1|2|3|4|5|6|7|8|9|0)";
        String splitText = "";
        String text = editText.getText().toString();
        String[] str = text.split(splitText);

        if (text.length() == 0) {
            editText.setError(getString(R.string.errorNotInput));
            return false;
        }
        if (text.trim().isEmpty()) {
            editText.setError(getString(R.string.errorSpaceOnly));
            return  false;
        }
        if (str[0].trim().isEmpty() || str[str.length - 1].trim().isEmpty()) {
            editText.setError(getString(R.string.errorSpaceHeadLast));
            return false;
        }
        if (text.replaceFirst("^[\\h]+", "").replaceFirst("[\\h]+$", "").isEmpty()) {
            editText.setError(getString(R.string.errorSpaceOnly));
            return false;
        }
        if (str[0].replaceFirst("^[\\h]+", "").replaceFirst("[\\h]+$", "").isEmpty() ||
                str[str.length - 1].replaceFirst("^[\\h]+", "").replaceFirst("[\\h]+$", "").isEmpty()) {
            editText.setError(getString(R.string.errorSpaceHeadLast));
            return false;
        }

        if (error == 1) {
            for (String s : str) {
                if (!s.matches(regex)) {
                    return true;
                }
            }
            editText.setError(getString(R.string.errorNotString));
            return false;
        } else if (error == 2) {
            for (String s : str) {
                if (!s.matches(regex)) {
                    editText.setError(getString(R.string.errorNotNum));
                    return false;
                }
            }
        }
        return true;
    }
}


class DataStoreAsyncTask extends AsyncTask<Void, Void, Integer> {
    private WeakReference<Activity> weakActivity;
    private AppDatabase db;
    private TextView textOutName;
    private StringBuilder sb;
    private EditText editName;
    private EditText editYear;
    private EditText editMonth;
    private EditText editDay;

    public DataStoreAsyncTask(AppDatabase db, Activity activity, TextView textOutName, EditText editName, EditText editYear, EditText editMonth, EditText editDay) {
        this.db = db;
        weakActivity = new WeakReference<>(activity);
        this.textOutName = textOutName;
        this.editName = editName;
        this.editYear = editYear;
        this.editMonth = editMonth;
        this.editDay = editDay;
    }

    @Override
    protected Integer doInBackground(Void... params) {
        TextsDao textsDao = db.textsDao();
        UsersInfoDao usersInfoDao = db.usersInfoDao();
        sb = new StringBuilder();

        String name = editName.getText().toString();
        int year = Integer.parseInt(editYear.getText().toString());
        int month = Integer.parseInt(editMonth.getText().toString());


        List<Texts> Text = textsDao.getAll();

        //for (Texts ts: Text) {
        //    sb.append(ts.getText()).append("\n");
        //}

        return 0;
    }

    @Override
    protected void onPostExecute(Integer code) {
        Activity activity = weakActivity.get();
        if(activity == null) {
            return;
        }
    }
}