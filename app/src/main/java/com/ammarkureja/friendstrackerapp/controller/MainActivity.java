package com.ammarkureja.friendstrackerapp.controller;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.ammarkureja.friendstrackerapp.R;
import com.ammarkureja.friendstrackerapp.model.Contact;
import com.ammarkureja.friendstrackerapp.model.ContactContract;
import com.ammarkureja.friendstrackerapp.model.Meeting;
import com.ammarkureja.friendstrackerapp.model.MeetingContract;

import static android.Manifest.permission.READ_CONTACTS;
import static com.ammarkureja.friendstrackerapp.R.layout.activity_main;

public class MainActivity extends AppCompatActivity implements CursorRecyclerViewAdapter_Meetings.OnMeetingClickListener{

    private static final String TAG = "MainActivity";
    private ListView listApp;
    //wether the activity is in 2-pane mode
    //i-e running on landscape mode in a tablet
    private boolean mTwoPane = false;
    private static final String ADD_EDIT_FRAGMENT = "AddEditFregment";
    private static final String ADD_EDIT_MEETING_FREGMENT = "AddEditMeetingFregment";

    private static final int REQUEST_CODE_READ_CONTACTS = 1;
    // private static boolean READ_CONTACTS_GRANTED;
    Button btn_manageFriends = null;
    Button btn_manageMeetings = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_main);

    String [] meetingProjection = {MeetingContract.Columns._ID,
    MeetingContract.Columns.MEETING_TITLE,
    MeetingContract.Columns.MEETING_START_TIME,
    MeetingContract.Columns.MEETING_END_TIME,
    MeetingContract.Columns.MEETING_DATE,
    MeetingContract.Columns.MEETING_LOCATION};

        ContentResolver contentResolver = getContentResolver();

//        String[] projection = {ContactContract.Columns._ID,
//                ContactContract.Columns.CONTACT_NAME,
//                ContactContract.Columns.CONTACT_DOB,
//                ContactContract.Columns.CONTACT_EMAIL,
//                ContactContract.Columns.CONTACT_IMAGEURL};
//        ContentResolver contentResolver = getContentResolver();
//
//        ContentValues values = new ContentValues();
//
//        //update multiple rows
////        values.put(ContactContract.Columns.CONTACT_NAME, "Jamal");
////        values.put(ContactContract.Columns.CONTACT_EMAIL, "jamal@email.com");
////        String selection = ContactContract.Columns.CONTACT_EMAIL +" = "+ "'abubakar@email.com'";
////        int count = contentResolver.update(ContactContract.CONTENT_URI, values, selection, null);
////        Log.d(TAG, "onCreate: " + count +" records updated");
//
//        //using selection args to putt multiple selection values
////        values.put(ContactContract.Columns.CONTACT_NAME, "Ali Hamza");
////        values.put(ContactContract.Columns.CONTACT_EMAIL, "hamza@gmail.com");
////        String selection = ContactContract.Columns._ID +" = ?";
////        String[] args = {"2"};
////        int count = contentResolver.update(ContactContract.CONTENT_URI, values, selection, args);
////        Log.d(TAG, "onCreate: " + count +" records updated");
//
//        //deleting rows
////        int count = contentResolver.delete(ContactContract.buildContactsUri(1), null, null);
////        Log.d(TAG, "onCreate: " + count +" records Deleted");
//
//        String selection = ContactContract.Columns.CONTACT_NAME + " = ?";
//        String[] args = {"Ali Hamza"};
//        int count = contentResolver.delete(ContactContract.CONTENT_URI, selection, args);
//        Log.d(TAG, "onCreate: " + count + " record(s) deleted");
//
//
//        //updating existing record
////        values.put(ContactContract.Columns.CONTACT_NAME, "Hassan");
////        values.put(ContactContract.Columns.CONTACT_DOB, "+923006781255");
////        int count = contentResolver.update(ContactContract.buildContactsUri(1), values, null, null);
////        Log.d(TAG, "onCreate: " + count + " records updated");
//
        //inserting data into Contacts table
//        values.put(ContactContract.Columns.CONTACT_NAME, "Abubakar");
//        values.put(ContactContract.Columns.CONTACT_DOB, "14-12-1991");
//        values.put(ContactContract.Columns.CONTACT_EMAIL, "abubakar@gmail.com");
//        values.put(ContactContract.Columns.CONTACT_IMAGEURL, "abubakarimageUrl");
//        Uri uri = contentResolver.insert(ContactContract.CONTENT_URI, values);


//        Cursor cursor = contentResolver.query(ContactContract.CONTENT_URI,
//                projection,
//                null,
//                null,
//                ContactContract.Columns.CONTACT_NAME);

        Cursor cursor = contentResolver.query(MeetingContract.CONTENT_URI, meetingProjection,
                null,
                null,
                MeetingContract.Columns.MEETING_TITLE);

        if (cursor!=null) {
            Log.d(TAG, "onCreate: On create number of rows: " + cursor.getCount());

            while (cursor.moveToNext()) {
                for (int i = 0; i<cursor.getColumnCount(); i++) {
                    Log.d(TAG, "onCreate: " + cursor.getColumnName(i) + ": " + cursor.getString(i));
                }
                Log.d(TAG, "onCreate: =================================================");
            }
        }
        cursor.close();

//        AppDatabase appDatabase = AppDatabase.getInstance(this);
//        final SQLiteDatabase db = appDatabase.getReadableDatabase();



        listApp = (ListView) findViewById(R.id.contact_list);
        //for Api 23 and above, we need to get explicit permission each time when accessing phone contatcs
        int hasReadContactPermission = ContextCompat.checkSelfPermission(this, READ_CONTACTS);
        Log.d(TAG, "onCreate: check self permission = " + hasReadContactPermission);
//        if (hasReadContactPermission == PackageManager.PERMISSION_GRANTED) {
//            Log.d(TAG, "onCreate: permission granted");
//            // READ_CONTACTS_GRANTED = true;
//        } else {
//            Log.d(TAG, "onCreate: requesting permission");
//            ActivityCompat.requestPermissions(this, new String[]{READ_CONTACTS}, REQUEST_CODE_READ_CONTACTS);
//        }
        if (hasReadContactPermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{READ_CONTACTS}, REQUEST_CODE_READ_CONTACTS);
        }

        Log.d(TAG, "onCreate: ends");

    }

    public void readContactsPermission(View view) {
        Log.d(TAG, "onClick: starts");
        if (ContextCompat.checkSelfPermission(MainActivity.this, READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            ContentResolver cr = getContentResolver();
            ContentValues contentValues = new ContentValues();
            Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
            if (cur.moveToFirst()) {

                do {
                    contentValues.put(ContactContract.Columns.PHONE_ID, cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID)));
                    String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                    Cursor cur1 = cr.query(
                            ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
                            ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    while (cur1.moveToNext()) {
                        //to get the contact names
                        contentValues.put(ContactContract.Columns.CONTACT_NAME, cur1.getString(cur1.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)));
                        contentValues.put(ContactContract.Columns.CONTACT_EMAIL, cur1.getString(cur1.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA)));
                    }
                    cr.insert(ContactContract.CONTENT_URI, contentValues);
                    cur1.close();
                } while (cur.moveToNext());
                cur.close();
            }
        }else {
            Snackbar.make(view, "This App cant display your contacts records unless you grant access", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Grant Access", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    //Toast.makeText(MainActivity.this, "Snackbar Action Clicked", Toast.LENGTH_SHORT).show();
                                    if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, READ_CONTACTS)) {
                                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{READ_CONTACTS}, REQUEST_CODE_READ_CONTACTS);
                                    } else {
                                        Intent intent = new Intent();
                                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                        Uri uri = Uri.fromParts("package", MainActivity.this.getPackageName(), null);
                                        intent.setData(uri);
                                        MainActivity.this.startActivity(intent);
                                    }

                                }
                            }
                    ).show();
        }

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.menuMain_addContact:
                contactEditRequest(null);
                break;
            case R.id.menumain_viewContacts:
                contactlistViewRequest();
                break;
            case R.id.menumain_addMeeting:
                meetingEditRequest(null);
                break;
            case R.id.menumain_viewMeetings:
                break;
            case R.id.menumain_settings:
                break;
            case R.id.menumain_showAbout:
                break;
            case R.id.menumain_generate:
                break;
        }

        return super.onOptionsItemSelected(item);

        }

    @Override
    public void onEditClick(Meeting meeting) {
        meetingEditRequest(meeting);
    }

    @Override
    public void onDeleteClick(Meeting meeting) {

    }

    private void  contactEditRequest (Contact contact) {
            Log.d(TAG, "contactEditRequest: starts");
            if (mTwoPane) {
                Log.d(TAG, "contactEditRequest: in two pane mode (tablet)");
            } else {
                Log.d(TAG, "contactEditRequest: in single pane mode (phone)");
                //in single pane mode start the activity for selected item id
                Intent detailIntent = new Intent(this, AddEditActivity.class);
                if (contact != null) {
                    //editing Task
                    detailIntent.putExtra(Contact.class.getSimpleName(), contact);
                    startActivity(detailIntent);
                } else { //adding a new contact
                    startActivity(detailIntent);
                }
            }
        }

        private void meetingEditRequest (Meeting meeting) {
            Log.d(TAG, "meetingEditRequest: starts");
            if (mTwoPane) {
                Log.d(TAG, "meetingEditRequest: in two pane mode (tablet)");
            } else {
                Log.d(TAG, "meetingEditRequest: in a single pane mode");
                //in single pane mode, start the activity for selected item id
                Intent detailIntent = new Intent(this, AddEditMeeting.class);
                if (meeting !=null) {
                    //editing meeting
                    detailIntent.putExtra(Meeting.class.getSimpleName(), meeting);
                    startActivity(detailIntent);
                } else { //adding a new meeting
                startActivity(detailIntent);
                }
            }

        }

        private void contactlistViewRequest() {

            Intent intent = new Intent(MainActivity.this, ViewContactsActivity.class);
            startActivity(intent);

        }


        private void readContacts() {



        }





//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        Log.d(TAG, "onRequestPermissionsResult: started");
//
//        switch (requestCode) {
//            case REQUEST_CODE_READ_CONTACTS:
//                //if request is cancelled, array is empty
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    //permission granted
//                    //do the contatcs related tasks
//                    Log.d(TAG, "onRequestPermissionsResult: permission granted");
//                    //READ_CONTACTS_GRANTED = true;
//
//                } else {
//                    //permission denied,
//                    //disable the functionality that depends upon permission
//                    Log.d(TAG, "onRequestPermissionsResult: permission refused");
//                }
//                //this method is enabling button if READ_CONTACTS_GRANTED IS true
//                //fab.setEnabled(READ_CONTACTS_GRANTED);
//        }
//
//        Log.d(TAG, "onRequestPermissionsResult: ends");
//    }

}
