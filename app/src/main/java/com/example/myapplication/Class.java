package com.example.myapplication;

import java.io.Serializable;

public class Class implements Serializable {
    public String number;
    public Teacher classTeacher;
    public Learner [] learners;
    public void getList(){

    }
    public void  getListParents(){

    }
    Class(String number,Teacher classTeacher,Learner [] learners){
        this.number=number;
        this.classTeacher=classTeacher;
        this.learners=learners;
    }
}
