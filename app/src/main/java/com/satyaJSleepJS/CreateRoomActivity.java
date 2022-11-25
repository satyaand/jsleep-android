package com.satyaJSleepJS;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.satyaJSleepJS.model.BedType;
import com.satyaJSleepJS.model.City;
import com.satyaJSleepJS.model.Facility;
import com.satyaJSleepJS.model.Room;
import com.satyaJSleepJS.request.BaseApiService;
import com.satyaJSleepJS.request.UtilsApi;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateRoomActivity extends AppCompatActivity {
    Button createR, cancelR;
    BaseApiService mApiService;
    Room roomCreated;
    Context mContext;
    Spinner citySpinner, bedSpinner;
    EditText name, price, address, size;
    CheckBox ac, refrigerator, wifi, bathtub, balcony, restaurant, swimmingPool, fitnessCenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_room);
        mApiService = UtilsApi.getApiService();
        mContext = this;

        citySpinner = findViewById(R.id.spinnerCity);
        citySpinner.setAdapter(new ArrayAdapter<City>(mContext, android.R.layout.simple_spinner_dropdown_item,City.values()));

        bedSpinner = findViewById(R.id.spinnerBedType);
        bedSpinner.setAdapter(new ArrayAdapter<BedType>(mContext, android.R.layout.simple_spinner_dropdown_item, BedType.values()));

        cancelR = findViewById(R.id.buttonCancelCreate);
        cancelR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CreateRoomActivity.this, MainActivity.class));
            }
        });

        name = findViewById(R.id.editTextRoomName);
        price = findViewById(R.id.editTextRoomPrice);
        address = findViewById(R.id.editTextRoomAddress);
        size = findViewById(R.id.editTextRoomSize);
        ac = findViewById(R.id.checkBoxAC);
        refrigerator = findViewById(R.id.checkBoxRefrigerator);
        wifi = findViewById(R.id.checkBoxWiFi);
        bathtub = findViewById(R.id.checkBoxBathtub);
        balcony = findViewById(R.id.checkBoxBalcony);
        restaurant = findViewById(R.id.checkBoxRestaurant);
        swimmingPool = findViewById(R.id.checkBoxSwimmingPool);
        fitnessCenter = findViewById(R.id.checkBoxFitnessCenter);
        createR = findViewById(R.id.buttonCreateRoom);
        createR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Room room = requestCreate();
                Toast.makeText(mContext, "Room created!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(CreateRoomActivity.this, MainActivity.class));
            }
        });
    }

    protected Room requestCreate(){
        ArrayList<Facility> sentFacility = new ArrayList<>();
        final City[] sentCity = {null};
        final BedType[] sentBedType = {null};

        if(ac.isChecked()) sentFacility.add(Facility.AC);
        if(refrigerator.isChecked()) sentFacility.add(Facility.Refrigerator);
        if(wifi.isChecked()) sentFacility.add(Facility.WiFi);
        if(bathtub.isChecked()) sentFacility.add(Facility.Bathtub);
        if(balcony.isChecked()) sentFacility.add(Facility.Balcony);
        if(restaurant.isChecked()) sentFacility.add(Facility.Restaurant);
        if(swimmingPool.isChecked()) sentFacility.add(Facility.SwimmingPool);
        if(fitnessCenter.isChecked()) sentFacility.add(Facility.FitnessCenter);

        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                City runSwitch = City.values()[i];
                switch(runSwitch){
                    case BALI:
                        sentCity[0] = City.BALI;
                        break;
                    case BANDUNG:
                        sentCity[0] = City.BANDUNG;
                        break;
                    case SURABAYA:
                        sentCity[0] = City.SURABAYA;
                        break;
                    case JAKARTA:
                        sentCity[0] = City.JAKARTA;
                        break;
                    case SEMARANG:
                        sentCity[0] = City.SEMARANG;
                        break;
                    case MEDAN:
                        sentCity[0] = City.MEDAN;
                        break;
                    case DEPOK:
                        sentCity[0] = City.DEPOK;
                        break;
                    case BEKASI:
                        sentCity[0] = City.BEKASI;
                        break;
                    case LAMPUNG:
                        sentCity[0] = City.LAMPUNG;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(mContext, "Please choose a city", Toast.LENGTH_SHORT).show();
            }
        });

        bedSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                BedType tempBed = BedType.values()[i];
                switch(tempBed){
                    case DOUBLE:
                        sentBedType[0] = BedType.DOUBLE;
                        break;
                    case SINGLE:
                        sentBedType[0] = BedType.SINGLE;
                        break;
                    case QUEEN:
                        sentBedType[0] = BedType.QUEEN;
                        break;
                    case KING:
                        sentBedType[0] = BedType.KING;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(mContext, "Please select a bed type", Toast.LENGTH_SHORT).show();
            }
        });

        mApiService.create(LoginActivity.accountCurrentlyLogged.id, name.getText().toString(),
                Integer.parseInt(size.getText().toString()), Integer.parseInt(price.getText().toString()),
                sentFacility, sentCity[0], address.getText().toString(), sentBedType[0]).enqueue(new Callback<Room>() {
            @Override
            public void onResponse(Call<Room> call, Response<Room> response) {
                roomCreated = response.body();
            }

            @Override
            public void onFailure(Call<Room> call, Throwable t) {

            }
        });
        return null;
    }
}