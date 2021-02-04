package com.example.myapplication;

import java.io.Serializable;

public class Teacher extends Participant implements Serializable {
    private String position;
    private String  qualifications;
    Teacher(String fullName, String phone, int CardID,String position, String  qualifications) {
        super(fullName, phone, CardID);
        this.position=position;
        this.qualifications=qualifications;
        this.status = "TEACHER";
    }

    public Teacher() {
        this.status = "TEACHER";
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getQualifications() {
        return qualifications;
    }

    public void setQualifications(String qualifications) {
        this.qualifications = qualifications;
    }
}
