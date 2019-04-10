package com.example.finalproject.NewsFeed;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.finalproject.R;

/**
 * when user use phone
 */
public class NewsEmpty extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_empty);
        Bundle dataToPass = getIntent().getExtras();

        NewsSavedFragment dFragment = new NewsSavedFragment();
        dFragment.setArguments( dataToPass ); //pass data to the the fragment
        dFragment.setTablet(false); //tell the Fragment that it's on a phone.
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragmentLocation, dFragment)
                .addToBackStack("AnyName")
                .commit();
    }
}
