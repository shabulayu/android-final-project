package com.example.finalproject.NewYorkTimes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.example.finalproject.R;

public class activity_details_newyork extends Fragment {

    private Bundle dataFromActivity;
    Button save;
    Button delete;
    Button back;
    Toolbar tBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        dataFromActivity = getArguments();

        // Inflate the layout for this fragment
        View result =  inflater.inflate(R.layout.activity_detail_newyork, container, false);

        save = result.findViewById(R.id.saveButton);
        delete = result.findViewById(R.id.deleteButton);
        back = result.findViewById(R.id.snackTest);
        tBar = result.findViewById(R.id.toolbar);

        //show the article title
        TextView title = (TextView)result.findViewById(R.id.title);
        title.setText(dataFromActivity.getString(activity_newyork.ITEM_TITLE)+"\n");

        //show the author name
        TextView authorView = (TextView)result.findViewById(R.id.author);
        authorView.setText(dataFromActivity.getString(activity_newyork.ITEM_AUTHOR));

        //show the article link
        TextView linkView = (TextView)result.findViewById(R.id.link);
        linkView.setText(dataFromActivity.getString(activity_newyork.ITEM_LINK) +"\n");

        //show the article description
        TextView desView = (TextView)result.findViewById(R.id.description);
        desView.setText(dataFromActivity.getString(activity_newyork.ITEM_DESCRIPTION) +"\n");

        // get the delete button, and add a click listener:
        save.setOnClickListener( clk -> {

            Toast.makeText(getActivity(), "Saved", Toast.LENGTH_LONG).show();

        });

        back.setOnClickListener( clk -> {

            Snackbar sb = Snackbar.make(tBar, "Go Back?", Snackbar.LENGTH_LONG)
                    .setAction("Yes", e -> {
                        getActivity().finish();
                    });
            sb.show();
        });
        return result;
    }

}
