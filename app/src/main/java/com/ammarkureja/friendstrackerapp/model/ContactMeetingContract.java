package com.ammarkureja.friendstrackerapp.model;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by admin on 25/08/2017.
 */

import static com.ammarkureja.friendstrackerapp.model.AppProvider.CONTENT_AUTHORITY;
import static com.ammarkureja.friendstrackerapp.model.AppProvider.CONTENT_AUTHORITY_URI;

public class ContactMeetingContract {

    static final String TABLE_NAME = "Contact_Meeting";

    //Tasks fields

    public static class Columns {
        public static final String _ID = BaseColumns._ID;
        public static final String CONTACT_ID = "Contact_id";
        public static final String MEETING_ID = "Meeting_id";


        private Columns() {
            // private constructor to prevent instatiation
        }
    }
    /*
    * The Uri to access the Contacts table
    * */

    public static final Uri CONTENT_URI = Uri.withAppendedPath(CONTENT_AUTHORITY_URI, TABLE_NAME);

    static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd." + CONTENT_AUTHORITY +"." + TABLE_NAME;
    static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd." + CONTENT_AUTHORITY + "." + TABLE_NAME;

    public static Uri buildContactsUri(long contactId) {
        return ContentUris.withAppendedId(CONTENT_URI, contactId);
    }

    public static long getContactId(Uri uri) {
        return ContentUris.parseId(uri);
    }
}
