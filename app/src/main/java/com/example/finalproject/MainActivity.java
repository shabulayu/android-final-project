package com.example.finalproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.example.finalproject.Dictionary.Dictionary;



import com.example.finalproject.Flight.FlightMainActivity;

import com.example.finalproject.NewYorkTimes.activity_newyork;

import com.example.finalproject.NewsFeed.NewsFeed;
import com.example.finalproject.R;

/**
 * this class displays four buttons. When clicking each button will go to the related activity page
 */

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
            Intent dic=new Intent(this, Dictionary.class);
            startActivity(dic);
        });

        newsBt.setOnClickListener(a->{
            Intent news = new Intent(this, NewsFeed.class);
            startActivity(news);
        });



        flightBt.setOnClickListener(b->{
            Intent flight = new Intent(this, FlightMainActivity.class);
            startActivity(flight);
        });
        //click the New York news button, then go the New York news search page

        searchBt .setOnClickListener(a->{
            Intent newYork = new Intent(this, activity_newyork.class);

            startActivity(newYork);


        });

    }
}
