package com.example.db_test2;

import android.provider.BaseColumns;

public final class DBContract {
    // 誤ってインスタンス化しないようにコンストラクタをPrivate宣言
    private DBContract() {}

    // テーブルの内容を定義
    public static class DBEntry implements BaseColumns {
        // BaseColumns インターフェースを実装することで内部クラスは_IDを継承できる
        public static final String TABLE_NAME = "sample_table";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_CONTENTS = "contents";
        public static final String COLUMN_NAME_UPDATE = "update";
    }
}
