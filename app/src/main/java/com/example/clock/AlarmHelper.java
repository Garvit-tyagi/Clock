package com.example.clock;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class AlarmHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Alarms.db";
    private static final String TABLE_NAME = "alarms";
    private static final String ALARM_ID = "id";
    private static final String ALARM_STATUS = "status";
    private static final String ALARM_TIME = "time";

    public AlarmHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_ALARMS = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" + ALARM_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " + ALARM_TIME + " TEXT, " + ALARM_STATUS + " TEXT)";
        db.execSQL(CREATE_TABLE_ALARMS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addAlarm(String time, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ALARM_TIME, time);
        values.put(ALARM_STATUS, status);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public Cursor viewAlarm() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    public void deleteAlarm(alarm_item alarmEntry) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, ALARM_ID + " =?", new String[]{String.valueOf(alarmEntry.getId())});
        db.close();
    }

    @SuppressLint("Recycle")
    public int getCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null).getCount();
    }

    public String getAlarmStatus(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + ALARM_ID + " = " + id + "", null);
        cursor.moveToFirst();
        return cursor.getString(2);
    }

    public void updateAlarmStatus(String id,String s) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ALARM_STATUS, s);
        db.update(TABLE_NAME, values, ALARM_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    public String getAlarmTime(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + ALARM_ID + " = " + id + "", null);
        cursor.moveToFirst();
        return cursor.getString(1);
    }

    public void updateAlarmTime(String id,String s){
           SQLiteDatabase db=this.getWritableDatabase();
           ContentValues cv=new ContentValues();
           cv.put(ALARM_TIME,s);
           db.update(TABLE_NAME,cv,ALARM_ID + "=?",new String[]{String.valueOf(id)});
           db.close();
    }
}
