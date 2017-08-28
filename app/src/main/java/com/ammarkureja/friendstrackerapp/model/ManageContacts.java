package com.ammarkureja.friendstrackerapp.model;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

/**
 * Created by admin on 16/08/2017.
 */

public class ManageContacts {

    private ArrayList<ContactEntry> contacts ;

    public ManageContacts () {
        Log.d(TAG, "ManageContacts: constructor");
        contacts = new ArrayList<ContactEntry>();
    }
    public boolean retrieveContacts (ContentResolver cr) {
        Log.d(TAG, "retrieveContacts: strat");

        //ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        if (cur.moveToFirst()) {

           do {
                ContactEntry contactEntry = new ContactEntry();
                String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));

                contactEntry.setId(id);
                Cursor cur1 = cr.query(
                        ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
                        ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
                        new String[]{id}, null);
                while (cur1.moveToNext()) {
                    //to get the contact names
              contactEntry.setName(cur1.getString(cur1.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)));
                    Log.e("Name :", contactEntry.getName());
                    String email = cur1.getString(cur1.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                    Log.e("Email", email);
                    if (email != null) {
                        contactEntry.setEmail(email);
                    }
                }
                contacts.add(contactEntry);
                cur1.close();
            } while (cur.moveToNext());
            cur.close();
        }
    return true;}

    public ArrayList<ContactEntry> getContacts() {
        return contacts;
    }

}
