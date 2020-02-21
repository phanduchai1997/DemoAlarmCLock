package com.example.demoalarmclock.model;

public class Number {
    private String number;
    private int type = 1;

    public Number(String number, int type) {
        this.number = number;
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Number(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
