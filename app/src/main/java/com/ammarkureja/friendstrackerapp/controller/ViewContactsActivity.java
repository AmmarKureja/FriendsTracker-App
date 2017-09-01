package com.ammarkureja.friendstrackerapp.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.ammarkureja.friendstrackerapp.R;
import com.ammarkureja.friendstrackerapp.model.Contact;
import com.ammarkureja.friendstrackerapp.model.ContactContract;

public class ViewContactsActivity extends AppCompatActivity implements CursorRecyclerViewAdapter_Contacts.OnContactClickListener{
    private static final String TAG = "ViewContactsActivity";
    private boolean mTwoPane = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_contacts);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public void onEditClick(Contact contact) {
        contactEditRequest(contact);
    }

    @Override
    public void onDeleteClick(Contact contact) {
        getContentResolver().delete(ContactContract.buildContactsUri(contact.getId()), null, null);

    }

    private void  contactEditRequest (Contact contact) {

        if (mTwoPane) {
            Log.d(TAG, "contactEditRequest: contactEditRequest: in two pane mode (tablet)");
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
}
