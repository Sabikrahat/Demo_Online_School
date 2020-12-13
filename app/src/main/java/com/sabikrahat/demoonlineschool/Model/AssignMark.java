package com.sabikrahat.demoonlineschool.Model;

public class AssignMark {

    private String title;
    private String mark;
    private String comment;
    private boolean isShowable;

    public AssignMark() {
    }

    public AssignMark(String title, String mark, String comment, boolean isShowable) {
        this.title = title;
        this.mark = mark;
        this.comment = comment;
        this.isShowable = isShowable;
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

    public void setShowable(boolean showable) {
        isShowable = showable;
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

    public boolean isShowable() {
        return isShowable;
    }
}
