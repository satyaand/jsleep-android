package com.satyaJSleepJS;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.satyaJSleepJS.model.Account;
import com.satyaJSleepJS.model.Room;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView lv;
    public ArrayList<Room> rooms = new ArrayList<>();
    public ArrayAdapter<Room> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = findViewById(R.id.list_view);
        getJsonDataUsingGson();
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, rooms);
        lv.setAdapter(arrayAdapter);
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
        }
        return super.onOptionsItemSelected(item);
    }

    private void getJsonDataUsingGson(){
        AssetManager assetManager = getAssets();
        try {
            InputStream inputStream = assetManager.open("randomRoomList.json");
            Gson gson = new Gson();
            Reader reader = new InputStreamReader(inputStream);
            rooms = gson.fromJson(reader, new TypeToken<List<Room>>(){}.getType());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}