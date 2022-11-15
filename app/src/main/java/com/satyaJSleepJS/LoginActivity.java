package com.satyaJSleepJS;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        TextView register = findViewById(R.id.textViewRegisterNow);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent move = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(move);
            }
        });

        TextView logged = findViewById(R.id.loginButton);
        logged.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent enter = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(enter);
            }
        });
    }
}