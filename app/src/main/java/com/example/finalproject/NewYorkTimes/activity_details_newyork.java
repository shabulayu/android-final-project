package com.example.finalproject.NewYorkTimes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.finalproject.R;

public class activity_details_newyork extends Fragment {

    private Bundle dataFromActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        dataFromActivity = getArguments();

        // Inflate the layout for this fragment
        View result =  inflater.inflate(R.layout.activity_detail_newyork, container, false);

        //show the article title
        TextView title = (TextView)result.findViewById(R.id.title);
        title.setText(dataFromActivity.getString("Title: " + activity_newyork.ITEM_TITLE));

        //show the author name
        TextView authorView = (TextView)result.findViewById(R.id.author);
        authorView.setText(dataFromActivity.getString("Author: " + activity_newyork.ITEM_AUTHOR));

        //show the article link
        TextView linkView = (TextView)result.findViewById(R.id.link);
        linkView.setText(dataFromActivity.getString("Article Link: " + activity_newyork.ITEM_LINK));

        //show the article description
        TextView desView = (TextView)result.findViewById(R.id.description);
        desView.setText(dataFromActivity.getString("Article Description: " + activity_newyork.ITEM_DESCRIPTION));

        // get the delete button, and add a click listener:
        Button deleteButton = (Button)result.findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener( clk -> {

             //You are only looking at the details, you need to go back to the previous list page

            // EntityActivity parent = (EntityActivity) getActivity();
            Intent backTOChat = new Intent();
            //backTOChat.putExtra(activity_newyork.ITEM_, id );
            getActivity().setResult(Activity.RESULT_OK, backTOChat); //send data back to FragmentExample in onActivityResult()
            getActivity().finish(); //go back

        });
        return result;
    }

}
