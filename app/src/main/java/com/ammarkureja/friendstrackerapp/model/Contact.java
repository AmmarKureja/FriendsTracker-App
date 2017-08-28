package com.ammarkureja.friendstrackerapp.model;

import java.io.Serializable;

/**
 * Created by admin on 25/08/2017.
 */

public class Contact implements Serializable {
    public static final long serialVersionUID = 20161120L;
    private long m_Id;
    private final String mName;
    private final String mBirth;
    private final String mEmail;
    private final String mImageUrl;

    public Contact(long id, String mName, String mBirth, String mEmail, String mImageUrl) {
        this.m_Id = id;
        this.mName = mName;
        this.mBirth = mBirth;
        this.mEmail = mEmail;
        this.mImageUrl = mImageUrl;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public long getId() {
        return m_Id;
    }

    public String getmName() {
        return mName;
    }

    public String getmBirth() {
        return mBirth;
    }

    public String getmEmail() {
        return mEmail;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setM_Id(long id) {
        this.m_Id = id;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "m_Id=" + m_Id +
                ", mName='" + mName + '\'' +
                ", mBirth='" + mBirth + '\'' +
                ", mEmail='" + mEmail + '\'' +
                ", mImageUrl='" + mImageUrl + '\'' +
                '}';
    }
}
