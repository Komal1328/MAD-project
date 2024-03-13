package com.example.studentbudgetigapp;

public class HelperClass {
    String name,email,password;
    int amount;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }


    public HelperClass(String name, String email, String password, int amount) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.amount = amount;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

//    public HelperClass(String name, String email, String password) {
//        this.name = name;
//        this.email = email;
//        this.password = password;
//        this.amount = 0;
//    }
    public HelperClass() {
    }
}
