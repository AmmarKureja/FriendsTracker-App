package com.ammarkureja.friendstrackerapp.controller;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.ammarkureja.friendstrackerapp.R;
import com.ammarkureja.friendstrackerapp.model.ContactEntry;
import com.ammarkureja.friendstrackerapp.model.ManageContacts;

import static android.Manifest.permission.READ_CONTACTS;
import static com.ammarkureja.friendstrackerapp.R.layout.content_main;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ListView listApp;

    private static final int REQUEST_CODE_READ_CONTACTS = 1;
    private static boolean READ_CONTACTS_GRANTED;
    Button fab = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(content_main);

        listApp = (ListView) findViewById(R.id.contact_list);
        //for Api 23 and above, we need to get explicit permission each time when accessing phone contatcs
        int hasReadContactPermission = ContextCompat.checkSelfPermission(this, READ_CONTACTS);
        Log.d(TAG, "onCreate: check self permission = " + hasReadContactPermission);
        if (hasReadContactPermission == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "onCreate: permission granted");
            READ_CONTACTS_GRANTED = true;
        } else {
            Log.d(TAG, "onCreate: requesting permission");
            ActivityCompat.requestPermissions(this, new String[]{READ_CONTACTS}, REQUEST_CODE_READ_CONTACTS);
        }


        fab = (Button) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
                               {
                                   @Override
                                   public void onClick (View view) {
                                       Log.d(TAG, "onClick: starts");
                                       ManageContacts manageContacts = new ManageContacts();
                                       manageContacts.retrieveContacts(getContentResolver());
                                       Log.d(TAG, "onClick: after retrieving: " + manageContacts.getContacts().get(0).toString());

                                       ArrayAdapter<ContactEntry> arrayAdapter = new ArrayAdapter<ContactEntry>(MainActivity.this,
                                               R.layout.app_textview, manageContacts.getContacts());
                                       listApp.setAdapter(arrayAdapter);



//                                       Log.d(TAG, "fab onClick: starts");
//                                       String projection[] = new String[] {ContactsContract.Contacts.DISPLAY_NAME_PRIMARY,
//                                               ContactsContract.CommonDataKinds.Email.DATA};
//                                       ContentResolver contentResolver = getContentResolver();
//                                       Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, projection,
//                                               null,
//                                               null,
//                                               ContactsContract.Contacts.DISPLAY_NAME_PRIMARY);
//
//                                       if (cursor != null) {
//                                           List<String> contacts = new ArrayList<String>();
//                                           while (cursor.moveToNext()) {
//                                               contacts.add(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY)) +
//                                                       cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA)));
//                                           }
//                                           cursor.close();
//                                           ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, R.layout.contact_detail, R.id.name, contacts);
//                                           contactNames.setAdapter(adapter);
//                                       }
//                                       Log.d(TAG, "fab onClick: ends ");
                                   }
                               });
        Log.d(TAG, "onCreate: ends");

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: started");

        switch (requestCode) {
            case REQUEST_CODE_READ_CONTACTS:
                //if request is cancelled, array is empty
                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //permission granted
                    //do the contatcs related tasks
                    Log.d(TAG, "onRequestPermissionsResult: permission granted");
                    READ_CONTACTS_GRANTED = true;

                } else {
                    //permission denied,
                    //disable the functionality that depends upon permission
                    Log.d(TAG, "onRequestPermissionsResult: permission refused");
                }

                fab.setEnabled(READ_CONTACTS_GRANTED);
        }

        Log.d(TAG, "onRequestPermissionsResult: ends");
    }
}
