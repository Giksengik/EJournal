package com.example.myapplication;

import java.io.Serializable;

public class Person implements Serializable {
    private String fullName;
    private String phone;
    Person(String fullName,String phone){
        this.fullName=fullName;
        this.phone=phone;
    }

    public Person() {
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}

