package com.satyaJSleepJS;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.satyaJSleepJS.model.Account;
import com.satyaJSleepJS.request.BaseApiService;
import com.satyaJSleepJS.request.UtilsApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    BaseApiService mApiService;
    EditText username, password;
    Context mContext;
    public static Account accountCurrentlyLogged;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mApiService = UtilsApi.getApiService();
        mContext = this;
        Button logonButton = findViewById(R.id.buttonEnter);
        TextView registerClick = findViewById(R.id.textRegisterClick);
        username = findViewById(R.id.inputEmail);
        password = findViewById(R.id.inputPassword);
        logonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Account account = requestLogin();
                // Account account = requestAccount();
                // startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
        });
        registerClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }
    public void toastMessage(String msg){
        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
        toast.show();
    }

    public void logButtonClicked(View view){
        toastMessage("Login Confirmed!");
    }

    protected Account requestAccount(){
        mApiService.getAccount(0).enqueue(new Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                if(response.isSuccessful()){
                    Account account;
                    account = response.body();
                    System.out.println(account.toString());
                }
            }

            @Override
            public void onFailure(Call<Account> call, Throwable t) {
                Toast.makeText(mContext, "no Account id=0", Toast.LENGTH_SHORT).show();
            }
        });
        return null;
    }

    protected Account requestLogin(){
        mApiService.loginAccount(username.getText().toString(), password.getText().toString()).enqueue(new Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                if(response.isSuccessful()){
                    accountCurrentlyLogged = response.body();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                }
            }

            @Override
            public void onFailure(Call<Account> call, Throwable t) {
                Toast.makeText(mContext, "Incorrect email/password combination", Toast.LENGTH_SHORT).show();
            }
        });
        return null;
    }
}