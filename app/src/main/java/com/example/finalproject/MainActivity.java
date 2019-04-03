package com.example.finalproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.example.finalproject.Dictionary.Dictionary;

public class MainActivity extends AppCompatActivity {

    Button dicBt;
    Button newsBt;
    Button flightBt;
    Button searchBt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dicBt = findViewById(R.id.dicBt);
        newsBt = findViewById(R.id.newsBt);
        flightBt = findViewById(R.id.flightBt);
        searchBt = findViewById(R.id.searchBt);

        dicBt.setOnClickListener(a -> {
            Intent dic=new Intent(MainActivity.this, Dictionary.class);
            startActivity(dic);
        });
    }
}
