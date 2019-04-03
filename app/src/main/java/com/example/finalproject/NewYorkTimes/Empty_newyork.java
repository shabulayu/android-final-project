package com.example.finalproject.NewYorkTimes;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.finalproject.R;

/**
 * this class to handle the fragment
 */
public class Empty_newyork extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_newyork);

        Bundle dataToPass = getIntent().getExtras(); //get the data that was passed from FragmentExample

        activity_details_newyork dFragment = new activity_details_newyork();
        dFragment.setArguments( dataToPass ); //pass data to the the fragment
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentLocation, dFragment)
                .commit();

    }

}
