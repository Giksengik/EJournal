package com.example.myapplication;

import java.io.Serializable;

public class Section implements Serializable {
    public String name;
    public Teacher classTeacher;
    public Learner [] learners;
    public void getList(){

    }
    public void  getListParents(){

    }
    Section(String name,Teacher classTeacher,Learner [] learners){
        this.name=name;
        this.classTeacher=classTeacher;
        this.learners=learners;
    }
}
