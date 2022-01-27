package com.example.db_test2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import static com.example.db_test2.DBContract.DBEntry;

public class MainActivity extends AppCompatActivity {

    private SampleDatabaseHelper helper = null;

    private TextView viewTitle    = null;
    private TextView viewContents = null;
    private EditText editTitle    = null;
    private EditText editContents = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ビューオブジェクトを取得
        viewTitle    = findViewById(R.id.textView_title);
        viewContents = findViewById(R.id.textView_contents);
        editTitle    = findViewById(R.id.editTitle);
        editContents = findViewById(R.id.editContents);

        // ヘルパーを準備
        helper = new SampleDatabaseHelper(this);

        // データを表示
        onShow();
    }

    // データを表示する
    protected void onShow() {

        // データベースから取得する項目を設定
        String[] cols = {DBEntry.COLUMN_NAME_TITLE, DBEntry.COLUMN_NAME_CONTENTS};

        // 読み込みモードでデータベースをオープン
        try (SQLiteDatabase db = helper.getReadableDatabase()) {

            // データを取得するSQLを実行
            // 取得したデータがCursorオブジェクトに格納される
            Cursor cursor = db.query(DBEntry.TABLE_NAME, cols, null,
                    null, null, null, null, null);

            // moveToFirstで、カーソルを検索結果セットの先頭行に移動
            // 検索結果が0件の場合、falseが返る
            if (cursor.moveToFirst()){

                // 表示用のテキスト・コンテンツに検索結果を設定
                viewTitle.setText(cursor.getString(0));
                viewContents.setText(cursor.getString(1));

                // 入力用のテキスト・コンテンツにも検索結果を設定
                editTitle.setText(cursor.getString(0));
                editContents.setText(cursor.getString(1));

            } else {
                // 検索結果が0件の場合のメッセージを設定
                viewTitle.setText("データがありません");
                viewContents.setText("");

                editTitle.setText("");
                editContents.setText("");
            }
        }

    }

    // 保存処理
    public void onSave(View view) {

        // 入力欄に入力されたタイトルとコンテンツを取得
        String title    = editTitle.getText().toString();
        String contents = editContents.getText().toString();

        // 書き込みモードでデータベースをオープン
        try (SQLiteDatabase db = helper.getWritableDatabase()) {

            // 入力されたタイトルとコンテンツをContentValuesに設定
            // ContentValuesは、項目名と値をセットで保存できるオブジェクト
            ContentValues cv = new ContentValues();
            cv.put(DBEntry.COLUMN_NAME_TITLE, title);
            cv.put(DBEntry.COLUMN_NAME_CONTENTS, contents);

            // 現在テーブルに登録されているデータの_IDを取得
            Cursor cursor = db.query(DBEntry.TABLE_NAME,  new String[] {DBEntry._ID}, null, null,
                    null, null, null, null);

            // テーブルにデータが登録されていれば更新処理
            if (cursor.moveToFirst()){

                // 取得した_IDをparamsに設定
                String[] params = {cursor.getString(0)};

                // _IDのデータを更新
                db.update(DBEntry.TABLE_NAME, cv, DBEntry._ID + " = ?", params);

            } else {

                // データがなければ新規登録
                db.insert(DBEntry.TABLE_NAME, null, cv);
            }
        }

        // データを表示
        onShow();

    }

    // 削除処理
    public void onDelete(View view){

        try (SQLiteDatabase db = helper.getWritableDatabase()) {
            db.delete(DBEntry.TABLE_NAME, null, null);
        }

        // データを表示
        onShow();
    }

}