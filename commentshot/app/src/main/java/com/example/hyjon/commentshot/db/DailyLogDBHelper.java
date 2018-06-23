package com.example.hyjon.commentshot.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.hyjon.commentshot.contract.DBContract;

public class DailyLogDBHelper extends SQLiteOpenHelper {
    //Reference : http://kuroikuma.tistory.com/75
    public static final int DB_VERSION = 1;
    public static final String DBFILE_DAILYLOG = "dailylog.db";

    public DailyLogDBHelper(Context context) {
        super(context, DBFILE_DAILYLOG, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DBContract.SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DBContract.SQL_DROP_TABLE);
        onCreate(db);
    }
}
