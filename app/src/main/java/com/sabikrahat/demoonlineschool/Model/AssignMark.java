package com.sabikrahat.demoonlineschool.Model;

public class AssignMark {

    private String title;
    private String mark;
    private String comment;

    public AssignMark() {
    }

    public AssignMark(String title, String mark, String comment) {
        this.title = title;
        this.mark = mark;
        this.comment = comment;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTitle() {
        return title;
    }

    public String getMark() {
        return mark;
    }

    public String getComment() {
        return comment;
    }
}
