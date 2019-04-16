package com.example.finalproject.Dictionary;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.finalproject.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * This class present the search functionality for the dictionary. It retrieve the definition of the target term from the given API and stored it to an arraylist.
 * The arraylist will pass to the main dictionary activity and store into database
 */
public class Dictionary_definition extends AppCompatActivity {
    private ProgressBar progressBar;


    String previousTyped;
    String definition = null;
    ArrayList<String> arrayList=new ArrayList<String>();
    private ArrayList<DictionaryList> dicList=new ArrayList<>();
    MyArrayAdapter adt;
    private DictionaryList message;
    private TextView txtInput;
    ListView theList;
//    MyDatabaseOpenHelper dbOpener = new MyDatabaseOpenHelper(this);
//    Cursor results;
    String def="";


    TextView txtV1;
    TextView txtV2;
    public static final int WORD_SAVED = 5338;
    protected SQLiteDatabase db = null;

    /**
     *Initiation for variables.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dictionary_definition);
        txtV1 = findViewById(R.id.word_name);
//        txtV2 = findViewById(R.id.word_definition);
        Intent fromPrevious = getIntent();
        previousTyped = fromPrevious.getExtras().getString("typed");
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        if(arrayList==null){
            arrayList=new ArrayList<>();}
        //get the database:
        MyDatabaseOpenHelper opener = new MyDatabaseOpenHelper(this);
        db =  opener.getWritableDatabase();
        adt= new MyArrayAdapter(arrayList);
        theList = (ListView)findViewById(R.id.the_list);
        theList.setAdapter(adt);

        SearchQuery networkThread = new SearchQuery();
        networkThread.execute(null, null, null);

        Button saveButton = (Button)findViewById(R.id.saveButton);
        saveButton.setOnClickListener( clk -> {
            int i=1;
            for(String s:arrayList){
                def=def+i++ + ". "+s+"\n";
            }

            //Create a ContentValues object for the new values:
            ContentValues newValues = new ContentValues();
            newValues.put(MyDatabaseOpenHelper.COL_MSG, previousTyped);
            newValues.put(MyDatabaseOpenHelper.COL_DEF, def);
            Intent newInfo = new Intent();
            newInfo.putExtra("word", previousTyped);
            newInfo.putExtra("def",def);
            setResult(WORD_SAVED,newInfo);
            finish();

    });



    }



    /**
     * This function takes a word input and start searching its definitions on the provided website
     */
    public class SearchQuery extends AsyncTask<String, Integer, String> {



        @Override
        protected String doInBackground(String... params) {

            try {
                //get the string url:
                // String myUrl = params[0];

                //create the network connection:
                String a=URLEncoder.encode(previousTyped, "UTF-8");
                String s="http://www.dictionaryapi.com/api/v1/references/sd3/xml/" + a + "?key=4556541c-b8ed-4674-9620-b6cba447184f";
                URL url = new URL(s);

                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                InputStream inStream = urlConnection.getInputStream();
//                publishProgress(50);

                //create a pull parser:
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);

                xpp.setInput(inStream, "UTF-8");  //inStream comes from line 46
                //  xpp.nextTag();

                //now loop over the XML:
                while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
                    if (xpp.getEventType() == XmlPullParser.START_TAG) {
                        String tagName = xpp.getName(); //get the name of the starting tag: <tagName>
                        if (tagName.equals("dt")) {
                            definition = xpp.nextText();
//                            message=new DictionaryList(definition,message.getMsg_id());
                            arrayList.add(definition);

                            Log.e("size",String.valueOf(arrayList.size()));
                            Log.e("AsyncTask", "Found definition: " + previousTyped);
                            Thread.sleep(500);
                            publishProgress(100);



                        }
                    }
                    xpp.next(); //advance to next XML event
                }

                urlConnection.disconnect();



            } catch (MalformedURLException e) {
                e.printStackTrace();

            } catch (XmlPullParserException xe) {
                xe.printStackTrace();
            } catch (FileNotFoundException fe) {
                fe.printStackTrace();
            } catch (IOException ie) {
                ie.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onProgressUpdate(Integer... values) {
            Log.i("AsyncTaskExample", "update:" + values[0]);
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(values[0]);

        }

        @Override
        protected void onPostExecute(String s) {
            progressBar.setVisibility(View.INVISIBLE);
            txtV1.setText(previousTyped);
//            txtV2.setText(getString(R.string.definition)+definition);

            ((MyArrayAdapter)adt).notifyDataSetChanged();
            if(arrayList.size()==0||arrayList==null){
                Snackbar sb = Snackbar.make(theList, "Word not Found", Snackbar.LENGTH_LONG)
                        .setAction("Go Back?", e -> finish());
                sb.show();
            }else {
                Snackbar sb = Snackbar.make(theList, "Definition Found", Snackbar.LENGTH_LONG)
                        .setAction("Go Back?", e -> finish());
                sb.show();
            }
        }
    }

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


        public String getItem(int position){
            return arrayList.get(position);
        }

        public View getView(int position, View old, ViewGroup parent)
        {
            View rowView = null;

            //get an object to load a layout:
            LayoutInflater inflater = getLayoutInflater();
            //Get the string to go in row: position
            String toDisplay = getItem(position);
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
