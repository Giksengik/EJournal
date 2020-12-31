package com.example.myapplication;

import java.io.Serializable;

public class Person implements Serializable {
    public String fullName;
    public String phone;
    Person(String fullName,String phone){
        this.fullName=fullName;
        this.phone=phone;
    }
}

