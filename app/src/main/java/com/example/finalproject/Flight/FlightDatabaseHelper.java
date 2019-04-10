package com.example.finalproject.Flight;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class FlightDatabaseHelper extends SQLiteOpenHelper {

    public static final int version = 1;
    public static final String TABLE_NAME = "SavedFlight";
    public static final String COL_ID = "_id";
    public static final String COL_Location = "location";
    public static final String COL_Speed = "speed";
    public static final String COL_Altitude = "altitude";
    public static final String COL_Status = "status";
    public static final String COL_IataNumber = "iataNumber";

    private static final String DATABASE_NAME = "FlightDatabase.db";

    public FlightDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + "( " + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL_Location + ", " + COL_Speed + ", "
                + COL_Altitude + ", " + COL_Status + ", " + COL_IataNumber + ") ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("Database upgrade", "old version: " + oldVersion + "  newVersion" + newVersion);

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("Database downgrade", "Old version:" + oldVersion + " newVersion:" + newVersion);

        //Delete the old table:
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        //Create a new table:
        onCreate(db);
    }
}
