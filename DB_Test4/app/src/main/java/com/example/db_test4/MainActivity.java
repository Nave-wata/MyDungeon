package com.example.db_test4;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.db_test4.db.AppDatabase;
import com.example.db_test4.db.AppDatabaseSingleton;
import com.example.db_test4.db.usersinfo.AsyncTask_Save;
import com.example.db_test4.db.usersinfo.AsyncTask_deleteLine;
import com.example.db_test4.db.usersinfo.AsyncTask_getLine;
import com.example.db_test4.db.usersinfo.AsyncTask_setName;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textOutYear = findViewById(R.id.textOutYear);
        TextView textOutMonth = findViewById(R.id.textOutMonth);
        TextView textOutDay = findViewById(R.id.textOutDay);

        EditText editName = findViewById(R.id.editName);
        EditText editYear = findViewById(R.id.editYear);
        EditText editMonth = findViewById(R.id.editMonth);
        EditText editDay = findViewById(R.id.editDay);

        editName.setNextFocusDownId(R.id.editYear);
        editYear.setNextFocusDownId(R.id.editMonth);
        editMonth.setNextFocusDownId(R.id.editDay);

        Button saveButton = findViewById(R.id.saveButton);
        Button deleteButton = findViewById(R.id.deleteButton);

        AppDatabase db = AppDatabaseSingleton.getInstance(getApplicationContext());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner users = findViewById(R.id.textOutName);

        new AsyncTask_setName(db, this, adapter, users).execute();

        saveButton.setOnClickListener(new SaveButton(this, db, users, adapter, textOutYear, textOutMonth, textOutDay, editName, editYear, editMonth, editDay));
        deleteButton.setOnClickListener(new DeleteButton(this, db, users, adapter, textOutYear, textOutMonth, textOutDay));
        users.setOnItemSelectedListener(new UsersSpinner(this, db, users, adapter, textOutYear, textOutMonth, textOutDay));
    }

    private class SaveButton implements View.OnClickListener {
        private final Activity activity;
        private final AppDatabase db;
        private final Spinner users;
        private final ArrayAdapter<String> adapter;
        private final TextView textOutYear;
        private final TextView textOutMonth;
        private final TextView textOutDay;
        private final EditText editName;
        private final EditText editYear;
        private final EditText editMonth;
        private final EditText editDay;

        private SaveButton(Activity activity, AppDatabase db, Spinner users, ArrayAdapter<String> adapter, TextView textOutYear, TextView textOutMonth, TextView textOutDay, EditText editName, EditText editYear, EditText editMonth, EditText editDay) {
            this.activity = activity;
            this.db = db;
            this.users = users;
            this.adapter = adapter;
            this.textOutYear = textOutYear;
            this.textOutMonth = textOutMonth;
            this.textOutDay = textOutDay;
            this.editName = editName;
            this.editYear = editYear;
            this.editMonth = editMonth;
            this.editDay = editDay;
        }

        @SuppressWarnings("StatementWithEmptyBody")
        @Override
        public void onClick(View view) {
            boolean[] flags = {true, true, true, true};

            flags[0] = editTextError(editName, 1);
            flags[1] = editTextError(editYear, 2);
            flags[2] = editTextError(editMonth, 2);
            flags[3] = editTextError(editDay, 2);

            for (boolean flag : flags) {
                if (flag) {
                    //
                } else {
                    return;
                }
            }
            new AsyncTask_Save(db, activity, users, adapter, textOutYear, textOutMonth, textOutDay, editName, editYear, editMonth, editDay).execute();
        }

        public boolean editTextError(EditText editText, int error) {
            String regex = "([1234567890])";
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

    private static class DeleteButton implements View.OnClickListener {
        private final Activity activity;
        private final AppDatabase db;
        private final Spinner users;
        private final ArrayAdapter<String> adapter;
        private final TextView textOutYear;
        private final TextView textOutMonth;
        private final TextView textOutDay;

        private DeleteButton(Activity activity, AppDatabase db, Spinner users, ArrayAdapter<String> adapter, TextView textOutYear, TextView textOutMonth, TextView textOutDay) {
            this.activity = activity;
            this.db = db;
            this.users = users;
            this.adapter = adapter;
            this.textOutYear = textOutYear;
            this.textOutMonth = textOutMonth;
            this.textOutDay = textOutDay;
        }

        @Override
        public void onClick(View view) {
            String item = users.getSelectedItem().toString();
            if (adapter.getCount() > 0) {
                new AsyncTask_deleteLine(db, activity, adapter, users, textOutYear, textOutMonth, textOutDay, item).execute();
            }
        }
    }

    private static class UsersSpinner implements AdapterView.OnItemSelectedListener {
        private final Activity activity;
        private final AppDatabase db;
        private final Spinner users;
        private final ArrayAdapter<String> adapter;
        private final TextView textOutYear;
        private final TextView textOutMonth;
        private final TextView textOutDay;

        private UsersSpinner(Activity activity, AppDatabase db, Spinner users, ArrayAdapter<String> adapter, TextView textOutYear, TextView textOutMonth, TextView textOutDay) {
            this.activity = activity;
            this.db = db;
            this.users = users;
            this.adapter = adapter;
            this.textOutYear = textOutYear;
            this.textOutMonth = textOutMonth;
            this.textOutDay = textOutDay;
        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Spinner spinner = (Spinner)parent;
            String item = (String)spinner.getSelectedItem();
            new AsyncTask_getLine(db, activity, users, textOutYear, textOutMonth, textOutDay, item).execute();
        }

        public void onNothingSelected(AdapterView<?> parent) {
            //
        }
    }
}
