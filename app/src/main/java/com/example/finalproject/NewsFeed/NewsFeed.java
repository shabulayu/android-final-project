package com.example.finalproject.NewsFeed;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.example.finalproject.Dictionary.Dictionary;
import com.example.finalproject.Flight.FlightMainActivity;
import com.example.finalproject.NewYorkTimes.activity_newyork;
import com.example.finalproject.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * This activity is the main page of NewsFeed app. it has search box to search related title from
 * a News Crawler site: https://webhose.io. The results will be listed in a list view. User can click
 * the item on list view to read details.
 */
public class NewsFeed extends AppCompatActivity {

    ArticleAdapter adt;
    ArticleQuery aq;
    Toolbar tBar;
    EditText numArt;
    SearchView sView;
    ProgressBar pBar;
    ListView tList;
    Article article;
    ArrayList<Article> articles = new ArrayList<>();
    Article showArticle = new Article();
    ArrayList<Article> showArticles = new ArrayList<>();

    /**
     * onCreate method to initialize the view of this page
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed);

        adt = new ArticleAdapter();

        tBar = findViewById(R.id.toolbar);
        tList = findViewById(R.id.titleList);
        sView = findViewById(R.id.searchview);
        pBar= findViewById(R.id.proBar);
        pBar.setVisibility(View.INVISIBLE);
        numArt = findViewById(R.id.numArticle);

        setSupportActionBar(tBar);

        //search view submit function
        sView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Log.i("search", "onCreate: search");
                if(s.length()==0||s==null){
                    Log.i("search", "onQueryTextSubmit: no search word");
                    Toast.makeText(NewsFeed.this,"please enter your search word",Toast.LENGTH_SHORT).show();

                }else{
                    aq = new ArticleQuery();
                    showArticles.clear();
                    adt.notifyDataSetChanged();
                    aq.execute();
                }

                Log.i("onQueryTextSubmit", "text: "+s);
                //listAdt.notifyDataSetChanged();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                tList.setFilterText(s);
                Log.i("onQueryTextChange", " new text: "+s);
                adt.notifyDataSetChanged();
                return false;
            }
        });

        tList.setAdapter(adt);
        //listAdt = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, showTitle);

        //list view click listener.
        tList.setOnItemClickListener((parent, view, position, id)-> {

            Bundle dataToPass = new Bundle();
            dataToPass.putString("title",showArticles.get(position).getTitle());
            dataToPass.putString("text",showArticles.get(position).getText());
            dataToPass.putString("author",showArticles.get(position).getAuthor());
            dataToPass.putString("url",showArticles.get(position).getUrl());

            Intent nextActivity = new Intent(NewsFeed.this, NewsArticle.class);
            nextActivity.putExtras(dataToPass); //send data to next activity
            startActivity(nextActivity); //make the transition

        });
        adt.notifyDataSetChanged();
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
                startActivity(new Intent(this,NewsSavedArticle.class));
                break;
            case R.id.item3:
                Intent air = new Intent(this, FlightMainActivity.class);
                startActivity(air);
                break;
            case R.id.item4:
                Intent dic = new Intent(this, Dictionary.class);
                startActivity(dic);
                break;
            case R.id.item5:
                Intent newyork = new Intent(this, activity_newyork.class);
                startActivity(newyork);
                break;
        }
        return true;
    }

    /**
     * pop up a dialog including author name,
     * activity version number and instruction for how to use the interface
     */
    public void alert(){

        View help = getLayoutInflater().inflate(R.layout.news_dialog_help, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setView(help);
        builder
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // What to do on Accept


                    }
                });

        builder.create().show();
    }

    /**
     * Class that save list view
     * In this case, showArticles is the article that its title matches the search word
     */
    protected class ArticleAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return showArticles.size();
        }

        @Override
        public Article getItem(int position) {
            return showArticles.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater =getLayoutInflater();
            View newView = null;

            showArticle = getItem(position);

            newView = inflater.inflate(R.layout.news_list_title,parent,false);
            TextView t = newView.findViewById(R.id.title);
            String show = showArticle.getTitle();
            t.setText(show);


            Log.i("getview", "getView: getview length: "+showArticles.size());
            return newView;
        }
    }

    /**
     * This class is to get data from internet.
     */
    private class ArticleQuery extends AsyncTask<String, Integer, String>{

        String title ="";
        String text ="";
        String author="";
        String link="";
        String searchText="";


        @Override
        protected String doInBackground(String... strings) {

            publishProgress(0);
            try {
                searchText = sView.getQuery().toString();
                String urlString = "http://webhose.io/filterWebContent?token=2efeac2a-cdc0-49c0-8b06-660ba5937a48&format=xml&sort=crawled&q="+searchText;
                URL url = new URL(urlString);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                InputStream inStream = conn.getInputStream();

                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");

                //set xml pull parser
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput( inStream  , "UTF-8");

                publishProgress(25);

                while(xpp.getEventType() != XmlPullParser.END_DOCUMENT){

                    Log.i("connected", "doInBackground: in while loop");
                    if(xpp.getEventType() == XmlPullParser.START_TAG){
                        String tagName = xpp.getName();
                        Log.i("connected", "doInBackground: in while loop start tag"+tagName);

                        if(tagName.equals("post")){
                            article = new Article();
                        }
                        if(tagName.equals("title_full")){
                            Log.i("connected", "doInBackground: in while loop title tag");

                            title = xpp.nextText();
                            article.setTitle(title);


                        }else if (tagName.equals("url")){
                            Log.i("url", "doInBackground: got url");
                            link = xpp.nextText();
                            article.setUrl(link);

                        }else if(tagName.equals("text")){
                            Log.i("text", "doInBackground: got text");
                            text = xpp.nextText();
                            article.setText(text);
                        }else if(tagName.equals("author")){
                            Log.i("author", "doInBackground: got author");
                            author = xpp.nextText();
                            article.setAuthor(author);
                            publishProgress(50);
                        }
                        //when it arrives end tag, it means the end of information for one article
                    }else if(xpp.getEventType() == XmlPullParser.END_TAG){
                        if(xpp.getName().equals("post")){
                            articles.add(article);
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
            pBar.setVisibility(View.VISIBLE);
            pBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(String s) {

            //when the article's title contains search text, add this article into showArticles array
            for(Article a: articles){
                if(a.getTitle().contains(searchText)){
                    showArticles.add(a);
                }
            }

            //pop up a toast and snack bar when there is no result
            if(showArticles.size()==0){
                Toast.makeText(NewsFeed.this, "article not found", Toast.LENGTH_SHORT).show();
                Snackbar snack = Snackbar.make(tBar, "article not found", Snackbar.LENGTH_LONG);
                View view = snack.getView();
                FrameLayout.LayoutParams params =(FrameLayout.LayoutParams)view.getLayoutParams();
                params.gravity = Gravity.TOP;
                view.setLayoutParams(params);
                snack.setAction("OK",(a->{})).show();
            }
            numArt.setText("Number of articles: "+showArticles.size()+"");
            adt.notifyDataSetChanged();

            pBar.setVisibility(View.INVISIBLE);

        }
    }
}
