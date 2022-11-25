package com.satyaJSleepJS;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.satyaJSleepJS.model.Account;
import com.satyaJSleepJS.request.BaseApiService;
import com.satyaJSleepJS.request.UtilsApi;

import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AboutMe extends AppCompatActivity {
    TextView name, email, balance;
    BaseApiService mApiService;
    EditText renterNameVal, renterPhoneVal, renterAddressVal;
    Context mContext;
    LinearLayout layRegister, layRenterName, layRenterAddress, layRenterPhone;
    Button regRenterButton, cancelRentButton, regButton;
    TextView regName, regAddress, regNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApiService = UtilsApi.getApiService();
        mContext = this;
        layRegister = (LinearLayout) findViewById(R.id.LayoutRegister);

        setContentView(R.layout.activity_about_me);
        if(LoginActivity.accountCurrentlyLogged.renter == null){
            regButton = findViewById(R.id.buttonStartRegisterRenter);
            regButton.setVisibility(View.VISIBLE);
            regButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    regButton.setVisibility(View.INVISIBLE);
                    layRegister = findViewById(R.id.LayoutRegister);
                    layRegister.setVisibility(View.VISIBLE);
                    renterNameVal = findViewById(R.id.editTextRenterName);
                    renterAddressVal = findViewById(R.id.editTextRenterAddress);
                    renterPhoneVal = findViewById(R.id.editTextRenterPhoneNumber);
                    regRenterButton = findViewById(R.id.buttonRegisterRenter);
                    regRenterButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Account account = requestRegRenter();
                        }
                    });
                    cancelRentButton = findViewById(R.id.buttonCancelRegister);
                    cancelRentButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            layRegister.setVisibility(View.INVISIBLE);
                            regButton.setVisibility(View.VISIBLE);
                        }
                    });
                }
            });
        } else {
            regName = findViewById(R.id.textRenterNameValue);
            regName.setText(LoginActivity.accountCurrentlyLogged.renter.username);
            regAddress = findViewById(R.id.textRenterAddressValue);
            regAddress.setText(LoginActivity.accountCurrentlyLogged.renter.address);
            regNumber = findViewById(R.id.textRenterPhoneValue);
            regNumber.setText(LoginActivity.accountCurrentlyLogged.renter.phoneNumber);
            layRenterName = findViewById(R.id.linearRenterName);
            layRenterName.setVisibility(View.VISIBLE);
            layRenterAddress = findViewById(R.id.linearRenterAddress);
            layRenterAddress.setVisibility(View.VISIBLE);
            layRenterPhone = findViewById(R.id.linearRenterPhoneNumber);
            layRenterPhone.setVisibility(View.VISIBLE);
        }
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

    protected Account requestRegRenter(){
        mApiService.registerRenter(LoginActivity.accountCurrentlyLogged.id, renterNameVal.getText().toString(),
                renterAddressVal.getText().toString(), renterPhoneVal.getText().toString()).enqueue(new Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                if(response.isSuccessful()){
                    layRegister.setVisibility(View.INVISIBLE);
                    Toast.makeText(mContext, "Register success, please refresh the menu!", Toast.LENGTH_SHORT);
                }
            }

            @Override
            public void onFailure(Call<Account> call, Throwable t) {
                Toast.makeText(mContext, "Register Renter Unsuccessful, please try again!", Toast.LENGTH_SHORT);
            }
        });
        return null;
    }
}