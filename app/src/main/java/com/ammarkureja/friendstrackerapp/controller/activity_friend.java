package com.ammarkureja.friendstrackerapp.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ammarkureja.friendstrackerapp.R;
import com.ammarkureja.friendstrackerapp.model.ContactEntry;
import com.ammarkureja.friendstrackerapp.model.ManageContacts;

public class activity_friend extends AppCompatActivity {
    private ListView listApp;
    private static final String TAG = "activity_friend";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);
        listApp = (ListView) findViewById(R.id.contact_list);
        //SQLiteDatabase sqLiteDatabase =getBaseContext().openOrCreateDatabase("FriendsTrackerApp_Database", MODE_PRIVATE, null);
        //sqLiteDatabase.execSQL();
        ManageContacts manageContacts = new ManageContacts();
                    manageContacts.retrieveContacts(getContentResolver());
                    Log.d(TAG, "onCreate: after retrieving: " + manageContacts.getContacts().get(0).toString());
                    ArrayAdapter<ContactEntry> arrayAdapter = new ArrayAdapter<ContactEntry>(activity_friend.this,
                            R.layout.app_textview, manageContacts.getContacts());
                    listApp.setAdapter(arrayAdapter);
    }
}
