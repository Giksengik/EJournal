package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class ElectivesDAO implements Serializable {
    public transient   DBHelperElectives dbHelperElectives;
    public transient SQLiteDatabase database;
    private transient ContentValues contentValues;
    public  void setDbHelper(DBHelperElectives dbHelper) {
        this.dbHelperElectives = dbHelper;
    }
    public void createDatabase(){
        database = dbHelperElectives.getWritableDatabase();
    }
    public ContentValues makeContentValueForElective(String subject, int teacherID){
        contentValues = new ContentValues();
        contentValues.put(DBHelperElectives.KEY_SUBJECT, subject);
        contentValues.put(DBHelperElectives.KEY_TEACHER_ID, teacherID);
        return contentValues;
    }
    private ArrayList<Learner> parseLearnersString(String toParse) {
        return new ArrayList<Learner>();
    }
    public ArrayList<Elective> getElectives(PeopleDAO peopleDAO){
        ArrayList<Elective> electives = new ArrayList<>();
        @SuppressLint("Recycle") Cursor cursor = database.query(DBHelperElectives.TABLE_ELECTIVES,
                null,null,null,null,null,null);
        if (cursor.moveToFirst()) {
            do {
                electives.add(new Elective(cursor.getString(cursor.getColumnIndex(DBHelperElectives.KEY_SUBJECT)),
                        parseLearnersString(cursor.getString(cursor.getColumnIndex(DBHelperElectives.KEY_LEARNERS))),
                        peopleDAO.findTeacherByID(cursor.getInt(cursor.getColumnIndex(DBHelperElectives.KEY_TEACHER_ID)))));
                } while(cursor.moveToNext());
            }
        return electives;
    }

}
