package com.example.myapplication;

import java.io.Serializable;
import java.util.ArrayList;

public class School implements Serializable {
    public ArrayList <Employee> listEmployees;
    public ArrayList <Teacher>  listTeachers;
    public ArrayList <Learner>  listLearners;
    public String address;
    public String name;
    public static int num_of_cards=1;
    public static int num_of_classes=0;
    public ArrayList <Class> listClasses;
    public ArrayList <Elective> listElectives;
    public ArrayList <Section> listSections;
    public void getListTeachers(){

    }
    public void getListEmployees(){

    }
    public void getListLearners(){

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
