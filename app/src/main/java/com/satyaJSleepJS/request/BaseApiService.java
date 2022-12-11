package com.satyaJSleepJS.request;

import com.satyaJSleepJS.model.Account;
import com.satyaJSleepJS.model.BedType;
import com.satyaJSleepJS.model.City;
import com.satyaJSleepJS.model.Facility;
import com.satyaJSleepJS.model.Payment;
import com.satyaJSleepJS.model.Renter;
import com.satyaJSleepJS.model.Room;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BaseApiService {
    @GET("account/{id}")
    Call<Account> getAccount (@Path("id") int id);

    @GET("room/getAllRoom")
    Call<ArrayList<Room>> getAllRoom (@Query("page") int page, @Query("pageSize") int pageSize);

    @GET("room/{id}")
    Call<Room> getRoom (@Path("id") int accountId);
    
    @POST("account/login")
    Call<Account> loginAccount(@Query("email") String email, @Query("password") String password);

    @POST("account/register")
    Call<Account> registerAccount(@Query("name") String name, @Query("email") String email, @Query("password") String password);

    @POST("account/{id}/registerRenter")
    Call<Account> registerRenter(@Path("id") int id, @Query("username") String username, @Query("address") String address, @Query("phoneNumber") String phoneNumber);

    @POST("room/create")
    Call<Room> create(@Query("accountId") int accountId,
                      @Query("name") String name,
                      @Query("size") int size,
                      @Query("price") int price,
                      @Query("facility") ArrayList<Facility> facility,
                      @Query("city") City city,
                      @Query("address") String address,
                      @Query("bedType") BedType bedType);

    @POST("account/{id}/topUp")
    Call<Boolean> topUp(@Path("id") int id, @Query("balance") double balance);

    @POST("payment/create")
    Call<Payment> create(@Query("buyerId") int buyerId,
                         @Query("renterId") int renterId,
                         @Query("roomId") int roomid,
                         @Query("from") String from,
                         @Query("to") String to);

    @POST("payment/{id}/cancel")
    Call<Boolean> cancel(@Path("id") int id);

    @POST("payment/{id}/accept")
    Call<Boolean> accept(@Path("id") int id);
}
