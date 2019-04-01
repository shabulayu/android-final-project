package com.example.finalproject.Flight;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.finalproject.R;

import java.util.ArrayList;
import java.util.List;

public class FlightSearchActivity extends Activity {

    private List<Flight> flightInfo;
    private ListView listView;
    private Button ButtonSearch;
    private EditText flightEditText;
    private SQLiteDatabase db;
    //private MyDatabaseOpenHelper dbOpener;
    private ChatAdapter flightAdapter;
    private boolean isTablet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flight_mainpage);

        flightAdapter = new ChatAdapter(this,0);
        flightInfo = new ArrayList<>();

        listView = (ListView) findViewById(R.id.fightListView);
        ButtonSearch = (Button) findViewById(R.id.search);
        flightEditText = (EditText) findViewById(R.id.AirportNo);
        //isTablet = findViewById(R.id.fragmentLocation) != null;

        listView.setAdapter(flightAdapter);

        ButtonSearch.setOnClickListener(v ->{

        });
    }






    protected class ChatAdapter extends ArrayAdapter<Flight> {

        public ChatAdapter(@NonNull Context context, int resource) {
            super(context, resource);
        }

        @Override
        public int getCount() {
            return flightInfo.size();
        }

        public View getView(int position, View old, ViewGroup parent) {
            LayoutInflater inflater = FlightSearchActivity.this.getLayoutInflater();
            View newView = null;

            newView = inflater.inflate(R.layout.flight_search, null);
            Flight flight = getItem(position);

            TextView messageTextView = (TextView) newView.findViewById(R.id.AirportNo);
            messageTextView.setText(flight.getAirportCode());

            return newView;
        }


        public long getItemId(int position)
        {
            return getItem(position).getId();
        }


}
}
