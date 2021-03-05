package com.example.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelperClasses extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 4;
    public static final String DATABASE_NAME = "contactDb2";
    public static final String TABLE_CLASSES = "classes";

    public static final String KEY_LEARNERS = "learnerId";
    public static final String KEY_NAME = "name";
    public static final String KEY_TEACHER_ID = "_id";


    public DBHelperClasses(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table " + TABLE_CLASSES + "(" + KEY_TEACHER_ID
                    + " integer primary key," + KEY_NAME + " text," + KEY_LEARNERS + " text"+ ")");
        }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_CLASSES);
        onCreate(db);
    }
}
