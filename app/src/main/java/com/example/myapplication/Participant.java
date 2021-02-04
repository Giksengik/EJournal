package com.example.myapplication;

import android.widget.ImageView;

import java.io.Serializable;

public class Participant extends Person implements Serializable {
    private int CardID;
    Participant(String fullName, String phone,int CardID) {
        super(fullName, phone);
        this.CardID=CardID;
    }
    public String status;
    public Participant() {
    }
    public int getImageRecourse(){
        switch (status){
            case "EMPLOYEE":
                return R.drawable.employee_icon_in_list;
            case "LEARNER" :
                return R.drawable.learner_icon_in_list;
            case "TEACHER":
                return R.drawable.teacher_icon_in_list;
        }
        return R.drawable.not_found_person_in_list;
    }

    public void setStatus(String status){
        this.status = status;
    }

    public int getCardID() {
        return CardID;
    }

    public void setCardID(int cardID) {
        CardID = cardID;
    }
}
