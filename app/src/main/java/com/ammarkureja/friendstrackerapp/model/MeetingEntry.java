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
    private double latitude;
    private double longitude;
    private ArrayList<ContactEntry> participants;

    MeetingEntry(){
        this.id = null;
        this.title = null;
        this.startTime = null;
        this.endTime = null;
        this.latitude = 0.0;
        this.longitude = 0.0;
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

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public ArrayList<ContactEntry> getParticipants() {
        return participants;
    }

    public void setParticipants(ArrayList<ContactEntry> participants) {
        this.participants = participants;
    }
}
