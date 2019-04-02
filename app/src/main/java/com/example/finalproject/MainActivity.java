package com.example.finalproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.example.finalproject.NewYorkTimes.activity_newyork;
import com.example.finalproject.NewsFeed.NewsFeed;

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
        Intent news = new Intent(this, NewsFeed.class);
        newsBt .setOnClickListener(a->{
            startActivity(news);
        });

        //click the New York news button, then go the New York news search page
        Intent newYork = new Intent(this, activity_newyork.class);
        searchBt .setOnClickListener(a->{
            startActivity(newYork);
        });
    }
}
