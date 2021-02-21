package com.example.myapplication;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelperSections extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "contactDb5";
    public static final String TABLE_SECTIONS = "sections";

    public static final String KEY_DISCIPLINE = "discipline";
    public static final String KEY_TEACHER_ID = "teacherID";
    public static final String KEY_LEARNERS = "learnersId";
    public DBHelperSections(@Nullable Context context) {
        super(context, DATABASE_NAME,null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_SECTIONS + "(" + KEY_TEACHER_ID
                + " integer primary key," + KEY_DISCIPLINE + " text," + KEY_LEARNERS + " text" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_SECTIONS);
        onCreate(db);
    }

}
