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
        contentValues.put(DBHelperElectives.KEY_LEARNERS, "");
        return contentValues;
    }
    private ArrayList<Learner> parseLearnersString(String toParse, PeopleDAO peopleDAO) {
        ArrayList<Learner> learners = new ArrayList<>();
        if(toParse != null) {
            StringBuilder currentID = new StringBuilder();
            for (int i = 0; i < toParse.length(); i++) {
                if (toParse.charAt(i) == ',') {
                    learners.add(peopleDAO.findLearnerByID(Integer.parseInt(currentID.toString())));
                    currentID.setLength(0);
                } else currentID.append(toParse.charAt(i));
            }
        }
        return learners;
    }
    public void addLearnerToElectiveDB(int learnerID, int teacherID){
        String query = "select * from " + DBHelperElectives.TABLE_ELECTIVES +
                " WHERE "+DBHelperElectives.KEY_TEACHER_ID + " = " + teacherID;
        @SuppressLint("Recycle") Cursor cursor = database.rawQuery(query, null);
        if(cursor.moveToFirst()){
            String oldStringLearners = cursor.getString(cursor.getColumnIndex(DBHelperElectives.KEY_LEARNERS))+"";
            String newStringLearners = oldStringLearners + learnerID + ",";
            database.execSQL("UPDATE "+DBHelperElectives.TABLE_ELECTIVES+" SET " + DBHelperElectives.KEY_LEARNERS + " = " +"'" + newStringLearners + "'");
        }
    }
    public ArrayList<Elective> getElectives(PeopleDAO peopleDAO){
        ArrayList<Elective> electives = new ArrayList<>();
        @SuppressLint("Recycle") Cursor cursor = database.query(DBHelperElectives.TABLE_ELECTIVES,
                null,null,null,null,null,null);
        if (cursor.moveToFirst()) {
            do {
                electives.add(new Elective(cursor.getString(cursor.getColumnIndex(DBHelperElectives.KEY_SUBJECT)),
                        parseLearnersString(cursor.getString(cursor.getColumnIndex(DBHelperElectives.KEY_LEARNERS)),peopleDAO),
                        peopleDAO.findTeacherByID(cursor.getInt(cursor.getColumnIndex(DBHelperElectives.KEY_TEACHER_ID)))));
                } while(cursor.moveToNext());
            }
        return electives;
    }

}
