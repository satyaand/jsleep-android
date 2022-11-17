package com.satyaJSleepJS.model;

import com.google.gson.annotations.SerializedName;

public class Price {
    @SerializedName("discount")
    public double discount;

    @SerializedName("price")
    public double price;
}
