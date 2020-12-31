package com.example.myapplication;

import java.io.Serializable;

public class Teacher extends Participant implements Serializable {
    public String position;
    public String [] qualifications;
    Teacher(String fullName, String phone, int CardID,String position, String [] qualifications) {
        super(fullName, phone, CardID);
        this.position=position;
        this.qualifications=qualifications;
    }
}
