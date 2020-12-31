package com.example.myapplication;

import java.io.Serializable;

public class Elective implements Serializable {
    public String academicSubject;
    public Learner [] learners;
    public Teacher [] classTeacher;
    public void getList(){
    }
    public void  getListParents(){
    }
    Elective(String academicSubject,Learner [] learners, Teacher [] classTeacher){
        this.academicSubject=academicSubject;
        this.learners=learners;
        this.classTeacher=classTeacher;
    }
}
