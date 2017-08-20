package com.ammarkureja.friendstrackerapp.model;

import java.util.ArrayList;

/**
 * Created by admin on 20/08/2017.
 */

public class MeetingEntry {
    private String id;
    private String title;
    private String startTime;
    private String endTime;
    private String location;
    private ArrayList<ContactEntry> participants;

    MeetingEntry(){
        this.id = null;
        this.title = null;
        this.startTime = null;
        this.endTime = null;
        this.participants = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public ArrayList<ContactEntry> getParticipants() {
        return participants;
    }

    public void setParticipants(ArrayList<ContactEntry> participants) {
        this.participants = participants;
    }
}
