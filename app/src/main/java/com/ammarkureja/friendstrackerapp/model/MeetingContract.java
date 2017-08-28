package com.ammarkureja.friendstrackerapp.model;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

import static com.ammarkureja.friendstrackerapp.model.AppProvider.CONTENT_AUTHORITY;
import static com.ammarkureja.friendstrackerapp.model.AppProvider.CONTENT_AUTHORITY_URI;

/**
 * Created by admin on 25/08/2017.
 */

public class MeetingContract {

    static final String TABLE_NAME = "Meetings";

    //Tasks fields

    public static class Columns {
        public static final String _ID = BaseColumns._ID;
        public static final String MEETING_TITLE = "Title";
        public static final String MEETING_START_TIME = "Start_Time";
        public static final String MEETING_END_TIME = "End_Time";
        public static final String MEETING_LOCATION = "Location";

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

    public static Uri buildContactsUri(long meetingId) {
        return ContentUris.withAppendedId(CONTENT_URI, meetingId);
    }

    public static long getMeetingId(Uri uri) {
        return ContentUris.parseId(uri);
    }
}
