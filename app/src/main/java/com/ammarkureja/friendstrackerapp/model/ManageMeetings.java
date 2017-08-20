package com.ammarkureja.friendstrackerapp.model;

import java.util.ArrayList;

/**
 * Created by admin on 20/08/2017.
 */

public class ManageMeetings {

    private ArrayList<MeetingEntry> meetings;
    private static final String TAG = "ManageMeetings";

    public ManageMeetings() {
        this.meetings = new ArrayList<>();
    }
}
