package com.ammarkureja.friendstrackerapp.controller;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.ammarkureja.friendstrackerapp.R;
import com.ammarkureja.friendstrackerapp.model.Contact;
import com.ammarkureja.friendstrackerapp.model.ContactContract;

/**
 * A placeholder fragment containing a simple view.
 */
public class AddEditActivityFragment extends Fragment {
    private static final String TAG = "AddEditActivityFragment";

    public enum  FragmentEditMode {Edit, Add}
    private FragmentEditMode cMode;

    private EditText cNameTextView;
    private EditText cEmailTextView;
    private EditText cBirthTextView;
    private EditText cLocation;
    private Button cSaveButton;

    public AddEditActivityFragment() {
        Log.d(TAG, "AddEditActivityFragment: constructor call");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: starts");
        View view = inflater.inflate(R.layout.fragment_add_edit, container, false);
        cNameTextView = (EditText) view.findViewById(R.id.addedit_name);
        cEmailTextView = (EditText) view.findViewById(R.id.addedit_email);
        cBirthTextView = (EditText) view.findViewById(R.id.addedit_dateofbirth);
        cLocation = (EditText) view.findViewById(R.id.addedit_location);
        cSaveButton = (Button) view.findViewById(R.id.addedit_save);
        cMode = FragmentEditMode.Edit;

        Bundle arguments = getActivity().getIntent().getExtras(); //this line will be changed

        final Contact contact;

        if (arguments != null) {
            Log.d(TAG, "onCreateView: retrieving contact details. . .");
            contact = (Contact) arguments.getSerializable(Contact.class.getSimpleName());

            if (contact != null) {
                Log.d(TAG, "onCreateView: task details found, editing...");
                cNameTextView.setText(contact.getmName());
                cEmailTextView.setText(contact.getmEmail());
                cBirthTextView.setText(contact.getmBirth());
                cLocation.setText(contact.getmLocation());
                cMode = FragmentEditMode.Edit;

            } else {
                // no contact so we must be adding new meeting
                cMode = FragmentEditMode.Add;
            }

        } else {
            // no Contact, we must be adding a new contact
            Log.d(TAG, "onCreateView: no argument for contacts, create new contact");
            contact = null;
            cMode = FragmentEditMode.Add;
        }

        cSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                //Update the database if atleast one field has changed.
                // there is no need to hit database unless this has happened

                String so; //to save repeated conversions to String.

                if (cNameTextView.length()> 0) {
                    so = cNameTextView.getText().toString();
                } else {
                    so = null;
                }

                ContentResolver contentResolver = getActivity().getContentResolver();
                ContentValues contentValues = new ContentValues();

                switch (cMode) {
                    case Edit:
                        if (!cNameTextView.getText().toString().equals(contact.getmName())) {
                            contentValues.put(ContactContract.Columns.CONTACT_NAME, cNameTextView.getText().toString());
                        }
                        if (!cEmailTextView.getText().toString().equals(contact.getmEmail())) {
                            contentValues.put(ContactContract.Columns.CONTACT_EMAIL, cEmailTextView.getText().toString());
                        }
                        if (!cBirthTextView.getText().toString().equals(contact.getmBirth())) {
                            contentValues.put(ContactContract.Columns.CONTACT_DOB, cBirthTextView.getText().toString());
                        }
                        if (!cLocation.getText().toString().equals(contact.getmLocation())) {
                            contentValues.put(ContactContract.Columns.CONTACT_LOCATION, cLocation.getText().toString());
                        }
                        // if any value is changed than it needs to be updated
                        if (contentValues.size() != 0) {
                            Log.d(TAG, "onClick: updating contact");
                            contentResolver.update(ContactContract.buildContactsUri(contact.getId()), contentValues, null, null);
                        }
                        break;
                    case Add:
                    if (cNameTextView.length() > 0) {
                        Log.d(TAG, "onClick: adding new contact");
                        contentValues.put(ContactContract.Columns.CONTACT_NAME, cNameTextView.getText().toString());
                        contentValues.put(ContactContract.Columns.CONTACT_DOB, cBirthTextView.getText().toString());
                        contentValues.put(ContactContract.Columns.CONTACT_EMAIL, cEmailTextView.getText().toString());
                        contentValues.put(ContactContract.Columns.CONTACT_LOCATION, cLocation.getText().toString());
                        contentResolver.insert(ContactContract.CONTENT_URI, contentValues);

                        break;
                    }
                    Log.d(TAG, "onClick: Done Editing");
                }

            }
        });
        Log.d(TAG, "onCreateView: Exiting...");

        return view;
    }
}
