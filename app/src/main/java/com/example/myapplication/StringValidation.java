package com.example.myapplication;

public class StringValidation {
    public static boolean isCorrectString(String string){
        return !("".equals(string) || !string.matches("[a-zA-Z| ]+") || string.matches("[ ]*"));
    }
    public static boolean isCorrectPhoneNumber(String phone){
        return !(phone.length() != 11 || !phone.matches("[0-9]+"));
    }
    public static boolean isCorrectID(String id, int maxNum){
        return id.matches("[0-9]+") && Integer.parseInt(id) <= maxNum && Integer.parseInt(id) != 0;
    }
}
