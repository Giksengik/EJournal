package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.Serializable;
import java.util.ArrayList;

public class ClassesDAO implements Serializable {
    public transient   DBHelperClasses dbHelperClasses;
    public transient SQLiteDatabase database;
    private transient ContentValues contentValues;
    public  void setDbHelper(DBHelperClasses dbHelper) {
        this.dbHelperClasses = dbHelper;
    }
    public void createDatabase(){
        database = dbHelperClasses.getWritableDatabase();
    }
    public void getClasses(PeopleDAO peopleDAO){
        ArrayList<Class> classes = new ArrayList<>();
        @SuppressLint("Recycle") Cursor cursor = database.query(DBHelperClasses.TABLE_CLASSES,
                null,null,null,null,null,null);
        if (cursor.moveToFirst()) {
            do {
                addClass(cursor.getString(cursor.getColumnIndex(DBHelper.KEY_NAME)),
                        peopleDAO.findTeacherByID(cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_TEACHER_ID))),
                        getLearnersListByParsingString(cursor.getString(cursor.getColumnIndex(DBHelperClasses.KEY_LEARNERS)), peopleDAO), classes);
            }while(cursor.moveToNext());
        }
        peopleDAO.school.listClasses = classes;
    }
    public void addLearnerToClass(int teacherID, int learnerID){
        String query ="select * from " + DBHelperClasses.TABLE_CLASSES +" WHERE "+DBHelperClasses.KEY_TEACHER_ID + " = " + teacherID;
        @SuppressLint("Recycle") Cursor cursor = database.rawQuery(query,null);
        if (cursor.moveToFirst()) {
            String toUpdate =  cursor.getString(cursor.getColumnIndex(DBHelperClasses.KEY_LEARNERS)) +learnerID + ",";
            database.execSQL("UPDATE "+DBHelperClasses.TABLE_CLASSES+" SET " + DBHelperClasses.KEY_LEARNERS + " = " +"'" + toUpdate + "'" +
                    " WHERE "+DBHelperClasses.KEY_TEACHER_ID + " = " + teacherID);
        }
        cursor.close();
    }
    private void addClass (String name, Teacher teacher, ArrayList<Learner> learnersList, ArrayList<Class> classes) {
        classes.add(new Class(name, teacher,learnersList));
    }
    private ArrayList<Learner> getLearnersListByParsingString(String learnersString, PeopleDAO peopleDAO){
        ArrayList<Learner> learnersList = new ArrayList<>();
        StringBuilder str = new StringBuilder();
        for (int i = 0; i<learnersString.length(); i ++){
            if (learnersString.charAt(i) == ','){
                learnersList.add(peopleDAO.findLearnerByID(Integer.parseInt(str.toString())));
                str.setLength(0);
            }
            else str.append(learnersString.charAt(i));
        }
        return learnersList;
    }
    public ContentValues makeContentValueForClass(String name, int teacherID){
        contentValues = new ContentValues();
        contentValues.put(DBHelper.KEY_TEACHER_ID, teacherID);
        contentValues.put(DBHelper.KEY_NAME, name);
        contentValues.put(DBHelperClasses.KEY_LEARNERS,"");
        return contentValues;
    }
}
