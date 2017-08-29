package com.ammarkureja.friendstrackerapp.model;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by admin on 22/08/2017.
 *
 * content povider for FriendsTrackerApp. This is the only class that knows about {@link AppDatabase}
 */

public class AppProvider extends ContentProvider{
    private static final String TAG = "AppProvider";

    private AppDatabase mOpenHelper;
    public static final UriMatcher sUriMatcher = buildUriMatcher();

    static final String CONTENT_AUTHORITY = "com.ammarkureja.friendstrackerapp.model.provider";
    public static final Uri CONTENT_AUTHORITY_URI = Uri.parse("content://"+ CONTENT_AUTHORITY);

    //Contacts Table
    private static final int CONTACTS = 100;
    private static final int CONTACTS_ID = 101;

    //Meetings Table
    private static final int MEETINGS = 200;
    private static final int MEETINGS_ID = 201;

    private static final int CONTACTS_MEETINGS = 300;
    private static final int CONTACTS_MEETINGS_ID = 401;

    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

        //e.g content://com.ammarkureja.friendstrackerapp.model.provider/Contacts
        matcher.addURI(CONTENT_AUTHORITY, ContactContract.TABLE_NAME, CONTACTS);
        //e.g content://com.ammarkureja.friendstrackerapp.model.provider/Contacts/8
        matcher.addURI(CONTENT_AUTHORITY,ContactContract.TABLE_NAME + "/#", CONTACTS_ID);

       matcher.addURI(CONTENT_AUTHORITY, MeetingContract.TABLE_NAME, MEETINGS);
       matcher.addURI(CONTENT_AUTHORITY, MeetingContract.TABLE_NAME + "/#", MEETINGS_ID);
//
        matcher.addURI(CONTENT_AUTHORITY, ContactMeetingContract.TABLE_NAME, CONTACTS_MEETINGS);
        matcher.addURI(CONTENT_AUTHORITY, ContactMeetingContract.TABLE_NAME + "/#", CONTACTS_MEETINGS_ID);

        return matcher;
    }
    @Override
    public boolean onCreate() {
        mOpenHelper = AppDatabase.getInstance(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Log.d(TAG, "query: called with uri "+ uri);
        final int match = sUriMatcher.match(uri);
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        Log.d(TAG, "query match: " + match);
        switch (match) {
            case CONTACTS:
                queryBuilder.setTables(ContactContract.TABLE_NAME);
                break;
            case CONTACTS_ID:
                queryBuilder.setTables(ContactContract.TABLE_NAME);
                long contactId = ContactContract.getContactId(uri);
                queryBuilder.appendWhere(ContactContract.Columns._ID + " = " + contactId);
                break;

            case MEETINGS:
                queryBuilder.setTables(MeetingContract.TABLE_NAME);
                break;
            case MEETINGS_ID:
                queryBuilder.setTables(MeetingContract.TABLE_NAME);
                long meetingId = MeetingContract.getMeetingId(uri);
                queryBuilder.appendWhere(MeetingContract.Columns._ID + " = " + meetingId);
                break;

            case CONTACTS_MEETINGS:
                queryBuilder.setTables(ContactMeetingContract.TABLE_NAME);
                break;
            case CONTACTS_MEETINGS_ID:
                queryBuilder.setTables(ContactMeetingContract.TABLE_NAME);
                long contactMeetingId = ContactMeetingContract.getContactMeetingId(uri);
                queryBuilder.appendWhere(ContactMeetingContract.Columns._ID + " = " + contactMeetingId);
                break;

            default:
                throw new IllegalArgumentException("Unknown Uri: "+uri);
        }
        SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        //returning cursor
        Cursor cursor = queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        Log.d(TAG, "query cursor: "+cursor);
        return cursor;


    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case CONTACTS:
                return ContactContract.CONTENT_TYPE;
            case CONTACTS_ID:
                return ContactContract.CONTENT_ITEM_TYPE;

            case MEETINGS:
                return MeetingContract.CONTENT_TYPE;

            case MEETINGS_ID:
                return MeetingContract.CONTENT_ITEM_TYPE;


            case CONTACTS_MEETINGS:
                return ContactMeetingContract.CONTENT_TYPE;

            case CONTACTS_MEETINGS_ID:
               return ContactMeetingContract.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Unknown Uri: "+uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        Log.d(TAG, "Entering insert called with uri: " + uri);
        final int match = sUriMatcher.match(uri);
        Log.d(TAG, "match is: "+match);
        final SQLiteDatabase db;

        Uri returnUri;
        long recordId;
        switch (match) {
            case CONTACTS:
                db = mOpenHelper.getWritableDatabase();
                recordId = db.insert(ContactContract.TABLE_NAME, null, values);
                if (recordId >= 0) {
                    returnUri = ContactContract.buildContactsUri(recordId);
                } else {
                    throw new android.database.SQLException("Failed to insert into" + uri.toString());
                }
                break;
            case MEETINGS:
                db = mOpenHelper.getWritableDatabase();
                recordId = db.insert(MeetingContract.TABLE_NAME, null, values);
                if (recordId >= 0) {
                    returnUri = MeetingContract.buildMeetingsUri(recordId);
                } else {
                    throw new android.database.SQLException("Failed to insert into" + uri.toString());
                }
                break;

            default:
                throw new IllegalArgumentException("Unknown Uri: "+uri);
        }
        Log.d(TAG, "Exiting insert and returning Uri: "+uri);
        return returnUri;
    }









    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        Log.d(TAG, "update: update called with uri: "+ uri);
        final int match = sUriMatcher.match(uri);
        Log.d(TAG, "match is: "+ match);

        final SQLiteDatabase db;
        int count;

        String selectionCriteria;

        switch (match) {
            case CONTACTS:
                db = mOpenHelper.getWritableDatabase();
                count = db.delete(ContactContract.TABLE_NAME, selection, selectionArgs);
                break;
            case CONTACTS_ID:
                db = mOpenHelper.getWritableDatabase();
                long contactId = ContactContract.getContactId(uri);
                selectionCriteria = ContactContract.Columns._ID + " = " +  contactId;
                if (selection !=null && (selection.length()>0)) {
                    selectionCriteria += " AND (" + selection + ")";
                }
                count = db.delete(ContactContract.TABLE_NAME, selectionCriteria, selectionArgs);
                break;


//            case MEETINGS:
//                db = mOpenHelper.getWritableDatabase();
//                count = db.delete(MeetingContract.TABLE_NAME, selection, selectionArgs);
//                break;
//            case MEETINGS_ID:
//                db = mOpenHelper.getWritableDatabase();
//                long meetingId = MeetingContract.getContactId(uri);
//                selectionCriteria = MeetingContract.Columns._ID + " = " +  meetingId;
//                if (selection !=null && (selection.length()>0)) {
//                    selectionCriteria += " AND (" + selection + ")";
//                }
//                count = db.delete(MeetingContract.TABLE_NAME, selectionCriteria, selectionArgs);
//                break;

            default:
                throw new IllegalArgumentException("Unknown Uri: "+ uri);
        }

        Log.d(TAG, "Exiting update: " + count);
        return count;

    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        Log.d(TAG, "update: update called with uri: "+ uri);
        final int match = sUriMatcher.match(uri);
        Log.d(TAG, "match is: "+ match);

        final SQLiteDatabase db;
        int count;

        String selectionCriteria;

        switch (match) {
            case CONTACTS:
                db = mOpenHelper.getWritableDatabase();
                count = db.update(ContactContract.TABLE_NAME, values, selection, selectionArgs);
                break;
            case CONTACTS_ID:
                db = mOpenHelper.getWritableDatabase();
                long contactId = ContactContract.getContactId(uri);
                        selectionCriteria = ContactContract.Columns._ID + " = " +  contactId;
                if (selection !=null && (selection.length()>0)) {
                    selectionCriteria += " AND (" + selection + ")";
                }
                count = db.update(ContactContract.TABLE_NAME, values, selectionCriteria, selectionArgs);
                break;


//            case MEETINGS:
//                db = mOpenHelper.getWritableDatabase();
//                count = db.update(MeetingContract.TABLE_NAME, values, selection, selectionArgs);
//                break;
//            case MEETINGS_ID:
//                db = mOpenHelper.getWritableDatabase();
//                long meetingId = MeetingContract.getContactId(uri);
//                selectionCriteria = MeetingContract.Columns._ID + " = " +  meetingId;
//                if (selection !=null && (selection.length()>0)) {
//                    selectionCriteria += " AND (" + selection + ")";
//                }
//                count = db.update(MeetingContract.TABLE_NAME, values, selectionCriteria, selectionArgs);
//                break;

            default:
                throw new IllegalArgumentException("Unknown Uri: "+ uri);
        }

        Log.d(TAG, "Exiting update: " + count);
        return count;
    }
}
