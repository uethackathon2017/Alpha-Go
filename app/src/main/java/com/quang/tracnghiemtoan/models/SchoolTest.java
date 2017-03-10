package com.quang.tracnghiemtoan.models;

/**
 * Created by QUANG on 3/10/2017.
 */

public class SchoolTest {

    private String title;
    private String linkTest;
    private String dateTest;
    private String linkImage;

    public SchoolTest() {
    }

    public SchoolTest(String title, String linkTest, String dateTest, String linkImage) {
        this.title = title;
        this.linkTest = linkTest;
        this.dateTest = dateTest;
        this.linkImage = linkImage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLinkTest() {
        return linkTest;
    }

    public void setLinkTest(String linkTest) {
        this.linkTest = linkTest;
    }

    public String getDateTest() {
        return dateTest;
    }

    public void setDateTest(String dateTest) {
        this.dateTest = dateTest;
    }

    public String getLinkImage() {
        return linkImage;
    }

    public void setLinkImage(String linkImage) {
        this.linkImage = linkImage;
    }
}
