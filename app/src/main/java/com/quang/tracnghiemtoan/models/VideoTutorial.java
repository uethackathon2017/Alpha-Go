package com.quang.tracnghiemtoan.models;

import com.quang.tracnghiemtoan.untils.StringUntils;

/**
 * Created by QUANG on 1/11/2017.
 */

public class VideoTutorial {
    private String titleVideo;
    private String descriptionVideo;
    private String idVideo;
    private String linkThumbnail;

    public VideoTutorial() {
    }

    public VideoTutorial(String titleVideo, String descriptionVideo, String idVideo, String linkThumbnail) {
        this.titleVideo = titleVideo;
        this.descriptionVideo = descriptionVideo;
        this.idVideo = idVideo;
        this.linkThumbnail = linkThumbnail;
    }

    public String getTitleVideo() {
        return titleVideo;
    }

    public void setTitleVideo(String titleVideo) {
        this.titleVideo = titleVideo;
    }

    public String getDescriptionVideo() {
        return descriptionVideo;
    }

    public void setDescriptionVideo(String descriptionVideo) {
        this.descriptionVideo = descriptionVideo;
    }

    public String getIdVideo() {
        return idVideo;
    }

    public void setIdVideo(String idVideo) {
        this.idVideo = idVideo;
    }

    public String getLinkThumbnail() {
        return linkThumbnail;
    }

    public void setLinkThumbnail(String linkThumbnail) {
        this.linkThumbnail = linkThumbnail;
    }

    @Override
    public String toString() {
        return StringUntils.unAccentAndLower(getTitleVideo());
    }
}
