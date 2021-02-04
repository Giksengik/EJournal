package com.example.myapplication;

import java.io.Serializable;

public class Learner extends Participant implements Serializable {
    private Parent [] parents;
    Learner(String fullName, String phone, int CardID, Parent [] parents) {
        super(fullName, phone, CardID);
        this.parents = parents;
        this.status = "LEARNER";
    }

    public Learner() {
        parents = new Parent[]{new Parent(), new Parent()};
        this.status = "LEARNER";
            }

    public Parent[] getParents() {
        return parents;
    }

    public void setParents(Parent[] parents) {
        this.parents = parents;
    }
}
