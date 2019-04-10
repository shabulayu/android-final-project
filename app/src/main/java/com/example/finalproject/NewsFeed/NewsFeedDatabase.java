package com.example.finalproject.NewsFeed;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * This class is database class extends from SQLiteOpenHelper
 */
public class NewsFeedDatabase extends SQLiteOpenHelper {

    public static final String DATABASE_NAME_SAVED = "NewsFeed";
    public static final int VERSION_NUM = 1;
    public static final String TABLE_NAME_SAVED = "SavedArticle";
    public static final String TABLE_NAME_LIST = "searchedArticles";
    public static final String COL_ID = "_id";
    public static final String COL_TITLE = "title";
    public static final String COL_AUTHOR = "author";
    public static final String COL_TEXT = "text";
    public static final String COL_URL = "url";

    public NewsFeedDatabase(Activity ctx){
        super(ctx, DATABASE_NAME_SAVED,null, VERSION_NUM);
    }

    /**
     * create a table
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME_SAVED + "( "
                + COL_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL_TITLE + " TEXT, " + COL_AUTHOR + " TEXT, "
                + COL_TEXT + " TEXT, " + COL_URL + " TEXT)");

        db.execSQL("CREATE TABLE " + TABLE_NAME_LIST + "( "
                + COL_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL_TITLE + " TEXT, " + COL_AUTHOR + " TEXT, "
                + COL_TEXT + " TEXT, " + COL_URL + " TEXT)");
    }

    /**
     * upgrade a table if new version is larger than old version
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Delete the old table:
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_SAVED);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_LIST);
        //Create a new table:
        onCreate(db);
    }

}
