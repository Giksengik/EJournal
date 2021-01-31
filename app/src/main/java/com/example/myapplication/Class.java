package com.example.myapplication;

import android.widget.ArrayAdapter;

import java.io.Serializable;
import java.util.ArrayList;

public class Class implements Serializable {
    public String number;
    public Teacher classTeacher;
    public ArrayList<Learner> learnersList;
    public void getList(){

    }
    public void  getListParents(){

    }
    Class(String number,Teacher classTeacher,ArrayList<Learner> learnersList){
        this.number=number;
        this.classTeacher=classTeacher;
        this.learnersList = learnersList;
    }
}
