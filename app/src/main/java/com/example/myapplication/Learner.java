package com.example.myapplication;

import java.io.Serializable;

public class Learner extends Participant implements Serializable {
    public Parent [] parents;
    Learner(String fullName, String phone, int CardID, Parent [] parents) {
        super(fullName, phone, CardID);
        this.parents=parents;
    }
}
