package com.ammarkureja.friendstrackerapp.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by admin on 21/08/2017.
 *
 * Basic database class for the application
 *
 * The only class that should use this is App provider{@link AppProvider}
 */

public class AppDatabase extends SQLiteOpenHelper{
    private static final String TAG = "AppDatabase";

    public static String DATABASE_NAME = "FriendsTracker.db";
    public static final int DATABASE_VERSION = 1;

    //implement App Database as a singleton
    private static AppDatabase instance = null;

    private AppDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d(TAG, "AppDatabase: constructor");
    }

    /**
     * Get instance of the App singleton database helper objects
     * @param context the content provider context
     * @return a SQLite Database helper object
     */
   public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            Log.d(TAG, "getInstance: creating new instance");
            instance = new AppDatabase(context);
        }

        return instance;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate: starts creating tables");
        String sSQL; //use a string to facilitate logging
//        sSQL = "DROP TABLE "+ ContactContract.TABLE_NAME+";";
//        db.execSQL(sSQL);
//        sSQL = "Create TABLE Contacts (_id INTEGER PRIMARY KEY NOT NULL, name TEXT NOT NULL, phone VARCHAR, email VARCHAR, imageUrl VARCHAR);";
        sSQL = "CREATE TABLE " + ContactContract.TABLE_NAME + " ("
                + ContactContract.Columns._ID + " INTEGER PRIMARY KEY NOT NULL, "
                + ContactContract.Columns.PHONE_ID + " INTEGER, "
                + ContactContract.Columns.CONTACT_NAME + " TEXT NOT NULL, "
                + ContactContract.Columns.CONTACT_DOB + " VARCHAR, "
                + ContactContract.Columns.CONTACT_EMAIL + " VARCHAR, "
                + ContactContract.Columns.CONTACT_LOCATION + " VARCHAR, "
                + ContactContract.Columns.CONTACT_IMAGEURL + " VARCHAR);";
        Log.d(TAG, "Contacts Table Created");
        db.execSQL(sSQL);


// creating table for meetings
        sSQL = "CREATE TABLE " + MeetingContract.TABLE_NAME + " ("
                + MeetingContract.Columns._ID + " INTEGER PRIMARY KEY NOT NULL, "
                + MeetingContract.Columns.MEETING_TITLE + " TEXT NOT NULL, "
                + MeetingContract.Columns.MEETING_START_TIME + " VARCHAR, "
                + MeetingContract.Columns.MEETING_END_TIME + " VARCHAR, "
                + MeetingContract.Columns.MEETING_DATE + " VARCHAR, "
                + MeetingContract.Columns.MEETING_LOCATION + " VARCHAR, "
                + MeetingContract.Columns.MEETING_COUNT_CONTACT + " VARCHAR);";

        db.execSQL(sSQL);
        Log.d(TAG, "Meeting Table Created");

        //creating contact_meeting table
        sSQL = "CREATE TABLE " + ContactMeetingContract.TABLE_NAME + "("
                + ContactMeetingContract.Columns.CONTACT_ID + " INTEGER NOT NULL, "
//                 REFERENCES "+ContactContract.TABLE_NAME+"("+ContactContract.Columns._ID+"), "
                + ContactMeetingContract.Columns.MEETING_ID + " INTEGER NOT NULL, "
//                + "REFERENCES "+MeetingContract.TABLE_NAME+"("+MeetingContract.Columns._ID+"), "
                + "FOREIGN KEY ("+ContactMeetingContract.Columns.CONTACT_ID +") REFERENCES "+ContactContract.TABLE_NAME+" ("+ContactContract.Columns._ID+"), "
                + "FOREIGN KEY ("+ContactMeetingContract.Columns.MEETING_ID +") REFERENCES "+MeetingContract.TABLE_NAME+" ("+ContactContract.Columns._ID+"));";
                //+ "PRIMARY KEY("+ContactMeetingContract.Columns.CONTACT_ID+", "+ContactMeetingContract.Columns.MEETING_ID+");";
        Log.d(TAG, "Contact_Meeting Table Created");
        db.execSQL(sSQL);



    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        Log.d(TAG, "onUpgrade: starts");
        switch (oldVersion) {
            case 1:
                //upgrade logic from version 1
//                String sSQL = "ALTER TABLE " + ContactContract.TABLE_NAME + " CHANGE "
//                        + ContactContract.Columns.CONTACT_DOB + " Date_of_Birth VARCHAR; ";


                break;
            default:
                throw new IllegalStateException("OnUpgrade() with unknown new version" + newVersion);
        }
        Log.d(TAG, "onUpgrade: ends");
    }
}
