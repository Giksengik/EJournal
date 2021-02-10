package com.example.myapplication;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DBHelperElectives extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "contactDb3";
    public static final String TABLE_ELECTIVES = "electives";

    public static final String KEY_SUBJECT = "electiveSubject";
    public static final String KEY_TEACHER_ID = "teacherID";
    public static final String KEY_LEARNERS = "learnersId";

    public DBHelperElectives(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_ELECTIVES + "(" + KEY_TEACHER_ID
                + " integer primary key," + KEY_SUBJECT + " text," + KEY_LEARNERS + " text" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_ELECTIVES);
        onCreate(db);
    }
}
