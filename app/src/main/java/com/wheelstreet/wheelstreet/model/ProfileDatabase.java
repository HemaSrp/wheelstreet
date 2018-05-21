package com.wheelstreet.wheelstreet.model;

import android.graphics.Bitmap;

public class ProfileDatabase {

    private String faceBookId;

    private String profileName;

    private String profileEmailId;

    private String profilePic;

    private String profileGender;

    private String profileNumber;

    public String getProfileAge() {
        return profileAge;
    }

    public void setProfileAge(String profileAge) {
        this.profileAge = profileAge;
    }

    private String profileAge;


    public String getFaceBookId() {
        return faceBookId;
    }

    public void setFaceBookId(String faceBookId) {
        this.faceBookId = faceBookId;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public String getProfileEmailId() {
        return profileEmailId;
    }

    public void setProfileEmailId(String profileEmailId) {
        this.profileEmailId = profileEmailId;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getProfileGender() {
        return profileGender;
    }

    public void setProfileGender(String profileGender) {
        this.profileGender = profileGender;
    }

    public String getProfileNumber() {
        return profileNumber;
    }

    public void setProfileNumber(String profileNumber) {
        this.profileNumber = profileNumber;
    }
}
