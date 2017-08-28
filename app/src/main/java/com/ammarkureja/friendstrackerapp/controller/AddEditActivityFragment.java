package com.ammarkureja.friendstrackerapp.controller;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.ammarkureja.friendstrackerapp.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class AddEditActivityFragment extends Fragment {
    private static final String TAG = "AddEditActivityFragment";

    public enum  FragmentEditMode {Edit, Add}
    private FragmentEditMode mMode;

    private EditText mNameTextView;
    private EditText mEmailTextView;
    private EditText mBirthTextView;
    private Button mSaveButton;

    public AddEditActivityFragment() {
        Log.d(TAG, "AddEditActivityFragment: constructor call");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: starts");
        View view = inflater.inflate(R.layout.fragment_add_edit, container, false);
        mNameTextView = (EditText) view.findViewById(R.id.addedit_name);
        mEmailTextView = (EditText) view.findViewById(R.id.addedit_email);
        mBirthTextView = (EditText) view.findViewById(R.id.addedit_dateofbirth);
        mSaveButton = (Button) view.findViewById(R.id.addedit_save);


        return inflater.inflate(R.layout.fragment_add_edit, container, false);
    }
}
