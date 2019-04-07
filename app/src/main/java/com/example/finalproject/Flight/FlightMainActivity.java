package com.example.finalproject.Flight;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.finalproject.Flight.model.Flight;
import com.example.finalproject.NewsFeed.NewsFeed;
import com.example.finalproject.R;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * author: Danyao Wang
 * version: 0.0.2
 * description: Flight Status Tracker Main page
 */
public class FlightMainActivity extends AppCompatActivity {
    private String ACTIVITY_NAME = "FLIGHT_MAIN_ACTIVITY";
    private List<Flight> flights;
    private FlightAdapter flightAdapter;
    private ProgressBar progressBar;
    private EditText editText;
    private SharedPreferences sp;


    /**
     * Override the onCreate() to create the activity, including initialize properties and
     * turn over to a new page to retrieve information
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flight_mainpage);
        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        flightAdapter = new FlightAdapter(this, 0);
        flights = new ArrayList<>();


        Button buttonSearch = findViewById(R.id.search);
        ListView flightlistView = findViewById(R.id.fightListView);
        progressBar = findViewById(R.id.fetchFlightDataProgressBar);

        flightlistView.setAdapter(flightAdapter);

        buttonSearch.setOnClickListener(v -> {

            progressBar.setVisibility(View.VISIBLE);
            new FlightDataQuery().execute();
        });

        flightlistView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(this, FlightDetailActivity.class);
            Flight flight = flightAdapter.getItem(position);
            intent.putExtra("location", "Latitude: " + flight.getLatitude() + "\nLongitude: " + flight.getLongitude());
            intent.putExtra("speed", flight.getSpeed());
            intent.putExtra("altitude", flight.getAltitude());
            intent.putExtra("status", flight.getStatus());
            intent.putExtra("iataNumber", flight.getIataNumber());
            startActivity(intent);
        });

        editText = getWindow().findViewById(R.id.AirportNo);
        sp = getSharedPreferences("Email", Context.MODE_PRIVATE);
        String savedString = sp.getString("ReserveName", "");

        editText.setText(savedString);


    }

    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences.Editor editor = sp.edit();

        String whatWasTyped = editText.getText().toString();
        editor.putString("ReserveName", whatWasTyped);

        editor.commit();
    }

    /**
     * specify the options menu for an activity
     * inflate your menu resource into the Menu provided in the callback
     *
     * @param menu
     */
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.flight_icon, menu);
        return true;
    }


    /**
     * pass in the menu item objected that was clicked
     * inflate your menu resource into the Menu provided in the callback
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //what to do when the menu item is selected:
            case R.id.item1:
                //startActivity(new Intent(FlightMainActivity.this, Merriam _Webster_DictionaryMainActivity.class));
                return true;

            case R.id.item2:
                startActivity(new Intent(FlightMainActivity.this, NewsFeed.class));

                return true;

            case R.id.item3:

                //startActivity(new Intent(FlightMainActivity.this, activity_newyork.class));
                return true;

            case R.id.item4:
                //Show the toast immediately:
                String title = getResources().getString(R.string.flight_instruction_title);
                String instruction = getResources().getString(R.string.flight_help);
                String positive = getResources().getString(R.string.flight_positive);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(FlightMainActivity.this);
                alertDialogBuilder.setTitle(title);
                alertDialogBuilder.setMessage(instruction).setCancelable(false).setPositiveButton(positive, null);

                alertDialogBuilder.create().show();
                return true;


        }
        return true;
    }

    /**
     * An adapter class to adapt to the list view
     */
    protected class FlightAdapter extends ArrayAdapter<Flight> {

        FlightAdapter(@NonNull Context context, int resource) {
            super(context, resource);
        }


        /**
         * returns the number of items
         *
         * @return
         */
        @Override
        public int getCount() {
            return flights.size();
        }

        /**
         * returns an item from a specific position
         *
         * @param position
         * @return
         */
        @Override
        public Flight getItem(int position) {
            return flights.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


        /**
         * return a story title to be shown at row position
         *
         * @param position
         * @param old
         * @param parent
         * @return
         */
        public View getView(int position, View old, ViewGroup parent) {
            LayoutInflater inflater = FlightMainActivity.this.getLayoutInflater();
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


    /**
     * A seperate Task to async flight detail information from website
     */
    private class FlightDataQuery extends AsyncTask<String, Integer, List<Flight>> {


        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected List<Flight> doInBackground(String... strings) {
            URL url = null;
            StringBuilder json = new StringBuilder();
            try {
                url = new URL("http://aviation-edge.com/v2/public/flights?key=1660a6-59a3ce&arrIata=" + editText.getText());
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                while ((inputLine = br.readLine()) != null) {
                    json.append(inputLine);
                }
                br.close();
                flights = new ArrayList<>();
                JsonArray jsonArray = new JsonParser().parse(json.toString()).getAsJsonArray();
                if (jsonArray.size() != 0) {
                    for (JsonElement flightJsonElement : jsonArray) {
                        String latitude = flightJsonElement.getAsJsonObject().get("geography").getAsJsonObject().get("latitude").getAsString();
                        String longitude = flightJsonElement.getAsJsonObject().get("geography").getAsJsonObject().get("longitude").getAsString();
                        publishProgress(0);
                        String altitude = flightJsonElement.getAsJsonObject().get("geography").getAsJsonObject().get("altitude").getAsString();
                        publishProgress(25);
                        String horizontal = flightJsonElement.getAsJsonObject().get("speed").getAsJsonObject().get("horizontal").getAsString();
                        publishProgress(50);
                        String status = flightJsonElement.getAsJsonObject().get("status").getAsString();
                        publishProgress(75);
                        String iataNumber = flightJsonElement.getAsJsonObject().get("flight").getAsJsonObject().get("iataNumber").getAsString();
                        publishProgress(100);
                        Flight flight = new Flight(latitude, longitude, altitude, horizontal, status, iataNumber);
                        flights.add(flight);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar = findViewById(R.id.fetchFlightDataProgressBar);
            progressBar.setVisibility(View.VISIBLE);

            //super.onProgressUpdate(values);
            progressBar.setProgress(values[0]);

        }

        /**
         * Override onPostExecute class to handle when web query task is finished
         *
         * @param s
         */
        @Override
        protected void onPostExecute(List<Flight> s) {
            /*super.onPostExecute(s);
            flightAdapter.notifyDataSetChanged();*/
            progressBar = findViewById(R.id.fetchFlightDataProgressBar);
            progressBar.setVisibility(View.INVISIBLE);
            flightAdapter.notifyDataSetChanged();
        }

    }

}
