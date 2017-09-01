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
import android.widget.Button;

import com.ammarkureja.friendstrackerapp.R;
import com.ammarkureja.friendstrackerapp.model.MeetingContract;

import java.security.InvalidParameterException;


public class MainActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final String TAG = "MainActivityFragment";

    public static final int LOADER_ID = 0;
    private static final int REQUEST_CODE_READ_CONTACTS = 1;
    private static boolean flag = false;

    private CursorRecyclerViewAdapter_Meetings mAdapter; // add adaptor reference
    Button addContacts;





    public MainActivityFragment() {
        Log.d(TAG, "MainActivityFragment: ");



    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onActivityCreated: starts");
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(LOADER_ID, null, this);

        addContacts.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Log.d(TAG, "onClick: on Click is working");
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d(TAG, "onCreateView: starts");
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.meeting_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        final View view2 = inflater.inflate(R.layout.fragment_main, container, false);
        addContacts=(Button) view2.findViewById(R.id.addPhoneContacts_btn);




        mAdapter = new CursorRecyclerViewAdapter_Meetings(null, (CursorRecyclerViewAdapter_Meetings.OnMeetingClickListener) getActivity());
        recyclerView.setAdapter(mAdapter);
        Log.d(TAG, "onCreateView: returning");
        return view;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Log.d(TAG, "onCreateLoader: starts with id: "+ id);
        String [] projection = {MeetingContract.Columns._ID,
                MeetingContract.Columns.MEETING_TITLE,
                MeetingContract.Columns.MEETING_LOCATION,
                MeetingContract.Columns.MEETING_DATE,
                MeetingContract.Columns.MEETING_START_TIME,
                MeetingContract.Columns.MEETING_END_TIME,
                MeetingContract.Columns.MEETING_COUNT_CONTACT};
        String sortOrder = MeetingContract.Columns.MEETING_DATE ;
        switch (id) {
            case LOADER_ID:
                return new CursorLoader(getActivity(),
                        MeetingContract.CONTENT_URI,
                        projection,
                        null,
                        null,
                        sortOrder);
            default:
                throw new InvalidParameterException(TAG + ".onCreateLoader called with invalid id" + id);
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Log.d(TAG, "Entering onLoadFinished: ");
        mAdapter.swapCursor(data);
        int count = mAdapter.getItemCount();



//        if (data != null) {
//            while (data.moveToNext()) {
//                for (int i = 0; i <data.getColumnCount(); i++) {
//                    Log.d(TAG, "onLoadFinished: " + data.getColumnName(i) + ": "+data.getString(i));
//                }
//                Log.d(TAG, "onLoadFinished: =================================");
//            }
//            count = data.getCount();
//        }
//        Log.d(TAG, "onLoadFinished: count is " + count);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);

    }


}


