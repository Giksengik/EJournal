package com.example.myapplication;

import java.io.Serializable;

public class Parent extends Person implements Serializable {
    Parent(String fullName, String phone) {
        super(fullName, phone);
    }

    public Parent() {
    }
}
