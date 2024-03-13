package com.example.studentbudgetigapp;

public class Ifood {
    String type,date;
    int amount;

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public Ifood(String type, String date, int amount) {
        this.type = type;
        this.date = date;
        this.amount = amount;
    }
}
