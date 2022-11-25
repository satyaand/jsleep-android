package com.satyaJSleepJS.model;

public enum City {
    BALI("Bali"),
    BANDUNG("Bandung"),
    SURABAYA("Surabaya"),
    JAKARTA("Jakarta"),
    SEMARANG("Semarang"),
    MEDAN("Medan"),
    DEPOK("Depok"),
    BEKASI("Bekasi"),
    LAMPUNG("Lampung");

    private final String userFriendlyName;
    City(String userFriendlyName){
        this.userFriendlyName = userFriendlyName;
    }

    @Override
    public String toString(){
        return userFriendlyName;
    }
}
