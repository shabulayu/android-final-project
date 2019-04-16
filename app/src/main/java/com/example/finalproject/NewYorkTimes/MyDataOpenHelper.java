package com.example.finalproject.NewYorkTimes;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * this class extends SQLiteOpenHelper to create the database
 */
public class MyDataOpenHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "MyDatabaseFile";
    public static final int VERSION_NUM = 1;
    public static final String TABLE_NAME = "ARTICLES";
    public static final String COL_ID = "ID";
    public static final String COL_TITLE = "TITLE";
    public static final String COL_AUTHOR = "AUTHOR";
    public static final String COL_LINK = "LINK";
    public static final String COL_DESCRIPTION = "DESCRIPTION";


    /**
     * constructor
     * @param ctx
     */
    public MyDataOpenHelper(Activity ctx) {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    /**
     * create the database
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        //Make sure you put spaces between SQL statements and Java strings:
        db.execSQL("CREATE TABLE " + TABLE_NAME + "( "
                + COL_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL_TITLE  + " TEXT," + COL_AUTHOR  + " TEXT," + COL_LINK  + " TEXT," + COL_DESCRIPTION  + " TEXT)");
    }

    /**
     * upgrade the database
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("Database upgrade", "Old version:" + oldVersion + " newVersion:"+newVersion);

        //Delete the old table:
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        //Create a new table:
        onCreate(db);
    }
}