package com.ammarkureja.friendstrackerapp.model;

import java.io.Serializable;

/**
 * Created by admin on 28/08/2017.
 */

public class Meeting implements Serializable {
    public static final long serialVsersionUID = 20122535L;

    private long meet_Id;
    private final String meet_Title;
    private final String meet_StartTime;
    private final String meet_EndTime;
    private final String meet_Date;
    private final String meet_Location;

    public Meeting(long id, String Title, String startTime, String endTime, String date, String Location) {
        this.meet_Id = id;
        this.meet_Title = Title;
        this.meet_StartTime = startTime;
        this.meet_EndTime = endTime;
        this.meet_Date = date;
        this.meet_Location = Location;
    }

    public long getId() {
        return meet_Id;
    }

    public String getTitle() {
        return meet_Title;
    }

    public String getStartTime() {
        return meet_StartTime;
    }

    public String getEndTime() {
        return meet_EndTime;
    }

    public String getMeet_Date() {
        return meet_Date;
    }

    public String getLocation() {
        return meet_Location;
    }

    public void setId(long meet_Id) {
        this.meet_Id = meet_Id;
    }

    @Override
    public String toString() {
        return "Meeting{" +
                "meet_Id=" + meet_Id +
                ", meet_Title='" + meet_Title + '\'' +
                ", meet_StartTime='" + meet_StartTime + '\'' +
                ", meet_EndTime='" + meet_EndTime + '\'' +
                ", meet_Date='" + meet_Date + '\'' +
                ", meet_Location='" + meet_Location + '\'' +
                '}';
    }
}
