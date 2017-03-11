package com.quang.tracnghiemtoan.models;

/**
 * Created by QUANG on 8/11/2016.
 */

public class UserInfo {
    private String name;
    private String idFacebook;
    private String idFirebase;
    private int point;

    public UserInfo() {
    }

    public UserInfo(String name, String idFacebook, String idFirebase, int point) {
        this.name = name;
        this.idFacebook = idFacebook;
        this.idFirebase = idFirebase;
        this.point = point;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdFacebook() {
        return idFacebook;
    }

    public void setIdFacebook(String idFacebook) {
        this.idFacebook = idFacebook;
    }

    public String getIdFirebase() {
        return idFirebase;
    }

    public void setIdFirebase(String idFirebase) {
        this.idFirebase = idFirebase;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }
}
