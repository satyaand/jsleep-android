package com.satyaJSleepJS.request;

import com.satyaJSleepJS.model.Account;
import com.satyaJSleepJS.model.Renter;
import com.satyaJSleepJS.model.Room;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface BaseApiService {
    @GET("account/{id}")
    Call<Account> getAccount (@Path("id") int id);

    @GET("room/{id}")
    Call<Room> getRoom (@Path("id") int accountId);
}
