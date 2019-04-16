package com.example.finalproject.Dictionary;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.finalproject.R;

/**
 * This class is to manipulate fragment settings for phone and tablet. Mainly to specify what to display in the EmptyActivity and the functionality in the EmptyActivity
 */
public class DetailFragment extends Fragment {

    private boolean isTablet;
    private Bundle dataFromActivity;
    private long id;
    private String direction;
    private LayoutInflater inflater;
    private ViewGroup container;
    private Bundle savedInstanceState;

    public void setTablet(boolean tablet) { isTablet = tablet; }

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.inflater = inflater;
        this.container = container;
        this.savedInstanceState = savedInstanceState;
//
        dataFromActivity = getArguments();
       long id = dataFromActivity.getLong(Dictionary.ITEM_ID);

        // Inflate the layout for this fragment
        View result =  inflater.inflate(R.layout.activity_dictionary_fragment_detail, container, false);

        //show the message
        TextView message = (TextView)result.findViewById(R.id.message);
        message.setText("Word Searched: " + dataFromActivity.getString(Dictionary.ITEM_SELECTED));

        //show the id:
        TextView idView = (TextView)result.findViewById(R.id.idText);
        idView.setText("ID=" + id);

        //show the definition
        TextView defView = (TextView)result.findViewById(R.id.definition);
        defView.setText("Definition:\n" + dataFromActivity.getString(Dictionary.ITEM_DEF));

        // get the delete button, and add a click listener:
        Button deleteButton = (Button)result.findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener( clk -> {

            if(isTablet) { //both the list and details are on the screen:
                Dictionary parent = (Dictionary)getActivity();
                parent.deleteMessageId((int)id); //this deletes the item and updates the list


                //now remove the fragment since you deleted it from the database:
                // this is the object to be removed, so remove(this):
                parent.getSupportFragmentManager().beginTransaction().remove(this).commit();
            }
            //for Phone:
            else //You are only looking at the details, you need to go back to the previous list page
            {
                EmptyActivity parent = (EmptyActivity)getActivity();
//                parent.deleteMessageId((long)id);
//                parent.getSupportFragmentManager().beginTransaction().remove(this).commit();
                Intent backToFragmentExample = new Intent();
                backToFragmentExample.putExtra("id",id );

                parent.setResult(Activity.RESULT_OK, backToFragmentExample); //send data back to FragmentExample in onActivityResult()
                parent.finish(); //go back
            }
            message.setText("");
            idView.setText("");
            defView.setText("");
        });
        return result;
    }
}
