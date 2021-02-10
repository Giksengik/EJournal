package com.example.myapplication;

import java.io.Serializable;
import java.util.ArrayList;

public class School implements Serializable {
    public ArrayList <Employee> listEmployees;
    public ArrayList <Teacher>  listTeachers;
    public ArrayList <Learner>  listLearners;
    public String name;
    public ArrayList <Class> listClasses;
    public ArrayList <Elective> listElectives;
    public ArrayList <Section> listSections;
    public ArrayList <Teacher> getListTeachers(){
        return listTeachers;
    }
    public ArrayList <Employee> getListEmployees(){
        return listEmployees;
    }
    public ArrayList <Learner> getListLearners(){
        return listLearners;
    }
    public void getElectronicJournal(){

    }
    public void getParticipant(){
    }

    public School(){
        this.listEmployees= new ArrayList<>();
        this.listTeachers=new ArrayList<>();
        this.listLearners=new ArrayList<>();
        this.listClasses=new ArrayList<>();
        this.listElectives = new ArrayList<>();
        this.listSections = new ArrayList<>();
    }
}
