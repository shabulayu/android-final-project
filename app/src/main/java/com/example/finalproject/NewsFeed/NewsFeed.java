package com.example.finalproject.NewsFeed;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.finalproject.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class NewsFeed extends AppCompatActivity {

    //ArticleAdapter adt;
    Article article;
    ArrayList<String> articles = new ArrayList<>();
    ArrayAdapter<String> listAdt;
    Toolbar tBar;
    SearchView sView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed);

        //adt = new ArticleAdapter();

        ArticleQuery aq = new ArticleQuery();

        tBar = findViewById(R.id.toolbar);
        ListView tList = findViewById(R.id.titleList);
        sView = findViewById(R.id.searchview);

        setSupportActionBar(tBar);
        listAdt = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, articles);

        sView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Log.i("search", "onCreate: search");
                aq.execute();
                if(articles.size()==0){
                    Toast.makeText(NewsFeed.this, "article not found", Toast.LENGTH_SHORT).show();
                }
                listAdt.notifyDataSetChanged();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
//                if(TextUtils.isEmpty(s)){
//                    Toast.makeText(NewsFeed.this, "please enter your search word", Toast.LENGTH_SHORT).show();
//                }else{
//                    aq.execute();
//
//                    listAdt.notifyDataSetChanged();
//                }
                return false;
            }
        });



        tList.setAdapter(listAdt);


    }

    /**
     * toolbar's menu layout
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    /**
     * toolbar's button click event
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //what to do when the menu item is selected:
            case R.id.item1:
                alert();
                break;
            case R.id.item2:
                startActivity(new Intent(this,SavedArticle.class));
                break;

        }
        return true;
    }

    /**
     * pop up a dialog including author name,
     * activity version number and instruction for how to use the interface
     */
    public void alert(){

        View dia = getLayoutInflater().inflate(R.layout.dialog_help, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //EditText et = (EditText) dia.findViewById(R.id.diaEdit);
        builder.setView(dia);
        builder
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // What to do on Accept


                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                    }
                });
        builder.create().show();
    }

    /**
     * the class that save listview(in this case is titles)
     */
    protected class ArticleAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return articles.size();
        }

        @Override
        public String getItem(int position) {
            return articles.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater =getLayoutInflater();
            View newView = null;


            newView = inflater.inflate(R.layout.list_title,parent,false);
            TextView t = newView.findViewById(R.id.title);

            t.setText(articles.get(position));


            Log.i("getview", "getView: getview length: "+articles.size());
            return newView;
        }
    }

    private class ArticleQuery extends AsyncTask<String, Integer, String>{

        String t ="";
        String searchText="";

        @Override
        protected String doInBackground(String... strings) {
            publishProgress(0);
            try {
                String urlString = "http://webhose.io/filterWebContent?token=b1b5bd7e-d181-4a89-b0cf-d8997993ae22&format=xml&sort=crawled&q=stock%20market%20language%3Aenglish";
                URL url = new URL(urlString);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                InputStream inStream = conn.getInputStream();

                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");

                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput( inStream  , "UTF-8");


                searchText = sView.getQuery().toString();
                while(xpp.getEventType() != XmlPullParser.END_DOCUMENT){
                    Log.i("connected", "doInBackground: in while loop");
                    if(xpp.getEventType() == XmlPullParser.START_TAG){
                        String tagName = xpp.getName();
                        Log.i("connected", "doInBackground: in while loop start tag"+tagName);

                        if(tagName.equals("title_full")){
                            Log.i("connected", "doInBackground: in while loop title tag");

                            t = xpp.nextText();
                                if(t.contains(searchText)){
                                    Log.i("text", "doInBackground: length: "+articles.size());

                                    articles.add(t);

                                }


                        }
                    }
                    xpp.next();

                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }
            return "finished";
        }
        @Override
        protected void onProgressUpdate(Integer... values) {

        }

        @Override
        protected void onPostExecute(String s) {
            listAdt.notifyDataSetChanged();
        }
    }
}
