package com.example.finalproject.Flight;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FlightDatabaseHelper extends SQLiteOpenHelper {

    private static final int version =1;
    private static final String DATABASE_NAME = "FlightDatabase.db";
    public FlightDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + FlightDatabaseSchema.FlightTable.NAME + "(" + "_id integer primary key " + "autoincrement, " + FlightDatabaseSchema.FlightTable.Cols.Location + ", " + FlightDatabaseSchema.FlightTable.Cols.Speed + ", " + FlightDatabaseSchema.FlightTable.Cols.Altitude + ", " + FlightDatabaseSchema.FlightTable.Cols.Status + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
