package com.example.myapplication;

import java.io.Serializable;

public class Participant extends Person implements Serializable {
    public int CardID;
    Participant(String fullName, String phone,int CardID) {
        super(fullName, phone);
        this.CardID=CardID;
    }
}
