package com.example.finalproject.NewsFeed;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.finalproject.R;

import java.util.ArrayList;

/**
 * this activity is to show saved articles
 */
public class NewsSavedArticle extends AppCompatActivity {

    ListView tList;
    ArrayList<Article> savedArticles = new ArrayList<>();
    Article savedArticle;
    NewsFeedDatabase database;
    SQLiteDatabase db;
    SavedAdapter adt =  new SavedAdapter();

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_saved_article);

        tList = findViewById(R.id.savedList);

        //get database
        database = new NewsFeedDatabase(this);
        db = database.getWritableDatabase();

        //query all the results from the database:
        String[] columns = {NewsFeedDatabase.COL_ID,NewsFeedDatabase.COL_TITLE,
                NewsFeedDatabase.COL_AUTHOR, NewsFeedDatabase.COL_TEXT, NewsFeedDatabase.COL_URL};
        Cursor c = db.query(false,NewsFeedDatabase.TABLE_NAME_SAVED, columns,
                null,null,null,null,null,null);

        //find the column indices
        int idColIndex = c.getColumnIndex(NewsFeedDatabase.COL_ID);
        int titleColIndex = c.getColumnIndex(NewsFeedDatabase.COL_TITLE);
        int authorColIndex = c.getColumnIndex(NewsFeedDatabase.COL_AUTHOR);
        int textColIndex = c.getColumnIndex(NewsFeedDatabase.COL_TEXT);
        int urlColIndex = c.getColumnIndex(NewsFeedDatabase.COL_URL);

        while(c.moveToNext()){
            long id = c.getLong(idColIndex);
            String title = c.getString(titleColIndex);
            String author = c.getString(authorColIndex);
            String text = c.getString(textColIndex);
            String url = c.getString(urlColIndex);

            savedArticles.add(new Article(title,text,author,url,id));
        }

        tList.setAdapter(adt);

        boolean isTablet = findViewById(R.id.fragmentLocation) != null;
        tList.setOnItemClickListener((parent, view, position, id)-> {
            //positionClicked = position;
            //databaseID = id;
            Bundle dataToPass = new Bundle();

            dataToPass.putString("title", savedArticles.get(position).getTitle());
            dataToPass.putString("author", savedArticles.get(position).getAuthor());
            dataToPass.putString("text", savedArticles.get(position).getText());
            dataToPass.putString("url", savedArticles.get(position).getUrl());
            dataToPass.putLong("id", id);
            if(isTablet)
            {
                NewsSavedFragment dFragment = new NewsSavedFragment(); //add a DetailFragment

//                dFragment.title.setText("");
//                dFragment.author.setText("");
//                dFragment.text.setText("");
//                dFragment.url.setText("");
                //dFragment.databaseiID.setText("");
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
                Intent nextActivity = new Intent(NewsSavedArticle.this, NewsEmpty.class);
                nextActivity.putExtras(dataToPass); //send data to next activity
                startActivityForResult(nextActivity,33); //make the transition
            }
        });
        adt.notifyDataSetChanged();
    }

    /**
     *  get result from next page
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 33)
        {
            if(resultCode == RESULT_OK) //if you hit the delete button instead of back button
            {

                long id = data.getLongExtra("id", 0);
                Log.i("Delete this message1:" , " id="+id);

                deleteMessageId((int)id);
            }
        }
    }

    /**
     * delete item from listview and database
     * @param id item id in database
     */
    public void deleteMessageId(int id)
    {

        //remove item from list view
        savedArticles.remove(id);
        String str="";
        Cursor d;
        String[] columns = {NewsFeedDatabase.COL_ID,NewsFeedDatabase.COL_TITLE,
                NewsFeedDatabase.COL_AUTHOR, NewsFeedDatabase.COL_TEXT, NewsFeedDatabase.COL_URL};
        d = db.query(false,NewsFeedDatabase.TABLE_NAME_SAVED, columns,
                null,null,null,null,null,null);
        if(d.moveToFirst()) {

            for (int i =0; i<id; i++) {
                d.moveToNext();

            }
            str = d.getString(d.getColumnIndex("_id"));
        }
        //delete item from database
        db.delete(NewsFeedDatabase.TABLE_NAME_SAVED, "_id=?", new String[] {str});

        adt.notifyDataSetChanged();
    }


    /**
     * this class is to save the list view of saved articles
     */
    protected class SavedAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return savedArticles.size();
        }

        @Override
        public Article getItem(int position) {
            return savedArticles.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater =getLayoutInflater();
            View newView = null;

            savedArticle = getItem(position);

            newView = inflater.inflate(R.layout.news_list_title,parent,false);
            TextView t = newView.findViewById(R.id.title);
            String show = savedArticle.getTitle();
            t.setText(show);

            return newView;
        }
    }
}
