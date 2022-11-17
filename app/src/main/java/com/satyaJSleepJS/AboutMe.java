package com.satyaJSleepJS;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class AboutMe extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);
    }

    public void processTopUpToast(View view){
        Toast.makeText(AboutMe.this, "Top Up Successful", Toast.LENGTH_SHORT).show();
    }
}