package com.example.finalproject.Flight;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalproject.Flight.model.Flight;
import com.example.finalproject.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.example.finalproject.Flight.FlightDatabaseHelper.TABLE_NAME;

public class FlightSavedFragment extends Fragment {

    private List<Flight> flights;
    private ListView listView;
    private SQLiteDatabase db;
    private FlightDatabaseHelper dbOpener;
    private Button deleteButton;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.flight_saved, container, false);

        flights = new ArrayList<>();

        listView = view.findViewById(R.id.savedFlight);
        deleteButton = view.findViewById(R.id.backButton);

        //get a database
        dbOpener = new FlightDatabaseHelper(getActivity().getApplicationContext());
        db = dbOpener.getWritableDatabase();
        final ChatAdapter flightAdapter = new ChatAdapter(getActivity().getApplicationContext(), 0);


        //query all the results from database
        String[] columns = {FlightDatabaseHelper.COL_ID, FlightDatabaseHelper.COL_Location, FlightDatabaseHelper.COL_Altitude, FlightDatabaseHelper.COL_Speed, FlightDatabaseHelper.COL_Status, FlightDatabaseHelper.COL_IataNumber};
        Cursor results = db.query(false, TABLE_NAME, columns, null, null, null, null, null, null);

        //find tge column indices
        int idColIndex = results.getColumnIndex(FlightDatabaseHelper.COL_ID);
        int locationColIndex = results.getColumnIndex(FlightDatabaseHelper.COL_Location);
        int altitudeColIndex = results.getColumnIndex(FlightDatabaseHelper.COL_Altitude);
        int speedColIndex = results.getColumnIndex(FlightDatabaseHelper.COL_Speed);
        int statusColIndex = results.getColumnIndex(FlightDatabaseHelper.COL_Status);
        int iataNumberColIndex = results.getColumnIndex(FlightDatabaseHelper.COL_IataNumber);

        //iterate over the results, return true if there is a next item:
        while (results.moveToNext()) {
            String location = results.getString(locationColIndex);
            String altitude = results.getString(altitudeColIndex);
            String speed = results.getString(speedColIndex);
            String status = results.getString(statusColIndex);
            String iataNumber = results.getString(iataNumberColIndex);
            results.getLong(idColIndex);

            flights.add(new Flight(location, altitude, speed, status, iataNumber));

            listView.setAdapter(flightAdapter);

        }
        flightAdapter.notifyDataSetChanged();

        deleteButton.setOnClickListener(v -> {
            getActivity().finish();
        });

        listView.setOnItemClickListener((parent, view1, position, id) -> {
            Intent intent = new Intent(this.getActivity(), FlightDetailActivity.class);
            Flight flight = flightAdapter.getItem(position);
            intent.putExtra("location", flight.getLocation());
            intent.putExtra("speed", flight.getSpeed());
            intent.putExtra("altitude", flight.getAltitude());
            intent.putExtra("status", flight.getStatus());
            intent.putExtra("iataNumber", flight.getIataNumber());
            startActivity(intent);
        });

        listView.setOnItemLongClickListener((parent, view1, position, id) -> {
            db.delete(FlightDatabaseHelper.TABLE_NAME,
                    FlightDatabaseHelper.COL_IataNumber + " = ?", new String[]{Objects.requireNonNull(flightAdapter.getItem(position)).getIataNumber()});
            flights.remove(position);
            Toast.makeText(this.getActivity(), "Flight deleted", Toast.LENGTH_SHORT).show();
            flightAdapter.notifyDataSetChanged();
            return true;
        });

        return view;
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
            LayoutInflater inflater = FlightSavedFragment.this.getLayoutInflater();
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
