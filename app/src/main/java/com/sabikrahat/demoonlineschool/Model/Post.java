package com.sabikrahat.demoonlineschool.Model;

public class Post {

    private String postid;
    private String dateTime;
    private String description;
    private String publisher;

    public Post() {
    }

    public Post(String postid, String dateTime, String description, String publisher) {
        this.postid = postid;
        this.dateTime = dateTime;
        this.description = description;
        this.publisher = publisher;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPostid() {
        return postid;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public String getPublisher() {
        return publisher;
    }
}
