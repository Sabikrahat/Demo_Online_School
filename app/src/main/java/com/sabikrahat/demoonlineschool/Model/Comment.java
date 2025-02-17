package com.sabikrahat.demoonlineschool.Model;

public class Comment {

    private String comment;
    private String publisher;
    private String commentid;

    public Comment() {
    }

    public Comment(String comment, String publisher, String commentid) {
        this.comment = comment;
        this.publisher = publisher;
        this.commentid = commentid;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setCommentid(String commentid) {
        this.commentid = commentid;
    }

    public String getComment() {
        return comment;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getCommentid() {
        return commentid;
    }
}
