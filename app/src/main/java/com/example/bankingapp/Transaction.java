package com.example.bankingapp;

public class Transaction {
    String sender,reciever;
    Float amount;
    int isSuccessful;

    public Transaction(String sender, String reciever, Float amount, int isSuccessful) {
        this.sender = sender;
        this.reciever = reciever;
        this.amount = amount;
        this.isSuccessful = isSuccessful;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReciever() {
        return reciever;
    }

    public void setReciever(String reciever) {
        this.reciever = reciever;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public int getIsSuccessful() {
        return isSuccessful;
    }

    public void setIsSuccessful(int isSuccessful) {
        this.isSuccessful = isSuccessful;
    }
}
