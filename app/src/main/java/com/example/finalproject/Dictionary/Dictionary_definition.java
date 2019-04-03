package com.example.finalproject.Dictionary;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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
 * This class present the search functionality for the dictionary
 */
public class Dictionary_definition extends AppCompatActivity {
    private ProgressBar progressBar;


    String previousTyped;
    String definition = null;
    ArrayList<String> arrayList=new ArrayList<String>();
    MyArrayAdapter adt;
    private DictionaryList message;
    private TextView txtInput;
    ListView theList;

    TextView txtV1;
    TextView txtV2;

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

        adt= new MyArrayAdapter(arrayList);
        theList = (ListView)findViewById(R.id.the_list);
        theList.setAdapter(adt);

        SearchQuery networkThread = new SearchQuery();
        networkThread.execute(null, null, null);


    }

//    public void alertExample() {
//        View middle = getLayoutInflater().inflate(R.layout.activity_dictionary_popup, null);
////        EditText etAlert = (EditText)middle.findViewById(R.id.view_edit_text);
//
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setMessage("The search box was not found! Please enter a new word. ")
//                .setPositiveButton("Positive", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        // What to do on Accept
//                        previousTyped = etAlert.getText().toString();
//                    }
//                })
//                .setNegativeButton("Negative", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        finish();// What to do on Cancel
//                    }
//                }).setView(middle);
//
//        builder.create().show();
//    }

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
//                while(urlConnection==null){
//                    alertExample();
//                    url = new URL("https://www.dictionaryapi.com/api/v1/references/sd3/xml/" + previousTyped + "?key=4556541c-b8ed-4674-9620-b6cba447184f");
//                    urlConnection = (HttpURLConnection) url.openConnection();
//                }
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
                            Thread.sleep(1000);
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

            Snackbar sb = Snackbar.make(theList, "Definition Found", Snackbar.LENGTH_LONG)
                    .setAction("Go Back?", e -> finish());
            sb.show();
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
