package com.example.finalproject.NewsFeed;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.Button;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.example.finalproject.R;

import java.util.ArrayList;

/**
 * This class shows the details of selected article from News feed main page.
 */
public class NewsArticle extends AppCompatActivity {

    TextView title;
    TextView text;
    TextView author;
    TextView url;
    Toolbar tBar;
    Button saveBt;
    NewsFeedDatabase database;
    SQLiteDatabase db;
    Article article;
    //ArrayList<Article> savedArticles = new ArrayList<>();


    /**
     * shows the details of an article, user can save the article if user like.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_article);

        title = findViewById(R.id.articleTitle);
        text = findViewById(R.id.articleText);
        author = findViewById(R.id.author);
        url = findViewById(R.id.url);
        tBar = findViewById(R.id.toolbar2);
        saveBt = findViewById(R.id.savedBt);



        setSupportActionBar(tBar);
        //get data from last page
        Bundle dataReceive = this.getIntent().getExtras();
        title.setText(dataReceive.getString("title"));
        text.setText(dataReceive.getString("text"));
        author.setText(dataReceive.getString("author"));
        url.setText(dataReceive.getString("url"));


        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //set the scroll bar if the article is too long
        text.setMovementMethod(ScrollingMovementMethod.getInstance());

        //get database
        database = new NewsFeedDatabase(this);
        db = database.getWritableDatabase();
        saveBt.setOnClickListener(a->{

            ContentValues newRow = new ContentValues();


            newRow.put(NewsFeedDatabase.COL_TITLE,dataReceive.getString("title"));
            newRow.put(NewsFeedDatabase.COL_AUTHOR,dataReceive.getString("author"));
            newRow.put(NewsFeedDatabase.COL_URL,dataReceive.getString("url"));
            newRow.put(NewsFeedDatabase.COL_TEXT,dataReceive.getString("text"));
            long newId = db.insert(NewsFeedDatabase.TABLE_NAME_SAVED,null,newRow);

            article = new Article(dataReceive.getString("title"),
                    dataReceive.getString("text"),dataReceive.getString("author"),
                    dataReceive.getString("url"), newId);
            //savedArticles.add(article);
            Toast.makeText(this,"Article saved successful",Toast.LENGTH_LONG ).show();
        });

    }
}
