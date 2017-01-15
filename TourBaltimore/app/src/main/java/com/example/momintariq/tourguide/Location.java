package com.example.momintariq.tourguide;

/**
 * Created by momintariq on 12/27/16.
 */

public class Location {

    // Private instance variables
    private String mName;
    private String mAddress;
    private String mPhoneNumber;
    private String mWebsite;
    private int mImageResourceID;

    // Constructor
    public Location(String name, String address, String phoneNumber, String website,
                    int imageResourceID) {
        mName = name;
        mAddress = address;
        mPhoneNumber = phoneNumber;
        mWebsite = website;
        mImageResourceID = imageResourceID;
    }

    // Public getters for the private instance variables
    public String getName() {
        return mName;
    }

    public String getAddress() {
        return mAddress;
    }

    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    public String getWebsite() {
        return mWebsite;
    }

    public int getImageResourceID() {
        return mImageResourceID;
    }
}
