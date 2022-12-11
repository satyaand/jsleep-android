package com.satyaJSleepJS.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.sql.Date;

public class Room extends Serializable {
    @SerializedName("accountId")
    public int accountId;

    @SerializedName("name")
    public String name;

    @SerializedName("booked")
    public ArrayList<Date> booked = new ArrayList<Date>();

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
    public ArrayList<Facility> facility;

    public String toString(){
        return this.name;
    }
}
