package com.example.myapplication;

import java.io.Serializable;

public class School implements Serializable {
    public Employee [] employees;
    public Teacher [] teachers;
    public Learner [] learners;
    public String address;
    public String name;
    public static int num_of_cards=1;
    public Class [] classes;
    public Elective [] electives;
    public Section [] sections;
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
    School(Employee [] employees,Teacher [] teachers,Learner [] learners,String address,
           String name, Class [] classes,Elective [] electives ,Section[] sections){
        this.employees=employees;
        this.teachers=teachers;
        this.learners=learners;
        this.address=address;
        this.name=name;
        this.classes=classes;
        this.electives=electives;
        this.sections=sections;
    }
}
