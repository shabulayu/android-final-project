package com.example.finalproject.Dictionary;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.finalproject.R;

/**
 * This is the main class for dictionary. When the user start searching words, it will jump to the "definition" class.
 * It also present toolbar on this page.
 */
public class Dictionary extends AppCompatActivity {
    String name;
    ImageButton mImageButton;
    EditText et;
    android.content.SharedPreferences sp;
    String s;

    /**
     * Initiation
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);
        Toolbar tBar = (Toolbar)findViewById(R.id.toolbar);
//        setSupportActionBar(tBar);

        mImageButton= (ImageButton)findViewById(R.id.image);
        mImageButton.setOnClickListener(a->{
            et = (EditText) findViewById(R.id.Editxt_search);
//            sp = getSharedPreferences("typed", Context.MODE_PRIVATE);
//            String savedString = sp.getString("typed","");
//            et.setText(savedString);
            name = et.getText().toString();

                if (name == null || name.equals("")) {
                    s="Search box is empty! Please enter a word. ";
                    alertExample();
                }else{
                Intent definition = new Intent(Dictionary.this, Dictionary_definition.class);

                definition.putExtra("typed", name);
//            android.content.SharedPreferences.Editor editor = sp.edit();
//            String whatWasTyped = et.getText().toString();
//            editor.putString("typed", name);

            //write it to disk:
//            editor.commit();

                Toast.makeText(Dictionary.this, "Start searching", Toast.LENGTH_LONG).show();
                startActivity(definition);}

        });

    }

    /**
     * Create menu
     * @param menu
     * @return
     */
    public boolean onCreateOptionsMenu(Menu menu){
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menuitem, menu);
        return true;
    }

    /**
     * Create toolbar functionality
     * @param item
     * @return
     */
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId())
        {
            //what to do when the menu item is selected:
            case R.id.feed:
                Intent feed=new Intent(Dictionary.this,NewsFeed.class);
                startActivity(feed);
                return true;
            case R.id.flight:
                Intent flight=new Intent(Dictionary.this,FlightMainActivity.class);
                startActivity(flight);
                return true;
            case R.id.news:
                Intent news= new Intent(Dictionary.this,activity_newyork.class);
                startActivity(news);
                return true;

            case R.id.action_settings:
                s="Author: Jiaying Huang\n\n"+
                        "Activity version number: 1.0\n\n"+
                        "Instruction: Enter a word in the search box and click the search button.\n";
                alertExample();
                return true;
        }
        return true;
    }


    /**
     * This is the building for the alert pop up window
     */

    public void alertExample() {
        View middle = getLayoutInflater().inflate(R.layout.activity_dictionary_popup, null);
//        EditText etAlert = (EditText)middle.findViewById(R.id.view_edit_text);


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(s)
//                .setPositiveButton("Positive", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        // What to do on Accept
//                        name = etAlert.getText().toString();
//                    }
//                })
                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // What to do on Cancel
                    }
                }).setView(middle);

        builder.create().show();
    }


    @Override
    protected void onPause() {
        super.onPause();

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }


}
