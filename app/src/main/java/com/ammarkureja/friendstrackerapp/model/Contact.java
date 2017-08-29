package com.ammarkureja.friendstrackerapp.model;

import java.io.Serializable;

/**
 * Created by admin on 25/08/2017.
 */

public class Contact implements Serializable {
    public static final long serialVersionUID = 20161120L;
    private long m_Id;
    private final String mPhoneId;
    private final String mName;
    private final String mBirth;
    private final String mEmail;
    private final String mLocation;
    private final String mImageUrl;

    public Contact(long id, String mPhoneId, String mName, String mBirth, String mEmail,String mLocation, String mImageUrl) {
        this.m_Id = id;
        this.mPhoneId = mPhoneId;
        this.mName = mName;
        this.mBirth = mBirth;
        this.mEmail = mEmail;
        this.mLocation = mLocation;
        this.mImageUrl = mImageUrl;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public long getId() {
        return m_Id;
    }

    public String getmPhoneId() {
        return mPhoneId;
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

    public String getmLocation() {
        return mLocation;
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
                ", mPhoneId='" + mPhoneId + '\'' +
                ", mName='" + mName + '\'' +
                ", mBirth='" + mBirth + '\'' +
                ", mEmail='" + mEmail + '\'' +
                ", mLocation='" + mLocation + '\'' +
                ", mImageUrl='" + mImageUrl + '\'' +
                '}';
    }
}
