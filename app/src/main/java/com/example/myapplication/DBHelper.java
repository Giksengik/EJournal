package com.example.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.io.Serializable;

public class DBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "contactDb";
    public static final String TABLE_PARTICIPANTS = "participants";

    public static final String KEY_ID = "_id";
    public static final String KEY_TEACHER_ID = "_id";
    public static final String KEY_STATUS = "status";
    public static final String KEY_NAME = "name";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_POSITION = "position";
    public static final String KEY_FirstParentName = "firstParentName";
    public static final String KEY_FirstParentPhone = "firstParentPhone";
    public static final String KEY_SecondParentName = "secondParentName";
    public static final String KEY_SecondParentPhone = "secondParentPhone";
    public static final String KEY_QUALIFICATIONS = "qualifications";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_PARTICIPANTS + "(" + KEY_ID
                + " integer primary key," + KEY_STATUS + " text," + KEY_NAME + " text," + KEY_PHONE + " text,"+ KEY_POSITION + " text,"
                + KEY_FirstParentName + " text,"+ KEY_FirstParentPhone + " text,"+ KEY_SecondParentName + " text,"+ KEY_SecondParentPhone + " text,"
                + KEY_QUALIFICATIONS + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_PARTICIPANTS);
        onCreate(db);
    }
}
