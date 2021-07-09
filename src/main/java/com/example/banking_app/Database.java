package com.example.banking_app;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Database extends SQLiteOpenHelper {

    private static final String TAG = "Database";

    private static final String TABLE_NAME = "Customers";
    private static final String COL1="Sl";
    private static final String COL2 = "ID";
    private static final String COL3 = "Name";
    private static final String COL4 = "Current_Balance";


    public Database(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + "(Sl INTEGER PRIMARY KEY AUTOINCREMENT, " + COL2 + " TEXT, " + COL3  +" TEXT, " + COL4 + " INTEGER)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP IF TABLE EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(String Name,String mail,Integer balance) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, mail);
        contentValues.put(COL3, Name);
        contentValues.put(COL4, balance);

        Log.d(TAG, "addData: Adding " + mail + " to " + TABLE_NAME);

        long result = db.insert(TABLE_NAME, null, contentValues);

        //if date as inserted incorrectly it will return -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Returns all the data from database
     * @return
     */
    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    /**
     * Returns only the ID that matches the name passed in
     * @param mail
     * @return
     */
    public Cursor getBalance(String mail){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL4 + " FROM " + TABLE_NAME +
                " WHERE " + COL2 + " = '" + mail + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getName(String mail){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL3 + " FROM " + TABLE_NAME +
                " WHERE " + COL2 + " = '" + mail + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    /**
     * Updates the name field
     * @param newBalance
     * @param id
     * @param oldBalance
     */
    public void updateBalance(Integer newBalance, String id, Integer oldBalance){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COL4 +
                " = " + newBalance + " WHERE " + COL2 + " = " +"'"+ id +
                "' AND " + COL4 + " = " + oldBalance;
        Log.d(TAG, "updateName: query: " + query);
        Log.d(TAG, "updateName: Setting name to " + newBalance);
        db.execSQL(query);
    }
}
