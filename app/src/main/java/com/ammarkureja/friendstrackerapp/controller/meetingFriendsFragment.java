package com.ammarkureja.friendstrackerapp.controller;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ammarkureja.friendstrackerapp.R;
import com.ammarkureja.friendstrackerapp.model.Contact;
import com.ammarkureja.friendstrackerapp.model.ContactContract;

import java.security.InvalidParameterException;
import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class meetingFriendsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, RecyclerItemClickListener.OnRecyclerClickListener{
    private static final String TAG = "meetingFriendsFragment";
    public static final int CONTACTS_LOADER_ID = 1;
//    public static final int MEETINGS_LOADER_ID = 2;
//    public static final int CONTACT_MEETING_LOADER = 3;
    private Meetingfriends_Adapter anAdapter; //adds adapter reference
    private ArrayList<Contact> contactList;
    private ArrayList<Contact> addedContacts;

    public meetingFriendsFragment() {
        contactList = new ArrayList<>();
        addedContacts = new ArrayList<>();


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onActivityCreated: starts...");
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(CONTACTS_LOADER_ID, null, this);
//        getLoaderManager().initLoader(MEETINGS_LOADER_ID, null, this);
//        getLoaderManager().initLoader(CONTACT_MEETING_LOADER, null, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meeting_friends, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_addfriends);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), recyclerView, this));
//        FloatingActionButton fab = (FloatingActionButton) View.findViewById();
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        anAdapter = new Meetingfriends_Adapter(null);
        Log.d(TAG, "onCreateView: helloworld");
        recyclerView.setAdapter(anAdapter);
        Log.d(TAG, "onCreateView: ends");



        return view;
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {


        String [] projection = {ContactContract.Columns._ID,
                ContactContract.Columns.PHONE_ID,
                ContactContract.Columns.CONTACT_NAME,
                ContactContract.Columns.CONTACT_DOB,
                ContactContract.Columns.CONTACT_EMAIL,
                ContactContract.Columns.CONTACT_LOCATION,
                ContactContract.Columns.CONTACT_IMAGEURL};
        String sortOrder = ContactContract.Columns.CONTACT_NAME + "," + ContactContract.Columns.CONTACT_EMAIL;
        switch (id) {
            case CONTACTS_LOADER_ID:
                return new CursorLoader(getActivity(),
                        ContactContract.CONTENT_URI,
                        projection, null, null,
                        sortOrder);
            default:
                throw new InvalidParameterException(TAG + ".onCreateLoader called with invalid id" + id);
        }
    }




    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        anAdapter.swapCursor(data);
        int count = anAdapter.getItemCount();
        Contact contact;
        if (data != null) {
            while (data.moveToNext()) {
                for (int i = 0; i <data.getColumnCount(); i++) {
                    Log.d(TAG, "onLoadFinish " + data.getColumnName(i) + ": "+data.getString(i));
                }
                Log.d(TAG, "onLoadFinished: =================================");
                contact = new Contact(data.getLong(0), data.getString(1), data.getString(2), data.getString(3), data.getString(4), data.getString(5), data.getString(6));
                contactList.add(contact);
            }
            count = data.getCount();

        }
        Log.d(TAG, "onLoadFinished: count is " + count);
    }



    @Override
    public void onLoaderReset(Loader loader) {

    }

    @Override
    public void onItemClick(View view, int position) {
        Log.d(TAG, "onItemClick: starts");
        Toast.makeText(getContext(), "Normal tap at position " + position + " " + getId(), Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onItemClick: Clicked it is: "+ contactList.get(position));
        addedContacts.add(contactList.get(position));



    }

    @Override
    public void onItemLongClick(View view, int position) {
        Log.d(TAG, "onItemLongClick: starts");
        Toast.makeText(getContext(), "Long tap at position" + position, Toast.LENGTH_SHORT).show();

    }


}
