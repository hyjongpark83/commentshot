package com.example.hyjon.commentshot.contract;

public class DBContract {

    public static final int INDEX_COLUMN_NUM = 0;
    public static final int BLOB_COLUMN_NUM = 1;
    public static final int TEXT_COLUMN_NUM = 2;
    public static final int IMPORTANCE_COLUMN_NUM = 3;

    public static final String TABLE_NAME = "daily_log_table";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_PICTURE = "picture";
    public static final String COLUMN_LOG = "log";
    public static final String COLUMN_IMPORTANCE = "importance";

    public static final String SQL_CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                    " (" +
                        COLUMN_ID +        " INTEGER PRIMARY KEY AUTOINCREMENT" + ", " +
                        COLUMN_PICTURE +         " TEXT"                + ", " +
                        COLUMN_LOG +         " TEXT"                + ", " +
                        COLUMN_IMPORTANCE +   " INTEGER"             +
                    " )";

    public static final String SQL_DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public static final String SQL_SELECT = "SELECT * FROM " + TABLE_NAME;

    public static final String SQL_INSERT = "INSERT OR REPLACE INTO " + TABLE_NAME + " " +
            "(" + COLUMN_PICTURE + ", " + COLUMN_LOG + ", " + COLUMN_IMPORTANCE + ") VALUES ";

    public static final String SQL_DELETE = "DELETE FROM " + TABLE_NAME;

    private DBContract() {}
}
