package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.Serializable;
import java.util.ArrayList;

public class SectionsDAO implements Serializable {
    public transient  DBHelperDisciplines dbHelperDisciplines;
    public transient SQLiteDatabase database;
    private transient ContentValues contentValues;
    public  void setDbHelpers(DBHelperDisciplines dbHelper) {
        this.dbHelperDisciplines = dbHelper;
    }

    public void createDatabases(){
        database = dbHelperDisciplines.getWritableDatabase();
    }
    public ArrayList<String> getDiscipline () {
        ArrayList<String> disciplines = new ArrayList<>();
        String query = "select * from " + DBHelperDisciplines.TABLE_DISCIPLINES;
        @SuppressLint("Recycle") Cursor cursor = database.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do{
                disciplines.add(cursor.getString(cursor.getColumnIndex(DBHelperDisciplines.KEY_DISCIPLINE)));
            }
            while(cursor.moveToNext());
        }
        return disciplines;
    }
    public boolean checkIsDisciplineExist (String name) {
        ArrayList<String> disciplines = new ArrayList<>();
        String query = "select * from " + DBHelperDisciplines.TABLE_DISCIPLINES  +
                " WHERE "+DBHelperDisciplines.KEY_DISCIPLINE + " = " + "'"+name +"'";
        @SuppressLint("Recycle") Cursor cursor = database.rawQuery(query, null);
        return cursor.moveToFirst();
    }
    public ContentValues makeContentValueForDiscipline(String name){
        contentValues = new ContentValues();
        contentValues.put(DBHelperDisciplines.KEY_DISCIPLINE, name);
        return contentValues;
    }
    public void addNewDiscipline (String name) {
        database.insert(DBHelperDisciplines.TABLE_DISCIPLINES, null ,
               makeContentValueForDiscipline(name));
    }
    public void getElectives(PeopleDAO peopleDAO){

    }
}
