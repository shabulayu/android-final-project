package com.example.finalproject.NewYorkTimes;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
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
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalproject.Dictionary.Dictionary;
import com.example.finalproject.Flight.FlightMainActivity;
import com.example.finalproject.NewsFeed.NewsFeed;
import com.example.finalproject.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * This class displays the page which the user can use the key words to search the article from the New York Times website.
 * When click the title can go the detail page
 */
public class activity_newyork extends AppCompatActivity {

    //find the widgets from the layout
    Toolbar tBar;
    SearchView sView;
    ListView listNews;
    ProgressBar pBar;
    View newView;
    ArrayList<TimesNews> tNewsFound = new ArrayList<>();
    MyOwnAdapter adt;
    SharedPreferences sp;
    String searchText;
    public static final String ITEM_TITLE = "TITLE";
    public static final String ITEM_AUTHOR = "AUTHOR";
    public static final String ITEM_LINK = "LINK";
    public static final String ITEM_DESCRIPTION = "ITEM_DESCRIPTION";

    /**
     * this method starts the activity, and display the search results on the list view
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newyork);

        //find the wedgets
        tBar = (Toolbar) findViewById(R.id.toolbar);
        sView = (SearchView) findViewById(R.id.search);
        listNews = (ListView) findViewById(R.id.the_list);
        pBar = (ProgressBar) findViewById(R.id.progressBar);

        //set the tool bar
        setSupportActionBar(tBar);
        //initialize the inner class
        adt = new MyOwnAdapter();

        //search the article then put it in the array list
        SearchView sView = (SearchView)findViewById(R.id.search);
        sView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                NewsQuery news = new NewsQuery();
                tNewsFound.clear();
                if(query.length()==0||query==null){
                    Toast.makeText(activity_newyork.this,"Please enter the key words to search",Toast.LENGTH_LONG).show();
                }else {
                    //set the progress bar
                    pBar.setVisibility(View.VISIBLE);
                    adt.notifyDataSetChanged();
                    news.execute();
                }
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                adt.notifyDataSetChanged();
                return false;
            }  });

        //using an adapter object and send it to the listVIew
        listNews.setAdapter(adt);
        //when the data is displayed, invisible the progress bar

        //when click the list go to the detail page
        listNews.setOnItemClickListener( (list, item, position, id) -> {

            Bundle dataToPass = new Bundle();
            dataToPass.putString(ITEM_TITLE, tNewsFound.get(position).getTitle());
            dataToPass.putString(ITEM_AUTHOR, tNewsFound.get(position).getAuthor());
            dataToPass.putString(ITEM_LINK, tNewsFound.get(position).getLink());
            dataToPass.putString(ITEM_DESCRIPTION, tNewsFound.get(position).getDescription());

            //go to the fragment
            Intent nextActivity = new Intent(activity_newyork.this, Empty_newyork.class);
            nextActivity.putExtras(dataToPass); //send data to next activity
            startActivityForResult(nextActivity, 345); //make the transition


        });
        //save the key word and display it next time when user goes into this page
        sp = getSharedPreferences("KeyValue", Context.MODE_PRIVATE);
        String saveString = sp.getString("kewWord", "");
        sView.setQuery(saveString, false);

        adt.notifyDataSetChanged();

    }

    /**
     * put the key word in to a sharedPreferences object
     */
    @Override
    protected void onPause(){
        super.onPause();

        //get an editor object
        SharedPreferences.Editor editor = sp.edit();
        //save what was typed under the name "kewWord"
        String whatWasTyped = sView.getQuery().toString();
        editor.putString("kewWord", whatWasTyped);
        //write it to disk:
        editor.commit();
    }

    /**
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //coming back from the Profile activity

    }

    /**
     * display the menu
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.times_menu, menu);

        return true;
    }

    /**
     * when click the different menu, implements different functions
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.news:
                //go to the news feed page:
                Intent news = new Intent(this, NewsFeed.class);
                startActivity(news);
                break;
            case R.id.airport:
                //go to the Flight Status tracker page:
                Intent air = new Intent(this, FlightMainActivity.class);
                startActivity(air);
                break;
            case R.id.dictionary:
                //go to the Merriam Webster Dictionary page:
                Intent dic = new Intent(this, Dictionary.class);
                startActivity(dic);
                break;
            case R.id.help:
                //when click the help menu, show the author's name and so on:
                customDialog();
                break;
            case R.id.save_menu:
                //when click the saved news menu, go to the saved news page
                Intent saved = new Intent(this, saved_list_newyork.class);
                startActivity(saved);
                break;
        }
        return true;
    }

    /**
     * the dialog shows the information of the article
     */
    private void customDialog(){
        LayoutInflater inflater = getLayoutInflater();
        View v = inflater.inflate(R.layout.customdialog_newyork, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //find the edit box
        EditText edit = v.findViewById(R.id.author);
        builder.setView(v);
        builder.create().show();
    }

    /**
     * the adapter for the list view
     */
    private class MyOwnAdapter extends BaseAdapter {
        public MyOwnAdapter() {
            super();
        }

        @Override
        public int getCount() {
            return tNewsFound.size() ;
        }

        @Override
        public TimesNews getItem(int position) {
            return tNewsFound.get(position);
        }

        /**
         * this method get the view for the list view single row
         * @param position
         * @param old
         * @param parent
         * @return
         */
        @Override
        public View getView(int position, View old, ViewGroup parent) {

            LayoutInflater inflater = getLayoutInflater();

            TimesNews messageToShow = getItem(position);
            newView = inflater.inflate(R.layout.single_row_newyork, null);
            TextView rowText = newView.findViewById(R.id.rowNews);
            rowText.setText(messageToShow.getTitle());

            return newView;
        }

        @Override
        public long getItemId(int position) {

            return position;
        }
    }

    /**
     * a inner class extends the AsyncTask, to get the data from the HTTP page
     */

    private class NewsQuery extends AsyncTask<String, Integer, String> {
        //variables
        private String title;
        private String author;
        private String link;
        private String description;
        TimesNews tNews;

        @Override
        protected String doInBackground(String... params) {

            //read the JSON file from the New York Times website
            searchText = sView.getQuery().toString();
            InputStream inStream = null;
            URL UVurl = null;
            try {
                UVurl = new URL("https://api.nytimes.com/svc/search/v2/articlesearch.json?q=" + searchText + "&api-key=89kmL9QdZSaSnHNrZtgRuPmf11e3mPQh");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            Log.i("wang test is", "" + searchText);
            HttpURLConnection UVConnection = null;
            try {
                UVConnection = (HttpURLConnection) UVurl.openConnection();

                inStream = UVConnection.getInputStream();

                //create a JSON object from the response
                BufferedReader reader = new BufferedReader(new InputStreamReader(inStream, "UTF-8"), 8);
                StringBuilder sb = new StringBuilder();

                String line = null;
                while ((line = reader.readLine()) != null)
                {
                    sb.append(line + "\n");
                }
                String result = sb.toString();


                JSONObject jsonObject = new JSONObject(result).getJSONObject("response");

                //get the data from the JSon file
                JSONArray arr = jsonObject.getJSONArray("docs");
                Log.i("test array:", ""+ arr.toString());

                for (int i = 0; i < arr.length(); i++) {
                    JSONObject temp = (JSONObject) arr.get(i);
                    Log.i("test temp:", ""+ temp);
                    title = temp.getJSONObject("headline").getString("main");
                    publishProgress(25);
                    Log.i("Title is:", ""+ title);
                    author = temp.getJSONObject("byline").getString("original");
                    publishProgress(40);
                    Log.i("Author is:", ""+ author);
                    link = temp.getString("web_url");
                    publishProgress(80);
                    Log.i("Link is:", ""+ link);
                    description = temp.getString("lead_paragraph");
                    publishProgress(100);
                    Log.i("Description is:", ""+ description);
                    //put the information to the TimesNews object
                    tNews = new TimesNews(title, author, link, description);
                    //add this news article to the array list
                    tNewsFound.add(tNews);
                }
                if(tNewsFound.size() == 0){
                    Toast.makeText(activity_newyork.this,"Do not find any result",Toast.LENGTH_LONG).show();
                }
                Thread.sleep(2000); //pause for 2000 milliseconds to watch the progress bar spin

            }catch(Exception ex){
                Log.i("Crash!!", ex.getMessage() );
            }
            return "Finish";
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            Log.i("AsyncTaskExample", "update:" + values[0]);
            pBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            //set the Progress bar invisible when finish display the data
            adt.notifyDataSetChanged();
            pBar.setVisibility(View.INVISIBLE);
        }
    }

}

