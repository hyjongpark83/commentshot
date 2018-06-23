package com.example.hyjon.commentshot.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.hyjon.commentshot.contract.DBContract;
import com.example.hyjon.commentshot.db.DailyLogDBHelper;
import com.example.hyjon.commentshot.model.DailyLogModel;

import java.util.ArrayList;
import java.util.List;

public class DailyLogRepository {

    private DailyLogDBHelper mDailyLogDBHelper;

    public DailyLogRepository(Context context) {
        mDailyLogDBHelper = new DailyLogDBHelper(context);
    }

    public long insert(DailyLogModel dailyLogModel) {
        SQLiteDatabase db = mDailyLogDBHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DBContract.COLUMN_PICTURE, dailyLogModel.getPictureFilePath());
        values.put(DBContract.COLUMN_LOG, dailyLogModel.getLog());
        values.put(DBContract.COLUMN_IMPORTANCE, dailyLogModel.getImportance());

        return db.insert(DBContract.TABLE_NAME, null, values);
    }

    public List<DailyLogModel> selectAll() {
        SQLiteDatabase db = mDailyLogDBHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(DBContract.SQL_SELECT, null);

        cursor.moveToFirst();
        List<DailyLogModel> modelList = new ArrayList<>();

        while (!cursor.isAfterLast()) {
            DailyLogModel dailyLogModel =
                    new DailyLogModel(
                            cursor.getInt(cursor.getColumnIndex(DBContract.COLUMN_ID)),
                            cursor.getString(cursor.getColumnIndex(DBContract.COLUMN_PICTURE)),
                            cursor.getString(cursor.getColumnIndex(DBContract.COLUMN_LOG)),
                            cursor.getInt(cursor.getColumnIndex(DBContract.COLUMN_IMPORTANCE)));
            modelList.add(dailyLogModel);
            cursor.moveToNext();
        }
        cursor.close();
        return modelList;
    }

    public void update(DailyLogModel dailyLogModel) {
        SQLiteDatabase db = mDailyLogDBHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DBContract.COLUMN_PICTURE, dailyLogModel.getPictureFilePath());
        values.put(DBContract.COLUMN_LOG, dailyLogModel.getLog());
        values.put(DBContract.COLUMN_IMPORTANCE, dailyLogModel.getImportance());

        db.update(DBContract.TABLE_NAME, values, "_id=?", new String[]{String.valueOf(dailyLogModel.getIndex())});
    }

    public void delete(int index) {
        SQLiteDatabase db = mDailyLogDBHelper.getWritableDatabase();
        db.delete(DBContract.TABLE_NAME, "_id=?", new String[]{String.valueOf(index)});
    }
}
