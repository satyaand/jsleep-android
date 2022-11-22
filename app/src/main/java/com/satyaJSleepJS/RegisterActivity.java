package com.satyaJSleepJS;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.satyaJSleepJS.model.Account;
import com.satyaJSleepJS.request.BaseApiService;
import com.satyaJSleepJS.request.UtilsApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Query;

public class RegisterActivity extends AppCompatActivity {
    BaseApiService mApiService;
    EditText name, email, password;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mApiService = UtilsApi.getApiService();
        mContext = this;
        name = findViewById(R.id.editTextName);
        email = findViewById(R.id.editTextEmail);
        password = findViewById(R.id.editTextPassword);
        Button regButton = findViewById(R.id.buttonRegistration);
        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Account account = requestRegister();
            }
        });
    }

    protected Account requestRegister(){
        mApiService.registerAccount(name.getText().toString(), email.getText().toString(), password.getText().toString()).enqueue(new Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                if(response.isSuccessful() && response.body() != null) startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }

            @Override
            public void onFailure(Call<Account> call, Throwable t) {
                Toast.makeText(mContext, "Account already registered", Toast.LENGTH_SHORT).show();
            }
        });
        return null;
    }
}