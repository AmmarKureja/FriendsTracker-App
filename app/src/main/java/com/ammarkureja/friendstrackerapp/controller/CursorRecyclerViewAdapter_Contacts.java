package com.ammarkureja.friendstrackerapp.controller;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.ammarkureja.friendstrackerapp.R;
import com.ammarkureja.friendstrackerapp.model.Contact;
import com.ammarkureja.friendstrackerapp.model.ContactContract;

/**
 * Created by admin on 30/08/2017.
 */

class CursorRecyclerViewAdapter_Contacts extends RecyclerView.Adapter<CursorRecyclerViewAdapter_Contacts.ContactViewHolder> {

    private static final String TAG = "CursorRecyclerViewAdapt";

    private Cursor cCursor;

    public CursorRecyclerViewAdapter_Contacts(Cursor cCursor) {
        Log.d(TAG, "CursorRecyclerViewAdapter_Contacts: constructor called");
        this.cCursor = cCursor;
    }

    @Override
    public CursorRecyclerViewAdapter_Contacts.ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: on create view holder starts");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_list_items, parent, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CursorRecyclerViewAdapter_Contacts.ContactViewHolder holder, int position) {

        Log.d(TAG, "onBindViewHolder: on bind view holder starts...");
        if ((cCursor == null) || cCursor.getCount() == 0) {
            Log.d(TAG, "onBindViewHolder: providing instructions");
            holder.name.setText("Enter your name");
            holder.location.setText("nnn-nnnnnnn");
            holder.email.setText("user@email.com");
            holder.dob.setText("- -/- -/- - - -");
            holder.editButton.setVisibility(View.GONE);
            holder.deleteButton.setVisibility(View.GONE);
        } else {
            if (!cCursor.moveToPosition(position)) {
                throw new IllegalStateException("Cursor couldnt move to the position "+position);
            }


            final Contact contact = new Contact(cCursor.getLong(cCursor.getColumnIndex(ContactContract.Columns._ID)),
                    cCursor.getString(cCursor.getColumnIndex(ContactContract.Columns.PHONE_ID)),
                    cCursor.getString(cCursor.getColumnIndex(ContactContract.Columns.CONTACT_NAME)),
                    cCursor.getString(cCursor.getColumnIndex(ContactContract.Columns.CONTACT_DOB)),
                    cCursor.getString(cCursor.getColumnIndex(ContactContract.Columns.CONTACT_EMAIL)),
                    cCursor.getString(cCursor.getColumnIndex(ContactContract.Columns.CONTACT_LOCATION)),
                    cCursor.getString(cCursor.getColumnIndex(ContactContract.Columns.CONTACT_IMAGEURL)));


            holder.name.setText(contact.getmName());
            holder.email.setText(contact.getmEmail());
            holder.dob.setText(contact.getmBirth());
            holder.location.setText(contact.getmLocation());
            holder.profile_pic.setVisibility(View.VISIBLE);
            holder.editButton.setVisibility(View.VISIBLE); //TODO ADD ON CLICKLISTENER
            holder.deleteButton.setVisibility(View.VISIBLE); //TODO ADD ON CLICKLISTENER


            //this is the button listener for recycler view both edit/delete button
            View.OnClickListener buttonListener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "onClick: starts");
                    Log.d(TAG, "onClick: button with id "+ view.getId() + "clicked");
                    Log.d(TAG, "onClick: contact name is: " + contact.getmName());
                }
            };

            holder.editButton.setOnClickListener(buttonListener);
            holder.deleteButton.setOnClickListener(buttonListener);

        }
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: starts");
        if ((cCursor== null) || (cCursor.getCount() == 0)) {
            return 1; //
        } else {
            return  cCursor.getCount();
        }
    }

    Cursor swapCursor (Cursor newCursor) {
        if (newCursor == cCursor) {
            return null;
        }

        final  Cursor oldCursor = cCursor;
        cCursor = newCursor;
        if (newCursor != null) {
            //notify observers about the new cursor
            notifyDataSetChanged();
        } else {
            // notify the observers about the lack of data set
            notifyItemRangeRemoved(0, getItemCount());
        }

        return oldCursor;


    }


    static class ContactViewHolder extends RecyclerView.ViewHolder {
        TextView name = null;
        TextView email = null;
        TextView location = null;
        TextView dob = null;
        ImageView profile_pic = null;
        ImageButton editButton = null;
        ImageButton deleteButton = null;

        public ContactViewHolder(View itemView) {
            super(itemView);

            this.name = (TextView) itemView.findViewById(R.id.cli_name);
            this.email = (TextView) itemView.findViewById(R.id.cli_email);
            this.location = (TextView) itemView.findViewById(R.id.cli_location);
            this.dob = (TextView) itemView.findViewById(R.id.cli_birthday);
            this.profile_pic = (ImageView) itemView.findViewById(R.id.cli_image);
            this.editButton = (ImageButton) itemView.findViewById(R.id.cli_edit);
            this.deleteButton = (ImageButton) itemView.findViewById(R.id.cli_delete);
        }
    }
}
