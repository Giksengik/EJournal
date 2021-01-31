package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

public class PeopleDAO implements Serializable{
    public  int PEOPLE_COUNT = 0;
    private transient   DBHelper dbHelper;
    public  School school;
    public transient SQLiteDatabase database;
    private transient  ContentValues contentValues;
    public PeopleDAO(){
        school = new School();
    }
    public void createDatabase(){
        database = dbHelper.getWritableDatabase();
    }
    public ContentValues makeContentValueForEmployee(String name, String phone, String position){
        contentValues = new ContentValues();
        contentValues.put(DBHelper.KEY_STATUS, "EMPLOYEE");
        contentValues.put(DBHelper.KEY_ID, ++PEOPLE_COUNT);
        contentValues.put(DBHelper.KEY_NAME, name);
        contentValues.put(DBHelper.KEY_PHONE, phone);
        contentValues.put(DBHelper.KEY_POSITION, position);
        return contentValues;
    }
    public ContentValues makeContentValueForLearner(String name, String phone, String firstParentName, String firstParentPhone, String secondParentName,
                                                     String secondParentPhone){
        contentValues = new ContentValues();
        contentValues.put(DBHelper.KEY_STATUS, "LEARNER");
        contentValues.put(DBHelper.KEY_ID, ++PEOPLE_COUNT);
        contentValues.put(DBHelper.KEY_NAME, name);
        contentValues.put(DBHelper.KEY_PHONE, phone);
        contentValues.put(DBHelper.KEY_FirstParentName, firstParentName);
        contentValues.put(DBHelper.KEY_FirstParentPhone, firstParentPhone);
        contentValues.put(DBHelper.KEY_SecondParentName, secondParentName);
        contentValues.put(DBHelper.KEY_SecondParentPhone, secondParentPhone);
        return contentValues;
    }
    public ContentValues makeContentValueForTeacher(String name, String phone, String position, String qualifications) {
        contentValues = new ContentValues();
        contentValues.put(DBHelper.KEY_STATUS, "TEACHER");
        contentValues.put(DBHelper.KEY_ID, ++PEOPLE_COUNT);
        contentValues.put(DBHelper.KEY_NAME, name);
        contentValues.put(DBHelper.KEY_PHONE, phone);
        contentValues.put(DBHelper.KEY_POSITION, position);
        contentValues.put(DBHelper.KEY_QUALIFICATIONS, qualifications);
        return contentValues;
    }
    public void getPeople(){
         @SuppressLint("Recycle") Cursor cursor = database.query(DBHelper.TABLE_PARTICIPANTS,
                null,null,null,null,null,null);
        if (cursor.moveToFirst()) {
            do {
                switch (cursor.getString(cursor.getColumnIndex(DBHelper.KEY_STATUS))) {
                    case "EMPLOYEE":
                        addEmployee(cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_ID)),
                                cursor.getString(cursor.getColumnIndex(DBHelper.KEY_NAME)),
                                cursor.getString(cursor.getColumnIndex(DBHelper.KEY_PHONE)),
                                cursor.getString(cursor.getColumnIndex(DBHelper.KEY_POSITION)));
                        break;
                    case "LEARNER":
                        addLearner(cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_ID)),
                                cursor.getString(cursor.getColumnIndex(DBHelper.KEY_NAME)),
                                cursor.getString(cursor.getColumnIndex(DBHelper.KEY_PHONE)),
                                cursor.getString(cursor.getColumnIndex(DBHelper.KEY_FirstParentName)),
                                cursor.getString(cursor.getColumnIndex(DBHelper.KEY_FirstParentPhone)),
                                cursor.getString(cursor.getColumnIndex(DBHelper.KEY_SecondParentName)),
                                cursor.getString(cursor.getColumnIndex(DBHelper.KEY_SecondParentPhone)));
                        break;
                    case "TEACHER":
                        addTeacher(cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_ID)),
                                cursor.getString(cursor.getColumnIndex(DBHelper.KEY_NAME)),
                                cursor.getString(cursor.getColumnIndex(DBHelper.KEY_PHONE)),
                                cursor.getString(cursor.getColumnIndex(DBHelper.KEY_POSITION)),
                                cursor.getString(cursor.getColumnIndex(DBHelper.KEY_QUALIFICATIONS)));
                        break;
                }
                if(PEOPLE_COUNT < cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_ID)))
                    PEOPLE_COUNT = cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_ID));
            }
            while (cursor.moveToNext());
            }
        }
        private void addEmployee(int cardID, String name, String phone, String position){
        school.listEmployees.add(new Employee(name,phone,cardID,position));
    }
    private void addLearner(int cardId, String name, String phone, String firstParentName,String firstParentPhone,
                            String secondParentName, String secondParentPhone){
        school.listLearners.add(new Learner(name,phone,cardId, new Parent[]{new Parent(firstParentName, firstParentPhone),
                new Parent(secondParentName, secondParentPhone)}));

    }

    private void addTeacher(int cardID, String name, String phone, String position, String qualifications){
        school.listTeachers.add(new Teacher(name,phone,cardID,position,qualifications));
    }
    public Teacher findTeacherByID(int id){
        String query ="select * from " + DBHelper.TABLE_PARTICIPANTS +
                " WHERE "+DBHelper.KEY_ID + " = " + id;
        @SuppressLint("Recycle") Cursor cursor = database.rawQuery(query,null);
        if (cursor.moveToFirst()){
            if (cursor.getString(cursor.getColumnIndex(DBHelper.KEY_STATUS)).equals("TEACHER")){
             return new Teacher(cursor.getString(cursor.getColumnIndex(DBHelper.KEY_NAME)),
                     cursor.getString(cursor.getColumnIndex(DBHelper.KEY_PHONE)),
                     cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_ID)),
                     cursor.getString(cursor.getColumnIndex(DBHelper.KEY_POSITION)),
                     cursor.getString(cursor.getColumnIndex(DBHelper.KEY_QUALIFICATIONS)));
            }
        }
        return null;
    }
    public Learner findLearnerByID (int id) {
        String query = "select * from " + DBHelper.TABLE_PARTICIPANTS +
                " WHERE "+DBHelper.KEY_ID + " = " + id;
        @SuppressLint("Recycle") Cursor cursor = database.rawQuery(query, null);
        if (cursor.moveToFirst()){
            if (cursor.getString(cursor.getColumnIndex(DBHelper.KEY_STATUS)).equals("LEARNER")){
                return new Learner(cursor.getString(cursor.getColumnIndex(DBHelper.KEY_NAME)),
                        cursor.getString(cursor.getColumnIndex(DBHelper.KEY_PHONE)),
                        cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_ID)),
                        new Parent[] {new Parent(cursor.getString(cursor.getColumnIndex(DBHelper.KEY_FirstParentName)),
                                cursor.getString(cursor.getColumnIndex(DBHelper.KEY_FirstParentPhone))), new Parent(
                                cursor.getString(cursor.getColumnIndex(DBHelper.KEY_SecondParentName)),
                                cursor.getString(cursor.getColumnIndex(DBHelper.KEY_SecondParentPhone)))});
            }
        }
        return null;
    }
    public Employee findEmployeeByID (int id) {
            String query = "select * from " + DBHelper.TABLE_PARTICIPANTS +
                    " WHERE "+DBHelper.KEY_ID + " = " + id;
        @SuppressLint("Recycle") Cursor cursor = database.rawQuery(query, null);
        if (cursor.moveToFirst()){
                return new Employee(cursor.getString(cursor.getColumnIndex(DBHelper.KEY_NAME)),
                        cursor.getString(cursor.getColumnIndex(DBHelper.KEY_PHONE)),
                        cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_ID)),
                        cursor.getString(cursor.getColumnIndex(DBHelper.KEY_POSITION)));
        }
        return null;
    }
    public String findParticipantsStatusByID(int id){
        String query = "select * from " + DBHelper.TABLE_PARTICIPANTS +
                " WHERE "+DBHelper.KEY_ID + " = " + id;
        @SuppressLint("Recycle") Cursor cursor = database.rawQuery(query, null);
        if(cursor.moveToFirst()){
        return cursor.getString(cursor.getColumnIndex(DBHelper.KEY_STATUS));
        }
        return null;
    }
    public DBHelper getDbHelper() {
        return dbHelper;
    }
    public  void setDbHelper(DBHelper dbHelper) {
        this.dbHelper = dbHelper;
    }
    public School getSchool() {
        return school;
    }
    public void setSchool(School school) {
        this.school = school;
    }
    public int getEmployeeListLength(){
        return school.listEmployees.size();
    }
    public int getLearnerListLength(){
        return school.listLearners.size();
    }
    public int getTeacherListLength(){
        return school.listTeachers.size();
    }

    public SQLiteDatabase getDatabase() {
        return database;
    }

    public void setDatabase(SQLiteDatabase database) {
        this.database = database;
    }
}
