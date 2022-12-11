package com.satyaJSleepJS;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.satyaJSleepJS.model.Payment;
import com.satyaJSleepJS.request.BaseApiService;
import com.satyaJSleepJS.request.UtilsApi;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentActivity extends AppCompatActivity {
    TextView selectedDate, roomName, roomPrice, accountBalance;
    LinearLayout proceedPayment;
    Button calendar, continuePayment, buttonToPay, buttonToCancel;
    Date dateStart, dateEnd = null;
    Context mContext;
    BaseApiService mApiService;
    Payment currentlyPaying;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        mApiService = UtilsApi.getApiService();
        mContext = this;


        continuePayment = findViewById(R.id.buttonContinuePay);
        continuePayment.setVisibility(View.VISIBLE);
        roomName = findViewById(R.id.textViewRoomNameVal);
        roomName.setText(DetailRoomActivity.detailedRoom.name);
        roomPrice = findViewById(R.id.textViewRoomPriceVal);
        roomPrice.setText("Rp" + String.format("%,.1f", DetailRoomActivity.detailedRoom.price.price));
        accountBalance = findViewById(R.id.textViewAccBalanceVal);
        accountBalance.setText("Rp" + String.format("%,.1f", LoginActivity.accountCurrentlyLogged.balance));
        proceedPayment = findViewById(R.id.linearLayoutPayButtons);
        selectedDate = findViewById(R.id.textViewSelectedDate);
        calendar = findViewById(R.id.buttonPickDates);
        MaterialDatePicker materialDatePicker = MaterialDatePicker.Builder.dateRangePicker().setSelection(Pair.create(MaterialDatePicker.thisMonthInUtcMilliseconds(),MaterialDatePicker.todayInUtcMilliseconds())).build();


        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                materialDatePicker.show(getSupportFragmentManager(), "Tag_picker");
                materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
                    @Override
                    public void onPositiveButtonClick(Object selection) {
                        selectedDate.setText(materialDatePicker.getHeaderText());
                        Pair<Long, Long> dateRange = (Pair<Long, Long>) materialDatePicker.getSelection();
                        dateStart = new Date(dateRange.first);
                        dateEnd = new Date(dateRange.second);
                    }
                });
            }
        });

    continuePayment.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Payment payment = requestCreatePayment();
            proceedPayment.setVisibility(View.VISIBLE);
            calendar.setVisibility(View.INVISIBLE);
        }
    });

    buttonToCancel = findViewById(R.id.buttonCancelPayment);
    buttonToCancel.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            requestPaymentCancel();
            startActivity(new Intent(PaymentActivity.this, DetailRoomActivity.class));
        }
    });

    buttonToPay = findViewById(R.id.buttonPay);
    buttonToPay.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            requestVerifyPayment();
        }
    });

    }

    protected Payment requestCreatePayment(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        mApiService.create(LoginActivity.accountCurrentlyLogged.id, DetailRoomActivity.detailedRoom.accountId,DetailRoomActivity.detailedRoom.id,
                dateFormat.format(dateStart), dateFormat.format(dateEnd)).enqueue(new Callback<Payment>() {
            @Override
            public void onResponse(Call<Payment> call, Response<Payment> response) {
                Toast.makeText(mContext, "Waiting for payment.", Toast.LENGTH_SHORT).show();
                continuePayment.setVisibility(View.INVISIBLE);
                currentlyPaying = response.body();
            }

            @Override
            public void onFailure(Call<Payment> call, Throwable t) {
                Toast.makeText(mContext, "Failed to book the room!", Toast.LENGTH_SHORT).show();
            }
        });
        return null;
    }

    protected void requestPaymentCancel(){
        mApiService.cancel(currentlyPaying.id).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                Toast.makeText(mContext, "Booking cancelled.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(mContext, "Cancellation Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected void requestVerifyPayment(){
        mApiService.accept(currentlyPaying.id).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                Toast.makeText(mContext, "Payment verified!", Toast.LENGTH_SHORT).show();
                proceedPayment.setVisibility(View.INVISIBLE);
                continuePayment.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(mContext, "Payment can't be verified.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}