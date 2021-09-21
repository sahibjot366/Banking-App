package com.example.bankingapp;

public class User {
    private String user_id;
    private String name;
    private String email;
    private float current_balance;

    public User(String user_id, String name, String email, float current_balance) {
        this.user_id = user_id;
        this.name = name;
        this.email = email;
        this.current_balance = current_balance;
    }
    public User(){

    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public float getCurrent_balance() {
        return current_balance;
    }

    public void setCurrent_balance(float current_balance) {
        this.current_balance = current_balance;
    }
}
