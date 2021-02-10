package com.example.myapplication;

import java.io.Serializable;
import java.util.ArrayList;

public class Elective implements Serializable {
    public String academicSubject;
    public ArrayList<Learner> listLearners;
    public Teacher  electiveTeacher;
    public void getList(){
    }
    public void  getListParents(){

    }
    Elective(String academicSubject,ArrayList<Learner> learners, Teacher classTeacher){
        this.academicSubject=academicSubject;
        this.listLearners = learners;
        this.electiveTeacher = classTeacher;
    }
    public int getImageRecourseID(){
        switch(academicSubject){
            case "Mathematics":
                return R.drawable.mathematics_in_list;
            case "Physics":
                return R.drawable.physics_in_list;
            case "Native Language":
                return R.drawable.native_language_in_list;
            case "Literature":
                return R.drawable.literature_in_list;
            case "Foreign language":
                return R.drawable.foreign_language_in_list;
            case "Social studies":
                return R.drawable.social_studies_in_list;
            case "History":
                return R.drawable.history_in_list;
            case "Biology":
                return R.drawable.biology_in_list;
            case "Chemistry":
                return R.drawable.chemisty_in_list;
            case "Computer science":
                return R.drawable.computer_science_in_list;
            case "Geography":
                return R.drawable.geography_in_list;
            case "Physical Training":
                return R.drawable.physical_training_in_list;
            default:
                return R.drawable.not_found_subject;
        }
    }
}
