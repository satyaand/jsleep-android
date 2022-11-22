package com.satyaJSleepJS;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AboutMe extends AppCompatActivity {
    TextView name, email, balance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);
        name = findViewById(R.id.textNameValue);
        email = findViewById(R.id.textEmailValue);
        balance = findViewById(R.id.textBalanceValue);
        name.setText(LoginActivity.accountCurrentlyLogged.name);
        email.setText(LoginActivity.accountCurrentlyLogged.email);
        balance.setText("Rp" + String.valueOf(LoginActivity.accountCurrentlyLogged.balance));
    }

    public void processTopUpToast(View view){
        Toast.makeText(AboutMe.this, "Top Up Successful", Toast.LENGTH_SHORT).show();
    }
}