package com.example.banking_app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Tansactions_Database extends SQLiteOpenHelper {
    private static final String TABLE_NAME = "TRANSACTIONS";
    private static final String COL2 = "TO_USER";
    private static final String COL3 = "FROM_USER";
    private static final String COL4 = "AMOUNT";
    private static final String COL5 = "DATETIME";


    public Tansactions_Database(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + "(Sl INTEGER PRIMARY KEY AUTOINCREMENT, " + COL2 + " TEXT, " + COL3  +" TEXT, " + COL4 + " INTEGER, " +COL5+" TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP IF TABLE EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(String To,String From,Integer Amount,String Date_Time) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, To);
        contentValues.put(COL3, From);
        contentValues.put(COL4, Amount);
        contentValues.put(COL5,Date_Time);

        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }
    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }
}
