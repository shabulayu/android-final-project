package com.example.finalproject.Dictionary;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalproject.Flight.FlightMainActivity;
import com.example.finalproject.NewYorkTimes.activity_newyork;
import com.example.finalproject.NewsFeed.NewsFeed;
import com.example.finalproject.R;


import java.util.ArrayList;

import static com.example.finalproject.Dictionary.Dictionary_definition.WORD_SAVED;

/**
 * This is the main class for dictionary. When the user start searching words, it will jump to the "definition" class.
 * It also present toolbar on this page.
 */
public class Dictionary extends AppCompatActivity {
    String name;
    DictionaryList DL;
    ImageButton mImageButton;
    private ArrayList<DictionaryList> arrayList=new ArrayList<>();
    EditText et;
    android.content.SharedPreferences sp;
    String s;
    MyDatabaseOpenHelper dbOpener = new MyDatabaseOpenHelper(this);

    Cursor results;
    ListView theList;
    private TextView txtInput;
    MyArrayAdapter adt;
    public static final String ITEM_SELECTED = "ITEM";
    public static final String ITEM_POSITION = "POSITION";
    public static final String ITEM_ID = "ID";
    public static final String ITEM_DEF = "DEFINITION";
    String def=null;
    SQLiteDatabase db;
    private static int ACTIVITY_VIEW_save = 33;
    private static int ACTIVITY_VIEW_delete = 55;

    /**
     * Initiation the Toolbar, arrayList and SharedPreferences
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);
        Toolbar tBar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(tBar);
        boolean isTablet = findViewById(R.id.fragmentLocation) != null;
        if(arrayList==null){
            arrayList=new ArrayList<>();}
        adt= new MyArrayAdapter(arrayList);
        theList = (ListView)findViewById(R.id.the_list);
        sp = getSharedPreferences("typed", Context.MODE_PRIVATE);
        String savedString = sp.getString("typed","");
        et = (EditText) findViewById(R.id.Editxt_search);
        et.setText(savedString);



        db = dbOpener.getWritableDatabase();
        String [] columns = {MyDatabaseOpenHelper.COL_MSG,MyDatabaseOpenHelper.COL_ID,MyDatabaseOpenHelper.COL_DEF};
        results = db.query(false, MyDatabaseOpenHelper.TABLE_NAME, columns, null, null, null, null, null, null);

        int msgColumnIndex = results.getColumnIndex(MyDatabaseOpenHelper.COL_MSG);
        int idColIndex = results.getColumnIndex(MyDatabaseOpenHelper.COL_ID);
        int defColIndex = results.getColumnIndex(MyDatabaseOpenHelper.COL_DEF);
        while(results.moveToNext())
        {
            String msg= results.getString(msgColumnIndex);

            long id = results.getLong(idColIndex);
            String def=results.getString(defColIndex );

            arrayList.add(new DictionaryList(msg,id,def));

        }


        theList.setAdapter(adt);

        //Start searching
        mImageButton= (ImageButton)findViewById(R.id.image);
        mImageButton.setOnClickListener(a->{
            et = (EditText) findViewById(R.id.Editxt_search);

            name = et.getText().toString();

            //pop up an alert if search box empty
                if (name == null || name.equals("")) {
                    s="Search box is empty! Please enter a word. ";
                    alertExample();
                }else{
                    ((MyArrayAdapter)adt).notifyDataSetChanged();
                Intent definition = new Intent(Dictionary.this, Dictionary_definition.class);

                definition.putExtra("typed", name);

            android.content.SharedPreferences.Editor editor = sp.edit();
            String whatWasTyped = et.getText().toString();
            editor.putString("typed", name);

//            write it to disk:
            editor.commit();

                Toast.makeText(Dictionary.this, "Start searching", Toast.LENGTH_LONG).show();
                startActivityForResult(definition,ACTIVITY_VIEW_save);

                }


        });

        //click items on the listview
        theList.setOnItemClickListener(( parent,  item,  position,  id) -> {

            Bundle dataToPass = new Bundle();
            dataToPass.putString(ITEM_SELECTED, arrayList.get(position).toString());
            //arrayList.get(position).getMsg_id()
            dataToPass.putLong(ITEM_ID,position);
            dataToPass.putString(ITEM_DEF,arrayList.get(position).getStr());
            dataToPass.putInt(ITEM_POSITION, position);


            if(isTablet)
            {
                DetailFragment dFragment = new DetailFragment(); //add a DetailFragment
                dFragment.setArguments( dataToPass ); //pass it a bundle for information
                dFragment.setTablet(true);  //tell the fragment if it's running on a tablet or not
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.fragmentLocation, dFragment) //Add the fragment in FrameLayout
                        .addToBackStack("AnyName") //make the back button undo the transaction
                        .commit(); //actually load the fragment.
            }
            else //isPhone
            {
                Intent nextActivity = new Intent(Dictionary.this, EmptyActivity.class);
                nextActivity.putExtras(dataToPass); //send data to next activity
                startActivityForResult(nextActivity,ACTIVITY_VIEW_delete); //make the transition
            }
        });
        ((MyArrayAdapter)adt).notifyDataSetChanged();
    }
    public void printCursor( Cursor c){

        Log.e("number of columns","Total_cols: "+c.getColumnCount());
        c.moveToFirst();
        for(int i=0;i<c.getColumnCount();i++) {
            Log.e("name of columns", "column_names: " + c.getColumnName(i));
            c.moveToNext();
        }

        Log.e("number of results","numOfResults"+c.getCount());
        c.moveToFirst();
        while(!c.isAfterLast()){
            Log.e("rows of results", "text: " + c.getString(c.getColumnIndex(MyDatabaseOpenHelper.COL_MSG)));
            c.moveToNext();
        }
    }

    /**
     *
     * @param requestCode  data return from an activity
     * @param resultCode   return for an event
     * @param data
     *
     * This function to manipulate multiple returns from other activities
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //If you're coming back from the view contact activity
        if(requestCode == ACTIVITY_VIEW_save)
        {

            if(resultCode==WORD_SAVED){

                ContentValues newRowValues = new ContentValues();

                newRowValues.put(MyDatabaseOpenHelper.COL_MSG,data.getStringExtra("word"));
                newRowValues.put(MyDatabaseOpenHelper.COL_DEF,data.getStringExtra("def"));
                long newId = db.insert(MyDatabaseOpenHelper.TABLE_NAME, null, newRowValues);
                DictionaryList newSearch=new DictionaryList(data.getStringExtra("word"),newId,data.getStringExtra("def"));
                arrayList.add(newSearch);
                ((MyArrayAdapter)adt).notifyDataSetChanged();
            }
        }
        if(requestCode == ACTIVITY_VIEW_delete){
            if(resultCode == RESULT_OK) //if you hit the delete button instead of back button
            {
                long id = data.getLongExtra("id", 0);
                deleteMessageId((int)id);
            }
        }
    }

    /**
     *
     * @param id locate the position of the item
     *
     * delete an item on the listview and the database
     */
    public void deleteMessageId(int id)
    {

        Log.i("Delete this message:" , " id="+id);
//        final SQLiteDatabase db = dbOpener.getWritableDatabase();
        arrayList.remove(id);
//        db.beginTransaction();
        String str="";
        Cursor c;
        String [] columns = {MyDatabaseOpenHelper.COL_MSG,MyDatabaseOpenHelper.COL_ID,MyDatabaseOpenHelper.COL_DEF};
        c= db.query(false, dbOpener.TABLE_NAME,
                columns,null, null, null, null, null, null);
        if(c.moveToFirst()) {

            for (int i =0; i<id; i++) {
                c.moveToNext();

            }
            str = c.getString(results.getColumnIndex("_id"));
        }
        db.delete(MyDatabaseOpenHelper.TABLE_NAME, "_id =? " ,  new String[] {str});

        ((MyArrayAdapter)adt).notifyDataSetChanged();
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
                Intent feed=new Intent(Dictionary.this, NewsFeed.class);
                startActivity(feed);
                return true;
            case R.id.flight:
                Intent flight=new Intent(Dictionary.this, FlightMainActivity.class);
                startActivity(flight);
                return true;
            case R.id.news:
                Intent news= new Intent(Dictionary.this, activity_newyork.class);
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

    /**
     * The customized arrayAdapter uses to display items on listview
     */
    protected class MyArrayAdapter extends BaseAdapter
    {


        //Keep a reference to the data:
        public MyArrayAdapter(ArrayList originalData)
        {
            arrayList = originalData;
        }



        //Tells the list how many elements to display:
        public int getCount()
        {
            return arrayList.size();
        }


        public DictionaryList getItem(int position){
            return arrayList.get(position);
        }

        public View getView(int position, View old, ViewGroup parent)
        {
            View rowView = null;

            //get an object to load a layout:
            LayoutInflater inflater = getLayoutInflater();
            //Get the string to go in row: position
            String toDisplay = getItem(position).toString();
            //View rowView=inflater.inflate(R.layout.single_row_send, parent, false);

            rowView=inflater.inflate(R.layout.activity_dictionary_singlerow,parent,false);
            txtInput=(TextView)rowView.findViewById(R.id.textOnRow);



            //Set the text of the text view
            txtInput.setText(toDisplay);

            //Return the text view:
            return rowView;
        }


        //Return 0 for now. We will change this when using databases
        @Override
        public long getItemId(int position)
        {
            return 0;
        }
    }

}
