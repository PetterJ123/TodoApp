package com.example.petri.todoapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by liemt4 on 2017-01-29.
 */

public class MyDb {

    private MyDatabaseHelper dbHelper;

    private SQLiteDatabase database;

    public final static String EMP_TABLE = "tasks"; // Table name

    public final static String EMP_ID = "_id"; // id value for employee
    public final static String EMP_TASK = "task";  // Message contents
    public final static String EMP_CHECK = "bool";  // Strikethrough bool

    /**
     * @param context
     */
    public MyDb(Context context) {
        dbHelper = new MyDatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
    }


    public long createRecords(String id, String task, int check) {
        ContentValues values = new ContentValues();
        values.put(EMP_ID, id);
        values.put(EMP_TASK, task);
        values.put(EMP_CHECK, Integer.toString(check));
        return database.insert(EMP_TABLE, null, values);
    }

    public int removeRecords(String id) {
        return database.delete(EMP_TABLE, EMP_ID+"="+id, null);
    }

    public int boolRecords(String id, int check) {
        ContentValues values = new ContentValues();
        values.put(EMP_CHECK, Integer.toString(check));
        return database.update(EMP_TABLE, values, EMP_ID+"="+id, null);
    }

    public Cursor selectRecords() {
        String[] cols = new String[]{EMP_ID, EMP_TASK, EMP_CHECK};
        Cursor mCursor = database.query(true, EMP_TABLE, cols, null
                , null, null, null, null, null);
        /*if (mCursor != null) {
            mCursor.moveToFirst();
        }*/
        return mCursor; // iterate to get each value.
    }
}
