package com.sabikrahat.demoonlineschool.Model;

public class User {

    private String uid;
    private String rid;
    private String createAccountDateTime;
    private String batch;
    private String email;
    private String name;
    private String phoneNumber;
    private String imageURL;
    private String status;
    private String availability;

    public User() {
    }

    public User(String uid, String rid, String createAccountDateTime, String batch, String email, String name, String phoneNumber, String imageURL, String status, String availability) {
        this.uid = uid;
        this.rid = rid;
        this.createAccountDateTime = createAccountDateTime;
        this.batch = batch;
        this.email = email;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.imageURL = imageURL;
        this.status = status;
        this.availability = availability;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public void setCreateAccountDateTime(String createAccountDateTime) {
        this.createAccountDateTime = createAccountDateTime;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getUid() {
        return uid;
    }

    public String getRid() {
        return rid;
    }

    public String getCreateAccountDateTime() {
        return createAccountDateTime;
    }

    public String getBatch() {
        return batch;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getStatus() {
        return status;
    }

    public String getAvailability() {
        return availability;
    }
}
