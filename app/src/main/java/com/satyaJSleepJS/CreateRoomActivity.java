package com.satyaJSleepJS;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateRoomActivity extends AppCompatActivity {
    Button createR, cancelR;
    ArrayList<Facility> sentFacility = new ArrayList<>();
    BaseApiService mApiService;
    Room roomCreated;
    Context mContext;
    Spinner citySpinner, bedSpinner;
    City spinnerSelectedCity;
    BedType spinnerSelectedBedType;
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
        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinnerSelectedCity = (City) citySpinner.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        bedSpinner = findViewById(R.id.spinnerBedType);
        bedSpinner.setAdapter(new ArrayAdapter<BedType>(mContext, android.R.layout.simple_spinner_dropdown_item, BedType.values()));
        bedSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinnerSelectedBedType = (BedType) bedSpinner.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

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

        List<CheckBox> checkboxes = Arrays.asList(ac, refrigerator, wifi, bathtub, balcony, restaurant, swimmingPool, fitnessCenter);
        createR = findViewById(R.id.buttonCreateRoom);
        createR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(CheckBox cb : checkboxes){
                    if(cb.isChecked()){
                        sentFacility.add(Facility.valueOf(cb.getText().toString()));
                    }
                }
                Room room = requestCreate(spinnerSelectedCity, spinnerSelectedBedType, sentFacility);
                startActivity(new Intent(CreateRoomActivity.this, MainActivity.class));
            }
        });
    }

    protected Room requestCreate(City sentCity, BedType sentBedType, ArrayList<Facility> sentFacility){
        mApiService.create(LoginActivity.accountCurrentlyLogged.id, name.getText().toString(),
                Integer.parseInt(size.getText().toString()), Integer.parseInt(price.getText().toString()),
                sentFacility, sentCity, address.getText().toString(), sentBedType).enqueue(new Callback<Room>() {
            @Override
            public void onResponse(Call<Room> call, Response<Room> response) {
                Toast.makeText(mContext, "Room created!", Toast.LENGTH_SHORT).show();
                System.out.println("Bed: " + sentBedType + " City: " + sentCity);
                roomCreated = response.body();
            }

            @Override
            public void onFailure(Call<Room> call, Throwable t) {
                Toast.makeText(mContext, "Room creation failed. Please try again!", Toast.LENGTH_SHORT).show();
            }
        });
        return null;
    }
}