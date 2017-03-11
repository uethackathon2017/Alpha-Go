package com.quang.tracnghiemtoan.models;

/**
 * Created by QUANG on 2/19/2017.
 */

public class ImageTest {
    private int height;
    private int width;
    private String link;
    private String id;

    public ImageTest() {
    }

    public ImageTest(int height, int width, String link, String id) {
        this.height = height;
        this.width = width;
        this.link = link;
        this.id = id;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
