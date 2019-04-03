package com.example.finalproject.Flight;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.example.finalproject.R;

/**
 * author: Danyao Wang
 * version: 0.0.2
 * description: a new page to show detail information of selected flight
 */
public class FlightDetailActivity extends AppCompatActivity {

    private Button saveButton;


    /**
     * Override the onCreate() to create the activity, including initialize properties and
     * get values to different attributes from the previous page
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flight_detail);

        TextView LocationTextView = findViewById(R.id.LocationTextView);
        TextView speedTextView = findViewById(R.id.speedTextView);
        TextView altitudeTextView = findViewById(R.id.altitudeTextView);
        TextView statusTextView = findViewById(R.id.statusTextView);
        saveButton = findViewById(R.id.saveButton);


        LocationTextView.setText(getIntent().getStringExtra("location"));
        speedTextView.setText("Speed: " + String.valueOf(getIntent().getDoubleExtra("speed", 0)));
        altitudeTextView.setText("Altitude: " + String.valueOf(getIntent().getDoubleExtra("altitude", 0)));
        statusTextView.setText("Status: " + getIntent().getStringExtra("status"));



        /**
         * click on save button, show up a toast to tell the user successfully saved the selected flight
         */
        saveButton.setOnClickListener(v->{

            Toast.makeText(this, "Successfully saved", Toast.LENGTH_SHORT).show();

        });

    }


}
