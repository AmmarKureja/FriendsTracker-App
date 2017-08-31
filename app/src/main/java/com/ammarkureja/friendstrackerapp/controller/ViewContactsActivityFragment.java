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

import com.ammarkureja.friendstrackerapp.R;
import com.ammarkureja.friendstrackerapp.model.ContactContract;

import java.security.InvalidParameterException;

/**
 * A placeholder fragment containing a simple view.
 */
public class ViewContactsActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    public static final int LOADER_ID = 1;
    private CursorRecyclerViewAdapter_Contacts cAdapter; //adds adapter reference

    private static final String TAG = "ViewContactsActivity: ";

    public ViewContactsActivityFragment() {
        Log.d(TAG, "ViewContactsActivityFragment: constructor");

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onActivityCreated: starts...");
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(LOADER_ID, null, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //inflates the layout of this fregment
        Log.d(TAG, "onCreateView: starts");
        View view = inflater.inflate(R.layout.fragment_view_contacts, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.contact_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        cAdapter = new CursorRecyclerViewAdapter_Contacts(null);
        recyclerView.setAdapter(cAdapter);
        Log.d(TAG, "onCreateView: ends");
        return view;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {


        String [] projection = {ContactContract.Columns._ID,
                ContactContract.Columns.PHONE_ID,
                ContactContract.Columns.CONTACT_NAME,
                ContactContract.Columns.CONTACT_DOB,
                ContactContract.Columns.CONTACT_EMAIL,
                ContactContract.Columns.CONTACT_LOCATION,
                ContactContract.Columns.CONTACT_IMAGEURL};
        String sortOrder = ContactContract.Columns.CONTACT_NAME + "," + ContactContract.Columns.CONTACT_EMAIL;
        switch (id) {
            case LOADER_ID:
                return new CursorLoader(getActivity(),
                        ContactContract.CONTENT_URI,
                        projection, null, null,
                        sortOrder);
            default:
                throw new InvalidParameterException(TAG + ".onCreateLoader called with invalif id" + id);
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        Log.d(TAG, "onLoadFinished: Entering onLoad finished");
        cAdapter.swapCursor(data);
        int count = cAdapter.getItemCount();
//        if (data != null) {
//            while (data.moveToNext()) {
//                for (int i = 0; i <data.getColumnCount(); i++) {
//                    Log.d(TAG, "onLoadFinish " + data.getColumnName(i) + ": "+data.getString(i));
//                }
//                Log.d(TAG, "onLoadFinished: =================================");
//            }
//            count = data.getCount();
//        }
//        Log.d(TAG, "onLoadFinished: count is " + count);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
