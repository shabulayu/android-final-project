package com.example.finalproject.NewYorkTimes;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalproject.R;

import static android.app.Activity.RESULT_OK;

/**
 * this is a fragment to display the details for an article
 */
public class activity_details_newyork extends Fragment {

    private Bundle dataFromActivity;
    Button save;
    Button delete;
    Toolbar tBar;
    EditText editLabel;
    SharedPreferences sp;
    String userLabel;
    MyDataOpenHelper dbOpener;
    SQLiteDatabase db;
    private long id = -1;

    /**
     * Override the onCreateView method to display the details
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        dataFromActivity = getArguments();

        //get a database:
        dbOpener = new MyDataOpenHelper(getActivity());
        db = dbOpener.getWritableDatabase();

        // Inflate the layout for this fragment
        View result =  inflater.inflate(R.layout.activity_detail_newyork, container, false);

        save = result.findViewById(R.id.saveButton);
        delete = result.findViewById(R.id.deleteB);
        tBar = result.findViewById(R.id.toolbar);
        editLabel = result.findViewById(R.id.label);
        //get the displayed article id
        Log.i("regular id:", ""+ id);
        id = dataFromActivity.getLong("ID");
        Log.i("TEST id:", ""+ id);

        //show the article title
        TextView titleView = (TextView)result.findViewById(R.id.title);
        titleView.setText(dataFromActivity.getString(activity_newyork.ITEM_TITLE)+"\n");

        //show the author name
        TextView authorView = (TextView)result.findViewById(R.id.author);
        authorView.setText(dataFromActivity.getString(activity_newyork.ITEM_AUTHOR));

        //show the article link
        TextView linkView = (TextView)result.findViewById(R.id.link);
        linkView.setText(dataFromActivity.getString(activity_newyork.ITEM_LINK) +"\n");

        //show the article description
        TextView desView = (TextView)result.findViewById(R.id.description);
        desView.setText(dataFromActivity.getString(activity_newyork.ITEM_DESCRIPTION) +"\n");

        // add a click listener for save button:
        save.setOnClickListener( clk -> {

            //check the artice is saved or not
            boolean idSaved = false;
            for(int i = 0; i < saved_list_newyork.tNewsSavd.size(); i++){
                if(id == saved_list_newyork.tNewsSavd.get(i).getId()){
                    idSaved = true;
                    break;
                }
            }
            if(!idSaved) {
                //get the information for each column when you click the save button
                String title = titleView.getText().toString();
                String author = authorView.getText().toString();
                String link = linkView.getText().toString();
                String description = desView.getText().toString();

                //add to the database and get the new ID
                ContentValues newRowValues = new ContentValues();
                //put string message in the MESSAGE column:
                newRowValues.put(MyDataOpenHelper.COL_TITLE, title);
                newRowValues.put(MyDataOpenHelper.COL_AUTHOR, author);
                newRowValues.put(MyDataOpenHelper.COL_LINK, link);
                newRowValues.put(MyDataOpenHelper.COL_DESCRIPTION, description);

                //insert in the database:
                long newId = db.insert(MyDataOpenHelper.TABLE_NAME, null, newRowValues);

                Toast.makeText(getActivity(), getResources().getString(R.string.toastSaved), Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(getActivity(), getResources().getString(R.string.alreadySaved), Toast.LENGTH_LONG).show();
            }

        });

        //back button to go back to the previous page
        delete.setOnClickListener( clk -> {

            boolean idFound = false;

            //loop to find out this article's id is in the saved list or not.
            // if no, alert user that do not need to delete; if yes, then delete it from the saved list
            for(int i = 0; i < saved_list_newyork.tNewsSavd.size(); i++){
                if(id == saved_list_newyork.tNewsSavd.get(i).getId()){
                    idFound = true;
                    break;
                }
            }

            if(idFound){

                Snackbar sb = Snackbar.make(tBar, getResources().getString(R.string.comfirmD), Snackbar.LENGTH_LONG)
                        .setAction(getResources().getString(R.string.yes), e -> {
                            Log.i("Delete this article:" , " id="+dataFromActivity.getInt("arrayID"));

                            saved_list_newyork.tNewsSavd.remove(dataFromActivity.getInt("arrayID"));

                            db.delete(dbOpener.TABLE_NAME, dbOpener.COL_ID + "=?", new String[] {Long.toString(id)});
                            Log.i("Delete database:" , " id="+id);
                            saved_list_newyork.adt.notifyDataSetChanged();
                            getActivity().finish();
                        });
                sb.show();

            }else {
                Toast.makeText(getActivity(), getResources().getString(R.string.notNeed), Toast.LENGTH_LONG).show();
            }
        });
        sp = getActivity().getSharedPreferences("UserLables", Context.MODE_PRIVATE);
        String saveString = sp.getString("label", "");
        editLabel.setText(saveString);

        return result;
    }

    @Override
    public void onPause() {
        super.onPause();
        //get an editor object
        SharedPreferences.Editor editor = sp.edit();
        //save what was typed under the name "kewWord"
        String whatWasTyped = editLabel.getText().toString();
        editor.putString("label", whatWasTyped);
        //write it to disk:
        editor.commit();
    }
}