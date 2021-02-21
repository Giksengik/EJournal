package com.example.myapplication;

import java.io.Serializable;
import java.util.ArrayList;

public class Section implements Serializable {
    public String name;
    public Teacher classTeacher;
    public ArrayList<Learner> learners;
    public void getList(){

    }
    public void  getListParents(){

    }
    Section(String name,Teacher classTeacher,ArrayList<Learner> learners){
        this.name=name;
        this.classTeacher=classTeacher;
        this.learners=learners;
    }
}
