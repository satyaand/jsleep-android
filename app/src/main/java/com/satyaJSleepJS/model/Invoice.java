package com.satyaJSleepJS.model;

public class Invoice extends Serializable {
    public int buyerId;
    public int renterId;
    public PaymentStatus status;

    public enum PaymentStatus{
        WAITING, SUCCESS, FAILED
    }

    public enum RoomRating{
        BAD, NEUTRAL, GOOD, NONE
    }
}
