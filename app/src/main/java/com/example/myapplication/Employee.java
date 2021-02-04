package com.example.myapplication;

import java.io.Serializable;

public class Employee extends Participant implements Serializable {
    private String position;
    Employee(String fullName, String phone, int CardID,String position) {
        super(fullName, phone, CardID);
        this.position=position;
        this.status = "EMPLOYEE";
    }

    public Employee() {
        this.status = "EMPLOYEE";
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
