package com.example.myapplication;

import java.io.Serializable;

public class Employee extends Participant implements Serializable {
    public String position;
    Employee(String fullName, String phone, int CardID,String position) {
        super(fullName, phone, CardID);
        this.position=position;
    }
}
