package com.satyaJSleepJS.request;

import com.satyaJSleepJS.model.Account;
import com.satyaJSleepJS.model.Renter;
import com.satyaJSleepJS.model.Room;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BaseApiService {
    @GET("account/{id}")
    Call<Account> getAccount (@Path("id") int id);

    @GET("room/{id}")
    Call<Room> getRoom (@Path("id") int accountId);
    
    @POST("account/login")
    Call<Account> loginAccount(@Query("email") String email, @Query("password") String password);

    @POST("account/register")
    Call<Account> registerAccount(@Query("name") String name, @Query("email") String email, @Query("password") String password);
}
