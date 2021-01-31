package com.example.myapplication;

import java.io.Serializable;

public class Participant extends Person implements Serializable {
    private int CardID;
    Participant(String fullName, String phone,int CardID) {
        super(fullName, phone);
        this.CardID=CardID;
    }

    public Participant() {
    }

    public int getCardID() {
        return CardID;
    }

    public void setCardID(int cardID) {
        CardID = cardID;
    }
}
