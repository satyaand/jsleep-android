package com.satyaJSleepJS.model;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

public class Serializable {
    @SerializedName("id")
    public final int id;

    private static HashMap<Class<?>, Integer> mapCounter = new HashMap<>();

    protected Serializable()
    {
        Integer serialTemp = mapCounter.get(getClass());
        if(serialTemp == null){
            serialTemp = 0;
        } else {
            serialTemp++;
        }
        mapCounter.put(getClass(), serialTemp);
        id = serialTemp;
    }
}
