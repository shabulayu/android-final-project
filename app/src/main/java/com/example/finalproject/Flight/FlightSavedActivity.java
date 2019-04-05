package com.example.finalproject.Flight;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.finalproject.Flight.model.Flight;
import com.example.finalproject.R;

import java.util.ArrayList;
import java.util.List;

public class FlightSavedActivity extends AppCompatActivity {

    private List<Flight> flights;
    private ListView listView;
    private SQLiteDatabase db;
    private FlightDatabaseHelper dbOpener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flight_saved);

        flights = new ArrayList<>();

        listView = findViewById(R.id.savedFlight);

        //get a database
        dbOpener = new FlightDatabaseHelper(this);
        db = dbOpener.getWritableDatabase();


        //query all the results from database
        String[] columns = {FlightDatabaseHelper.COL_ID,FlightDatabaseHelper.COL_Location,  FlightDatabaseHelper.COL_Altitude, FlightDatabaseHelper.COL_Speed, FlightDatabaseHelper.COL_Status, FlightDatabaseHelper.COL_IataNumber};
        Cursor results = db.query(false, FlightDatabaseHelper.TABLE_NAME,columns, null, null, null, null, null, null);

        //find tge column indices
        int idColIndex = results.getColumnIndex(FlightDatabaseHelper.COL_ID);
        int locationColIndex = results.getColumnIndex(FlightDatabaseHelper.COL_Location);
        int altitudeColIndex = results.getColumnIndex(FlightDatabaseHelper.COL_Altitude);
        int speedColIndex = results.getColumnIndex(FlightDatabaseHelper.COL_Speed);
        int statusColIndex = results.getColumnIndex(FlightDatabaseHelper.COL_Status);
        int iataNumberColIndex = results.getColumnIndex(FlightDatabaseHelper.COL_IataNumber);

        //iterate over the results, return true if there is a next item:
        while(results.moveToNext()){
            String location = results.getString(locationColIndex);
            String altitude = results.getString(altitudeColIndex);
            String speed = results.getString(speedColIndex);
            String status = results.getString(statusColIndex);
            String iataNumber = results.getString(iataNumberColIndex);
            long id = results.getLong(idColIndex);

            flights.add(new Flight(Double.parseDouble(location),Double.parseDouble(altitude),Double.parseDouble(speed),status, iataNumber));

            final ChatAdapter flightAdapter = new ChatAdapter(this,0);
            listView.setAdapter(flightAdapter);

        }


    }

    protected class ChatAdapter extends ArrayAdapter<Flight> {

        public ChatAdapter(@NonNull Context context, int resource) {
            super(context, resource);
        }

        @Override
        public int getCount() {
            return flights.size();
        }

        public Flight getItem(int position) {
            return flights.get(position);
        }


        public View getView(int position, View old, ViewGroup parent) {
            LayoutInflater inflater = FlightSavedActivity.this.getLayoutInflater();
            View newView = inflater.inflate(R.layout.flight_search, null);
            Flight flight = getItem(position);

            TextView searchFlight = newView.findViewById(R.id.searchFlight);
            TextView flightStatus = newView.findViewById(R.id.flightStatus);
            assert flight != null;
            searchFlight.setText(flight.getIataNumber());
            flightStatus.setText(flight.getStatus());

            return newView;

        }
    }

}
