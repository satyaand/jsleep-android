package com.satyaJSleepJS.model;

import com.google.gson.annotations.SerializedName;

public class Account extends Serializable {
    @SerializedName("name")
    public String name;

    @SerializedName("password")
    public String password;

    @SerializedName("renter")
    public Renter renter;

    @SerializedName("email")
    public String email;

    @SerializedName("balance")
    public double balance;

    public Account(String name, String email, String password)
    {
        // initialise instance variables
        this.name = name;
        this.email = email;
        this.password = password;
        this.renter = null;
        this.balance = 0;
    }

    @Override
    public String toString(){
        return "Account{" +
                "balance= " + balance +
                ", email= " + email + '\'' +
                ", name= " + name + '\'' +
                ", password= " + password + '\'' +
                ", renter= " + renter + '\'' +
                "}";
    }
}
