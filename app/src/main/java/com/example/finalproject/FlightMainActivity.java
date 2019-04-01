package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class FlightMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flight_mainpage);
        Toolbar toolbar = (Toolbar)findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
    }

    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.flight_icon, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            //what to do when the menu item is selected:
            case R.id.item1:
                //startActivity(new Intent(FlightMainActivity.this, Merriam _Webster_DictionaryMainActivity.class));
                return true;

            case R.id.item2:
                //startActivity(new Intent(FlightMainActivity.this, New_FeedMainActivity.class));
                return true;

            case R.id.item3:

                //startActivity(new Intent(FlightMainActivity.this, New York Times Article SearchMainActivity.class));
                return true;

            case R.id.item4:
                //Show the toast immediately:
                String title = getResources().getString(R.string.flight_instruction_title);
                String instruction = getResources().getString(R.string.flight_help);
                String positive = getResources().getString(R.string.flight_positive);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(FlightMainActivity.this);
                alertDialogBuilder.setTitle(title);
                alertDialogBuilder.setMessage(instruction).setCancelable(false).setPositiveButton(positive,null);

                alertDialogBuilder.create().show();
                return true;



        }
        return true;
    }
}
