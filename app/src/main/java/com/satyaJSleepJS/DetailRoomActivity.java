package com.satyaJSleepJS;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.text.Html;
import android.widget.Toast;

import com.satyaJSleepJS.model.Facility;
import com.satyaJSleepJS.model.Room;
import com.satyaJSleepJS.request.BaseApiService;

public class DetailRoomActivity extends AppCompatActivity {
    TextView roomName, bedTypeText, sizeText, priceText, addressText;
    CheckBox ac, refrigerator, wifi, bathtub, balcony, restaurant, swimmingPool, fitnessCenter;
    Button bookRoom;
    Context mContext;
    BaseApiService mApiService;
    public static Room detailedRoom = MainActivity.getSelectedRoom();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_room);
        roomName = findViewById(R.id.textNameValue);
        roomName.setText(detailedRoom.name);
        bedTypeText = findViewById(R.id.textBedTypeValue);
        bedTypeText.setText(detailedRoom.bedType.toString());
        sizeText = findViewById(R.id.textSizeValue);
        sizeText.setText(String.valueOf(detailedRoom.size) + " m" + Html.fromHtml("<sup>2</sup>"));
        priceText = findViewById(R.id.textPriceValue);
        priceText.setText("Rp" + String.format("%,.1f", detailedRoom.price.price));
        addressText = findViewById(R.id.textAddressValue);
        addressText.setText(detailedRoom.address);

        ac = findViewById(R.id.checkBoxAC);
        refrigerator = findViewById(R.id.checkBoxRefrigerator);
        wifi = findViewById(R.id.checkBoxWiFi);
        bathtub = findViewById(R.id.checkBoxBathtub);
        balcony = findViewById(R.id.checkBoxBalcony);
        restaurant = findViewById(R.id.checkBoxRestaurant);
        swimmingPool = findViewById(R.id.checkBoxSwimmingPool);
        fitnessCenter = findViewById(R.id.checkBoxFitnessCenter);

        if(detailedRoom.facility.contains(Facility.AC)) ac.setChecked(true);
        if(detailedRoom.facility.contains(Facility.Refrigerator)) refrigerator.setChecked(true);
        if(detailedRoom.facility.contains(Facility.WiFi)) wifi.setChecked(true);
        if(detailedRoom.facility.contains(Facility.Bathtub)) bathtub.setChecked(true);
        if(detailedRoom.facility.contains(Facility.Balcony)) balcony.setChecked(true);
        if(detailedRoom.facility.contains(Facility.Restaurant)) restaurant.setChecked(true);
        if(detailedRoom.facility.contains(Facility.SwimmingPool)) swimmingPool.setChecked(true);
        if(detailedRoom.facility.contains(Facility.FitnessCenter)) fitnessCenter.setChecked(true);

        bookRoom = findViewById(R.id.buttonRoomBooking);
        bookRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(LoginActivity.accountCurrentlyLogged.balance >= detailedRoom.price.price){
                    startActivity(new Intent(DetailRoomActivity.this, PaymentActivity.class));
                } else {
                    Toast.makeText(mContext, "Not enough balance!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}