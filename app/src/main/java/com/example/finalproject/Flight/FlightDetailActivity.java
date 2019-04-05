package com.example.finalproject.Flight;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.example.finalproject.Flight.model.Flight;
import com.example.finalproject.R;

import java.util.ArrayList;
import java.util.List;

/**
 * author: Danyao Wang
 * version: 0.0.2
 * description: a new page to show detail information of selected flight
 */
public class FlightDetailActivity extends AppCompatActivity {

    private Button saveButton;
    private SQLiteDatabase db;
    private FlightDatabaseHelper dbOpener;
    private List<Flight> flights;
    private Toolbar tBar;
    private boolean isSaved = false;

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
        flights = new ArrayList<>();


        tBar = (Toolbar)findViewById(R.id.my_toolbar);
        setSupportActionBar(tBar);


        //get a database
        dbOpener = new FlightDatabaseHelper(this);
        db = dbOpener.getWritableDatabase();


        LocationTextView.setText(getIntent().getStringExtra("location"));
        speedTextView.setText("Speed: " + String.valueOf(getIntent().getDoubleExtra("speed", 0)));
        altitudeTextView.setText("Altitude: " + String.valueOf(getIntent().getDoubleExtra("altitude", 0)));
        statusTextView.setText("Status: " + getIntent().getStringExtra("status"));

        /**
         * click on save button, show up a toast to tell the user successfully saved the selected flight
         */

            saveButton.setOnClickListener(v -> {
                if (isSaved) {
                    String location = LocationTextView.getText().toString();
                    String speed = speedTextView.getText().toString();
                    String altitude = altitudeTextView.getText().toString();
                    String status = statusTextView.getText().toString();
                    String iataNumber = getIntent().getStringExtra("iataNumber");

                    //add to the database and get the new ID
                    ContentValues newRowValues = new ContentValues();

                    //put string message in the message column
                    newRowValues.put(FlightDatabaseHelper.COL_Location, location);
                    newRowValues.put(FlightDatabaseHelper.COL_Altitude, location);
                    newRowValues.put(FlightDatabaseHelper.COL_Speed, speed);
                    newRowValues.put(FlightDatabaseHelper.COL_IataNumber, iataNumber);
                    newRowValues.put(FlightDatabaseHelper.COL_Status, status);

                    //insert to the database
                    long newId = db.insert(FlightDatabaseHelper.TABLE_NAME, null, newRowValues);


                    Flight savedFlight = new Flight(Double.parseDouble(location), Double.parseDouble(altitude), Double.parseDouble(speed), status, iataNumber);

                    //add the savedFlight to the list
                    flights.add(savedFlight);
                    isSaved = true;

                    Toast.makeText(this, "Successfully saved", Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(this, "Has been saved", Toast.LENGTH_SHORT).show();
                }

                Snackbar sb = Snackbar.make(tBar, "Go to saved list?", Snackbar.LENGTH_LONG)
                        .setAction("Yes", e -> startActivity(new Intent(FlightDetailActivity.this, FlightSavedActivity.class)));

                sb.show();
            });
    }


}
