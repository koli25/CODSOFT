package com.example.alarmclockapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class AlarmDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "alarms.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_ALARMS = "alarms";
    private static final String COL_ID = "id";
    private static final String COL_TIME = "time";
    private static final String COL_TONE = "tone";
    private static final String COL_STATUS = "status";

    public AlarmDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_ALARMS + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_TIME + " TEXT, " +
                COL_TONE + " TEXT, " +
                COL_STATUS + " INTEGER)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ALARMS);
        onCreate(db);
    }

    public boolean addAlarm(AlarmModel alarm) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_TIME, alarm.getTime());
        values.put(COL_TONE, alarm.getTone());
        values.put(COL_STATUS, alarm.isActive() ? 1 : 0);
        long result = db.insert(TABLE_ALARMS, null, values);
        db.close();
        return result != -1;
    }

    public ArrayList<AlarmModel> getAllAlarms() {
        ArrayList<AlarmModel> alarms = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_ALARMS, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String time = cursor.getString(1);
                String tone = cursor.getString(2);
                boolean status = cursor.getInt(3) == 1;

                alarms.add(new AlarmModel(id, time, tone, status));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return alarms;
    }

    public boolean updateAlarmStatus(int id, boolean status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_STATUS, status ? 1 : 0);
        int result = db.update(TABLE_ALARMS, values, COL_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
        return result > 0;
    }

    public boolean deleteAlarm(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_ALARMS, COL_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
        return result > 0;
    }
}

