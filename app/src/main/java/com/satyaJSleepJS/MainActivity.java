package com.satyaJSleepJS;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.satyaJSleepJS.model.Room;
import com.satyaJSleepJS.request.BaseApiService;
import com.satyaJSleepJS.request.UtilsApi;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    ListView lv;
    EditText page;
    public static int pageSize = 10;
    public int pageNumber = 1;
    public static ArrayList<Room> rooms = new ArrayList<>();
    public ArrayAdapter<Room> arrayAdapter;
    BaseApiService mApiService;
    Context mContext;
    Button prev, next, go;
    public static int selectedRoomIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mApiService= UtilsApi.getApiService();
        mContext = this;

        requestAllRoom(pageNumber, pageSize);

        page = findViewById(R.id.editTextPageNumber);
        if(page.getText() == null){
            pageNumber = 1;
        } else {
            pageNumber = Integer.parseInt(page.getText().toString());
        }
        prev = findViewById(R.id.buttonPrevPage);
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                --pageNumber;
                if(pageNumber < 1) pageNumber = 1;
                page.setText(Integer.toString(pageNumber));
                requestAllRoom(pageNumber, pageSize);
            }
        });
        next = findViewById(R.id.buttonNextPage);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ++pageNumber;
                page.setText(Integer.toString(pageNumber));
                requestAllRoom(pageNumber, pageSize);
            }
        });
        go = findViewById(R.id.buttonGoPage);
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pageNumber = Integer.parseInt(page.getText().toString());
                requestAllRoom(pageNumber, pageSize);
                page.setText(Integer.toString(pageNumber));
            }
        });

    lv = findViewById(R.id.list_view);
    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            selectedRoomIndex = i;
            Intent in = new Intent(MainActivity.this, DetailRoomActivity.class);
            startActivity(in);
        }
    });
    }

    public static Room getSelectedRoom(){
        return rooms.get(selectedRoomIndex);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu){
        MenuItem create = menu.findItem(R.id.add_button);
        if(LoginActivity.accountCurrentlyLogged.renter == null){
            create.setVisible(false);
        } else {
            create.setVisible(true);
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == R.id.profile_button){
            startActivity(new Intent(MainActivity.this, AboutMe.class));
        } else if(item.getItemId() == R.id.add_button){
            startActivity(new Intent(MainActivity.this, CreateRoomActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    protected void requestAllRoom(int pageNum, int pageSz){
        lv = findViewById(R.id.list_view);
        mApiService.getAllRoom(pageNum - 1, pageSz - 1).enqueue(new Callback<ArrayList<Room>>() {
            @Override
            public void onResponse(Call<ArrayList<Room>> call, Response<ArrayList<Room>> response) {
                rooms = response.body();
                arrayAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_list_item_1, rooms);
                lv.setAdapter(arrayAdapter);
            }

            @Override
            public void onFailure(Call<ArrayList<Room>> call, Throwable t) {
                Toast.makeText(mContext, "Failed to fetch rooms data.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}