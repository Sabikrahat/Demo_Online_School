package com.sabikrahat.demoonlineschool.Model;

public class RecordedVideo {

    private String batchName;
    private String vUid;
    private String videoTitle;
    private String videoDate;
    private String videoID;

    public RecordedVideo() {
    }

    public RecordedVideo(String batchName, String vUid, String videoTitle, String videoDate, String videoID) {
        this.batchName = batchName;
        this.vUid = vUid;
        this.videoTitle = videoTitle;
        this.videoDate = videoDate;
        this.videoID = videoID;
    }

    public void setBatchName(String batchName) {
        this.batchName = batchName;
    }

    public void setvUid(String vUid) {
        this.vUid = vUid;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }

    public void setVideoDate(String videoDate) {
        this.videoDate = videoDate;
    }

    public void setVideoID(String videoID) {
        this.videoID = videoID;
    }

    public String getBatchName() {
        return batchName;
    }

    public String getvUid() {
        return vUid;
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public String getVideoDate() {
        return videoDate;
    }

    public String getVideoID() {
        return videoID;
    }
}
