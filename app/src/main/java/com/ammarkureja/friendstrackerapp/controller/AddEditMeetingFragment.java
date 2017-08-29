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
import com.ammarkureja.friendstrackerapp.model.Meeting;
import com.ammarkureja.friendstrackerapp.model.MeetingContract;

/**
 * A placeholder fragment containing a simple view.
 */
public class AddEditMeetingFragment extends Fragment {
    private static final String TAG = "AddEditMeetingFragment";

    private enum FregmentMeetingEditMode {EDIT, ADD}
    private FregmentMeetingEditMode mMood;

    private EditText mTitleTextView;
    private EditText mLocationTextView;
    private EditText mStarttimeTextView;
    private EditText mEndtimeTextView;
    private EditText mDateTextView;
    private Button mAddContacts;
    private Button  addeditMeeting_save_meeting;

    public AddEditMeetingFragment() {
        Log.d(TAG, "AddEditMeetingFragment: constructor called");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: starts");
        View view = inflater.inflate(R.layout.fragment_add_edit_meeting, container, false);

        mTitleTextView = (EditText) view.findViewById(R.id.addeditMeeting_title);
        mLocationTextView = (EditText) view.findViewById(R.id.addeditMeeting_location);
        mStarttimeTextView = (EditText) view.findViewById(R.id.addeditMeeting_starttime);
        mEndtimeTextView = (EditText) view.findViewById(R.id.addeditMeeting_endtime);
        mDateTextView = (EditText) view.findViewById(R.id.addeditMeeting_date);
        mAddContacts = (Button) view.findViewById(R.id.addeditMeeting_addcontacts);
        addeditMeeting_save_meeting = (Button) view.findViewById(R.id.addeditMeeting_save_meeting);

        Bundle arguments = getActivity().getIntent().getExtras(); //this line will change later

        final Meeting meeting;
        if (arguments != null) {
            Log.d(TAG, "onCreateView: retrieving meeting details.");

            meeting = (Meeting) arguments.getSerializable(Meeting.class.getSimpleName());
            if (meeting != null) {
                Log.d(TAG, "onCreateView: Task Details found, editing. . .");
                mTitleTextView.setText(meeting.getTitle());
                mLocationTextView.setText(meeting.getLocation());
                mStarttimeTextView.setText(meeting.getStartTime());
                mDateTextView.setText(meeting.getMeet_Date());
                mEndtimeTextView.setText(meeting.getEndTime());
                mMood = FregmentMeetingEditMode.EDIT;
            } else {
                // no meeting so we must be adding new meeting
                mMood = FregmentMeetingEditMode.ADD;
            }
        } else { //if arguments were null for som reason
            meeting = null;
            Log.d(TAG, "onCreateView: no Arguments, add a new record");
            mMood = FregmentMeetingEditMode.ADD;
        }

       addeditMeeting_save_meeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {

                //Update the database if atleast one field has changed.
                // there is no need to hit database unless this has happened

                String abc;
                if (mTitleTextView.length() > 0) {
                    abc = mTitleTextView.getText().toString();
                } else {
                    abc = null;
                }

                ContentResolver contentResolver = getActivity().getContentResolver();
                ContentValues contentValues = new ContentValues();

                switch (mMood) {
                    case EDIT:
                        if (!mTitleTextView.getText().toString().equals(meeting.getTitle())) {
                            contentValues.put(MeetingContract.Columns.MEETING_TITLE, meeting.getTitle().toString());
                        }

                        if (!mLocationTextView.getText().toString().equals(meeting.getLocation())){
                            contentValues.put(MeetingContract.Columns.MEETING_LOCATION, meeting.getLocation().toString());
                        }
                        if (!mStarttimeTextView.getText().toString().equals(meeting.getStartTime())) {
                            contentValues.put(MeetingContract.Columns.MEETING_START_TIME, meeting.getLocation().toString());
                        }
                        if (!mEndtimeTextView.getText().toString().equals(meeting.getEndTime())) {
                            contentValues.put(MeetingContract.Columns.MEETING_END_TIME, meeting.getEndTime().toString());
                        }
                        if (!mDateTextView.getText().toString().equals(meeting.getMeet_Date())) {
                            contentValues.put(MeetingContract.Columns.MEETING_DATE, meeting.getMeet_Date().toString());
                        }

                        if (contentValues.size() != 0) {
                            Log.d(TAG, "onClick: updating meeting values");
                            contentResolver.update(MeetingContract.buildMeetingsUri(meeting.getId()), contentValues, null, null);
                        }
                        break;

                    case ADD: {
                        Log.d(TAG, "onClick: adding new meeting");
                        contentValues.put(MeetingContract.Columns.MEETING_TITLE, mTitleTextView.getText().toString());
                        contentValues.put(MeetingContract.Columns.MEETING_START_TIME, mStarttimeTextView.getText().toString());
                        contentValues.put(MeetingContract.Columns.MEETING_END_TIME, mEndtimeTextView.getText().toString());
                        contentValues.put(MeetingContract.Columns.MEETING_DATE, mDateTextView.getText().toString());
                        contentValues.put(MeetingContract.Columns.MEETING_LOCATION, mLocationTextView.getText().toString());
                        contentResolver.insert(MeetingContract.CONTENT_URI, contentValues);
                    }
                }


                }


        });

        return view;

    }
}



