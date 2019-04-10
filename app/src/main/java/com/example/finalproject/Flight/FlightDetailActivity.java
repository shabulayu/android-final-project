package com.example.finalproject.Flight;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
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
    private Button deleteButton;
    private SQLiteDatabase db;
    private long dbId;
    private FlightDatabaseHelper dbOpener;
    private Toolbar tBar;
    private boolean isSaved = false;

    /**
     * Override the onCreate() to create the activity, including initialize properties and
     * get values to different attributes from the previous page
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flight_detail);

        View view = findViewById(R.id.flightDetailView);

        TextView LocationTextView = findViewById(R.id.LocationTextView);
        TextView speedTextView = findViewById(R.id.speedTextView);
        TextView altitudeTextView = findViewById(R.id.altitudeTextView);
        TextView statusTextView = findViewById(R.id.statusTextView);
        saveButton = findViewById(R.id.saveButton);
        deleteButton = findViewById(R.id.backButton);
        deleteButton.setEnabled(false);


        tBar = findViewById(R.id.my_toolbar);
        setSupportActionBar(tBar);


        //get a database
        dbOpener = new FlightDatabaseHelper(this);
        db = dbOpener.getWritableDatabase();


        LocationTextView.setText(getIntent().getStringExtra("location"));
        speedTextView.setText("Speed: " + getIntent().getStringExtra("speed"));
        altitudeTextView.setText("Altitude: " + getIntent().getStringExtra("altitude"));
        statusTextView.setText("Status: " + getIntent().getStringExtra("status"));

        /**
         * click on save button, show up a toast to tell the user successfully saved the selected flight
         */

        saveButton.setOnClickListener(v -> {
            if (!isSaved) {

                //add to the database and get the new ID
                ContentValues newRowValues = new ContentValues();

                //put string message in the message column
                newRowValues.put(FlightDatabaseHelper.COL_Location, getIntent().getStringExtra("location"));
                newRowValues.put(FlightDatabaseHelper.COL_Altitude, getIntent().getDoubleExtra("altitude", 0));
                newRowValues.put(FlightDatabaseHelper.COL_Speed, getIntent().getDoubleExtra("speed", 0));
                newRowValues.put(FlightDatabaseHelper.COL_Status, getIntent().getStringExtra("status"));
                newRowValues.put(FlightDatabaseHelper.COL_IataNumber, getIntent().getStringExtra("iataNumber"));


                //insert to the database
                Log.d("newRowValues", newRowValues.toString());
                dbId = db.insert(FlightDatabaseHelper.TABLE_NAME, null, newRowValues);

                //add the savedFlight to the list
                isSaved = true;

                Toast.makeText(this, "Successfully saved", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(this, "Has been saved", Toast.LENGTH_SHORT).show();
            }

            Snackbar.make(view, "Go to saved list?", Snackbar.LENGTH_LONG)
                    .setAction("Yes", e -> {
                        startActivity(new Intent(FlightDetailActivity.this, FlightSavedActivity.class));
                        isSaved = false;
                    }).show();
            deleteButton.setEnabled(true);
            saveButton.setEnabled(false);
        });

        deleteButton.setOnClickListener(v -> {
            db.delete(FlightDatabaseHelper.TABLE_NAME,
                    FlightDatabaseHelper.COL_ID + " = ?", new String[]{String.valueOf(dbId)});
            Toast.makeText(this, "Has been delete from database", Toast.LENGTH_SHORT).show();
            Snackbar.make(view, "Go to saved list?", Snackbar.LENGTH_LONG)
                    .setAction("Yes", e -> {
                        startActivity(new Intent(FlightDetailActivity.this, FlightSavedActivity.class));
                        isSaved = false;
                    }).show();
            deleteButton.setEnabled(false);
            saveButton.setEnabled(true);
        });

    }


}
