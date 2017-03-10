package com.quang.tracnghiemtoan.models;

/**
 * Created by QUANG on 1/14/2017.
 */

public class UserOnline {
    private String firebaseId;
    private String facebookId;
    private String name;

    public UserOnline() {
    }

    public UserOnline(String firebaseId, String facebookId, String name) {
        this.firebaseId = firebaseId;
        this.facebookId = facebookId;
        this.name = name;
    }

    public String getFirebaseId() {
        return firebaseId;
    }

    public void setFirebaseId(String firebaseId) {
        this.firebaseId = firebaseId;
    }

    public String getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
