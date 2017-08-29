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
import com.ammarkureja.friendstrackerapp.model.MeetingContract;

/**
 * Created by admin on 29/08/2017.
 */

class CursorRecyclerViewAdapter_Meetings extends RecyclerView.Adapter<CursorRecyclerViewAdapter_Meetings.MeetingViewHolder> {

    private static final String TAG = "CursorRecyclerViewAdapt";
    private Cursor mCursor;

    public CursorRecyclerViewAdapter_Meetings(Cursor mCursor) {
        Log.d(TAG, "CurRcyclrVwAdptrMeet:Con called");
        this.mCursor = mCursor;
    }

    @Override
    public MeetingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: new View requested");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meeting_list_items, parent, false);
        return new MeetingViewHolder(view);

    }

    @Override
    public void onBindViewHolder(MeetingViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: starts");

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
            holder.title.setText(mCursor.getString(mCursor.getColumnIndex(MeetingContract.Columns.MEETING_TITLE)));
            holder.statTime.setText(mCursor.getString(mCursor.getColumnIndex(MeetingContract.Columns.MEETING_START_TIME)));
            holder.endTime.setText(mCursor.getString(mCursor.getColumnIndex(MeetingContract.Columns.MEETING_END_TIME)));
            holder.location.setText(mCursor.getString(mCursor.getColumnIndex(MeetingContract.Columns.MEETING_LOCATION)));
            holder.date.setText(mCursor.getString(mCursor.getColumnIndex(MeetingContract.Columns.MEETING_DATE)));
            holder.countParticipants.setText(mCursor.getString(mCursor.getColumnIndex(MeetingContract.Columns.MEETING_COUNT_CONTACT)));
            holder.editButton.setVisibility(View.VISIBLE); //TODO ADD ON CLICKLISTENER
            holder.deleteButton.setVisibility(View.VISIBLE); //TODO ADD ON CLICKLISTENER
        }

    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: starts");
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
            notifyItemRangeChanged(0, getItemCount());
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
            Log.d(TAG, "MeetingViewHolder: starts");
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
