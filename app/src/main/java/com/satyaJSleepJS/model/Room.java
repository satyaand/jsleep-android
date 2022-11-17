package com.satyaJSleepJS.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;

public class Room extends Serializable {
    @SerializedName("accountId")
    public int accountId;

    @SerializedName("name")
    public String name;

    @SerializedName("booked")
    public ArrayList<Date> booked;

    @SerializedName("address")
    public String address;

    @SerializedName("price")
    public Price price;

    @SerializedName("city")
    public City city;

    @SerializedName("size")
    public int size;

    @SerializedName("bedType")
    public BedType bedType;

    @SerializedName("facility")
    public Facility facility;

    public String toString(){
        return this.name;
    }
}
