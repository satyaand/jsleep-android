package com.satyaJSleepJS.model;

public enum BedType {
    DOUBLE("Double"),
    SINGLE("Single"),
    QUEEN("Queen"),
    KING("King");

    private final String userFriendlyName;

    BedType(String userFriendlyName){
        this.userFriendlyName = userFriendlyName;
    }

    @Override
    public String toString(){return userFriendlyName;}
}
