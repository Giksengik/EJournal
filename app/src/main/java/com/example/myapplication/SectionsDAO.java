package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.Serializable;
import java.util.ArrayList;

public class SectionsDAO implements Serializable {
    public transient  DBHelperDisciplines dbHelperDisciplines;
    public transient  DBHelperSections dbHelperSections;
    public transient SQLiteDatabase databaseDisciplines;
    public transient SQLiteDatabase databaseSections;
    private transient ContentValues contentValues;
    public  void setDbHelpers(DBHelperDisciplines dbHelper, DBHelperSections dbHelperSections) {
        this.dbHelperDisciplines = dbHelper;
        this.dbHelperSections = dbHelperSections;
    }

    public void createDatabases(){
        databaseDisciplines = dbHelperDisciplines.getWritableDatabase();
        databaseSections = dbHelperSections.getWritableDatabase();
    }
    public ArrayList<String> getDiscipline () {
        ArrayList<String> disciplines = new ArrayList<>();
        String query = "select * from " + DBHelperDisciplines.TABLE_DISCIPLINES;
        @SuppressLint("Recycle") Cursor cursor = databaseDisciplines.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do{
                disciplines.add(cursor.getString(cursor.getColumnIndex(DBHelperDisciplines.KEY_DISCIPLINE)));
            }
            while(cursor.moveToNext());
        }
        return disciplines;
    }
    public boolean checkIsDisciplineExist (String name) {
        String query = "select * from " + DBHelperDisciplines.TABLE_DISCIPLINES  +
                " WHERE "+DBHelperDisciplines.KEY_DISCIPLINE + " = " + "'"+name +"'";
        @SuppressLint("Recycle") Cursor cursor = databaseDisciplines.rawQuery(query, null);
        return cursor.moveToFirst();
    }
    public ContentValues makeContentValueForDiscipline(String name){
        contentValues = new ContentValues();
        contentValues.put(DBHelperDisciplines.KEY_DISCIPLINE, name);
        return contentValues;
    }
    public void addNewDiscipline (String name) {
        databaseDisciplines.insert(DBHelperDisciplines.TABLE_DISCIPLINES, null ,
               makeContentValueForDiscipline(name));
    }
    public ContentValues makeContentValueForSection(int teacherID, String discipline) {
        contentValues = new ContentValues();
        contentValues.put(DBHelperSections.KEY_DISCIPLINE, discipline);
        contentValues.put(DBHelperSections.KEY_TEACHER_ID, teacherID);
        contentValues.put(DBHelperSections.KEY_LEARNERS, "");
        return contentValues;
    }
    public void addNewSection (Teacher teacher,String discipline) {
        databaseSections.insert(DBHelperSections.TABLE_SECTIONS, null ,
                makeContentValueForSection(teacher.getCardID(),discipline));
    }
    public ArrayList<Section> getSections(PeopleDAO peopleDAO){
        String query = "select * from " + DBHelperSections.TABLE_SECTIONS;
        @SuppressLint("Recycle") Cursor cursor = databaseSections.rawQuery(query, null);
        ArrayList<Section> sections = new ArrayList<>();
        if(cursor.moveToFirst()){
            do{
                sections.add(new Section(
                        cursor.getString(cursor.getColumnIndex(DBHelperSections.KEY_DISCIPLINE)),
                        peopleDAO.findTeacherByID(cursor.getInt(cursor.getColumnIndex(DBHelperSections.KEY_TEACHER_ID))),
                        parseLearnersString(cursor.getString(cursor.getColumnIndex(DBHelperSections.KEY_LEARNERS)),peopleDAO))
                );
            } while(cursor.moveToNext());
        }
        return sections;
    }
    public boolean isSectionAlreadyExist(String discipline) {
        String query = "select * from " + DBHelperSections.TABLE_SECTIONS  +
                " WHERE "+DBHelperSections.KEY_DISCIPLINE + " = " + "'"+discipline +"'";
            @SuppressLint("Recycle") Cursor cursor = databaseSections.rawQuery(query, null);
        return cursor.moveToFirst();
    }
    public Section getSectionByDiscipline (String discipline,PeopleDAO peopleDAO) {
        String query = "select * from " + DBHelperSections.TABLE_SECTIONS  +
                " WHERE "+DBHelperSections.KEY_DISCIPLINE + " = " + "'"+discipline +"'";
        @SuppressLint("Recycle") Cursor cursor = databaseSections.rawQuery(query, null);
        if(cursor.moveToFirst()){
            return new Section( cursor.getString(cursor.getColumnIndex(DBHelperSections.KEY_DISCIPLINE)),
                    peopleDAO.findTeacherByID(cursor.getInt(cursor.getColumnIndex(DBHelperSections.KEY_TEACHER_ID))),
                    parseLearnersString(cursor.getString(cursor.getColumnIndex(DBHelperSections.KEY_LEARNERS)),peopleDAO));
        }
        return null;
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
    public void addLearnerToSection(int learnerID, String discipline) {
        String query = "select * from " + DBHelperSections.TABLE_SECTIONS  +
                " WHERE "+DBHelperSections.KEY_DISCIPLINE + " = " + "'"+discipline +"'";
        @SuppressLint("Recycle") Cursor cursor = databaseSections.rawQuery(query, null);
        if(cursor.moveToFirst()){
            String oldStringLearners = cursor.getString(cursor.getColumnIndex(DBHelperElectives.KEY_LEARNERS))+"";
            String newStringLearners = oldStringLearners + learnerID + ",";
            databaseSections.execSQL("UPDATE "+DBHelperSections.TABLE_SECTIONS+" SET "
                    + DBHelperSections.KEY_LEARNERS + " = " +"'" + newStringLearners + "'" +
                    " WHERE "+DBHelperSections.KEY_DISCIPLINE + " = " +"'" + discipline+ "'" );
        }

    }


}
