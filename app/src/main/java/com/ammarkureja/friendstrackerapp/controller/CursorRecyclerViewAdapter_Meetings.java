package com.ammarkureja.friendstrackerapp.controller;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ammarkureja.friendstrackerapp.R;
import com.ammarkureja.friendstrackerapp.model.Meeting;
import com.ammarkureja.friendstrackerapp.model.MeetingContract;

/**
 * Created by admin on 29/08/2017.
 */

class CursorRecyclerViewAdapter_Meetings extends RecyclerView.Adapter<CursorRecyclerViewAdapter_Meetings.MeetingViewHolder> {

    private static final String TAG = "CursorRecyclerViewAdapt";
    private Cursor mCursor;
    private OnMeetingClickListener mListener;
    interface OnMeetingClickListener {
        void onEditClick(Meeting meeting);
        void onDeleteClick(Meeting meeting);

    }

    public CursorRecyclerViewAdapter_Meetings(Cursor myCursor, OnMeetingClickListener listener) {
        Log.d(TAG, "CurRcyclrVwAdptrMeet:Con called");
       mCursor = myCursor;
        mListener = listener;
    }

    @Override
    public MeetingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Log.d(TAG, "onCreateViewHolder: new View requested");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meeting_list_items, parent, false);
        return new MeetingViewHolder(view);

    }

    @Override
    public void onBindViewHolder(MeetingViewHolder holder, int position) {
        //Log.d(TAG, "onBindViewHolder: starts");

        if ((mCursor == null) || (mCursor.getCount() == 0)) {
            Log.d(TAG, "onBindViewHolder: providing instructions");
            holder.title.setText(R.string.title);
            holder.statTime.setText("00:00:00");
            holder.endTime.setText("00:00:00");
            holder.location.setText("00:00:00");
            holder.date.setText("00:00:00");
            holder.countParticipants.setText("00:00:00");
            holder.editButton.setVisibility(View.GONE);
            holder.deleteButton.setVisibility(View.GONE);

        } else {
            if (!mCursor.moveToPosition(position)) {
                throw new IllegalStateException("Couldnt move cursor to position " + position);
            }

        final    Meeting meeting = new Meeting(mCursor.getLong(mCursor.getColumnIndex(MeetingContract.Columns._ID)),
                    mCursor.getString(mCursor.getColumnIndex(MeetingContract.Columns.MEETING_TITLE)),
                    mCursor.getString(mCursor.getColumnIndex(MeetingContract.Columns.MEETING_LOCATION)),
                    mCursor.getString(mCursor.getColumnIndex(MeetingContract.Columns.MEETING_DATE)),
                    mCursor.getString(mCursor.getColumnIndex(MeetingContract.Columns.MEETING_START_TIME)),
                    mCursor.getString(mCursor.getColumnIndex(MeetingContract.Columns.MEETING_END_TIME)),
                    mCursor.getString(mCursor.getColumnIndex(MeetingContract.Columns.MEETING_COUNT_CONTACT)));


            holder.title.setText(meeting.getTitle());
            holder.statTime.setText(meeting.getStartTime());
            holder.endTime.setText(meeting.getEndTime());
            holder.location.setText(meeting.getLocation());
            holder.date.setText(meeting.getMeet_Date());
            holder.countParticipants.setText(meeting.getMeet_Count_Contacts());
            holder.editButton.setVisibility(View.VISIBLE); //TODO ADD ON CLICKLISTENER
            holder.deleteButton.setVisibility(View.VISIBLE); //TODO ADD ON CLICKLISTENER

            //this is the button listener for recycler view both edit/delete button
            View.OnClickListener buttonListener = new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    //Log.d(TAG, "onClick: starts");
                    switch (view.getId()) {
                        case R.id.mli_edit_item:
                            if (mListener != null){
                        mListener.onEditClick(meeting);
                            }
                            break;
                        case R.id.mli_delete_item:
                            if (mListener !=null) {
                                mListener.onDeleteClick(meeting);
                            }
                            break;
                        default:
                            Log.d(TAG, "onClick: unexpected button id");
                    }
                   // Log.d(TAG, "onClick: meeting id is: "+ meeting.getId());
                   // Log.d(TAG, "onClick: meeting name is: " + meeting.getTitle());

                }
            };



            holder.editButton.setOnClickListener(buttonListener);
            holder.deleteButton.setOnClickListener(buttonListener);
        }

    }

    @Override
    public int getItemCount() {
      //  Log.d(TAG, "getItemCount: starts");
        if ((mCursor== null) || (mCursor.getCount() == 0)) {
            return 1; //
        } else {
            return  mCursor.getCount();
        }

    }
    /*Swap a new cursor and return old cursor.
    The old Cursor is not closed
    * @param newCursor
    * @return returns the previously set cursor or null if there wasnt one
    * if the given new cursor is the same instance as the previously set
    * Cursor, null is also returned
    * */

    Cursor swapCursor (Cursor newCursor) {
        if (newCursor == mCursor) {
            return null;
        }



        final  Cursor oldCursor = mCursor;
        mCursor = newCursor;
        if (newCursor != null) {
            //notify observers about the new cursor
            notifyDataSetChanged();
        } else {
            // notify the observers about the lack of data set
            notifyItemRangeRemoved(0, getItemCount());
        }

        return oldCursor;


    }

    static class MeetingViewHolder extends RecyclerView.ViewHolder {
        private static final String TAG = "MeetingViewHolder";
        TextView title = null;
        TextView statTime = null;
        TextView endTime = null;
        TextView date = null;
        TextView location = null;
        TextView countParticipants = null;
        ImageButton editButton = null;
        ImageButton deleteButton = null;


        public MeetingViewHolder(View itemView ) {
            super(itemView);
          //  Log.d(TAG, "MeetingViewHolder: starts");
            this.title = (TextView) itemView.findViewById(R.id.mli_title);
            this.statTime = (TextView) itemView.findViewById(R.id.mli_start_time);
            this.endTime = (TextView) itemView.findViewById(R.id.mli_end_time);
            this.date = (TextView) itemView.findViewById(R.id.mli_meeting_date);
            this.location = (TextView) itemView.findViewById(R.id.mli_location);
            this.countParticipants = (TextView) itemView.findViewById(R.id.mli_number_of_attendees);
            this.editButton = (ImageButton) itemView.findViewById(R.id.mli_edit_item);
            this.deleteButton = (ImageButton) itemView.findViewById(R.id.mli_delete_item);


        }
    }
}
