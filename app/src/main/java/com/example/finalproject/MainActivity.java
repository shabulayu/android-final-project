package com.example.finalproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

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
    }
}
